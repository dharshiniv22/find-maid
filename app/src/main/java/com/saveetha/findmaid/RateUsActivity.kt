package com.saveetha.findmaid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.server.ReviewSubmitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RateUsActivity : AppCompatActivity() {

    private lateinit var starViews: List<ImageView>
    private var selectedRating = 0
    private lateinit var editFeedback: EditText
    private lateinit var btnSubmitRating: Button

    private var maidName: String = ""
    private var maidId: Int = -1
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)

        // Get views
        starViews = listOf(
            findViewById(R.id.star1),
            findViewById(R.id.star2),
            findViewById(R.id.star3),
            findViewById(R.id.star4),
            findViewById(R.id.star5)
        )
        editFeedback = findViewById(R.id.edit_feedback)
        btnSubmitRating = findViewById(R.id.btn_submit_rating)

        // Get maid info from intent
        maidName = intent.getStringExtra("maid_name") ?: ""
        maidId = intent.getIntExtra("maid_id", -1)

        // Get logged-in user ID from SharedPreferences
        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        userId = sharedPref.getInt("user_id", -1)

        // Star click logic
        for (i in starViews.indices) {
            starViews[i].setOnClickListener {
                selectedRating = i + 1
                updateStars(selectedRating)
            }
        }

        // Submit button click
        btnSubmitRating.setOnClickListener {
            val feedbackText = editFeedback.text.toString()

            if (selectedRating == 0) {
                Toast.makeText(this, "Please select a rating", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (maidName.isEmpty() || userId == -1) {
                Toast.makeText(this, "Invalid maid or user details", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            submitReview(maidName, userId, selectedRating, feedbackText)
        }
    }

    // Highlight selected stars
    private fun updateStars(rating: Int) {
        for (i in starViews.indices) {
            starViews[i].setImageResource(
                if (i < rating) R.drawable.ratestar_gold else R.drawable.ratestar
            )
        }
    }

    // Submit review via API
    private fun submitReview(maidName: String, userId: Int, rating: Int, comment: String) {
        ApiClient.apiService.submitReview(maidName, userId, rating, comment)
            .enqueue(object : Callback<ReviewSubmitResponse> {
                override fun onResponse(
                    call: Call<ReviewSubmitResponse>,
                    response: Response<ReviewSubmitResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == "success") {
                        Toast.makeText(
                            this@RateUsActivity,
                            "Review submitted successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                        // Get user name to send back to ReviewActivity
                        val reviewerName = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                            .getString("user_name", "You") ?: "You"

                        val resultIntent = Intent().apply {
                            putExtra("rating", rating)
                            putExtra("review", comment)
                            putExtra("reviewer_name", reviewerName)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    } else {
                        Toast.makeText(this@RateUsActivity, "Failed to submit review", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ReviewSubmitResponse>, t: Throwable) {
                    Toast.makeText(this@RateUsActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
