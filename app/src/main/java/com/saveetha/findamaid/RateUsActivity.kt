package com.saveetha.findamaid

import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.model.ReviewSubmitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RateUsActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var commentEditText: EditText
    private lateinit var submitButton: Button
    private var maidPostId: Int = 0
    private var userId: Int = 0
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)

        ratingBar = findViewById(R.id.ratingBar)
        commentEditText = findViewById(R.id.feedbackEditText)
        submitButton = findViewById(R.id.submitButton)

        maidPostId = intent.getIntExtra("maid_id", -1)
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)

        userId = sharedPreferences.getInt("user_id",-1)
        userName = sharedPreferences.getString("user_name","").toString()

        submitButton.setOnClickListener {
            val rating = ratingBar.rating.toInt()
            val comment = commentEditText.text.toString().trim()

            if (rating > 0 && comment.isNotBlank()) {
                ApiClient.apiService.submitReview(maidPostId, userId, userName, rating, comment)
                    .enqueue(object : Callback<ReviewSubmitResponse> {
                        override fun onResponse(
                            call: Call<ReviewSubmitResponse>,
                            response: Response<ReviewSubmitResponse>
                        ) {
                            if (response.isSuccessful && response.body()?.status == true) {
                                Toast.makeText(
                                    this@RateUsActivity,
                                    "Review submitted successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RateUsActivity,
                                    "Submission failed: ${response.body()?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<ReviewSubmitResponse>, t: Throwable) {
                            Toast.makeText(
                                this@RateUsActivity,
                                "Error: ${t.localizedMessage}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            } else {
                Toast.makeText(this, "Please provide rating and comment", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
