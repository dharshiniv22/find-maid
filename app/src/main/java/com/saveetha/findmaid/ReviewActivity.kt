package com.saveetha.findmaid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.model.Review
import com.saveetha.findmaid.server.ReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {
    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private val reviews = mutableListOf<Review>()
    private lateinit var btnRateUs: Button
    private val RATE_US_REQUEST_CODE = 1001
    private var maidId: Int = -1
    private var maidName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Get data from intent
        maidId = intent.getIntExtra("maid_id", -1)
        maidName = intent.getStringExtra("maid_name") ?: ""

        findViewById<TextView>(R.id.titleReviews)?.text = "All Reviews"

        reviewRecyclerView = findViewById(R.id.reviewRecyclerView)
        btnRateUs = findViewById(R.id.btn_rate_us)

        reviewAdapter = ReviewAdapter(reviews)
        reviewRecyclerView.layoutManager = LinearLayoutManager(this)
        reviewRecyclerView.adapter = reviewAdapter

        fetchReviews(maidName)

        btnRateUs.setOnClickListener {
            val intent = Intent(this, RateUsActivity::class.java)
            intent.putExtra("maid_id", maidId)
            intent.putExtra("maid_name", maidName)
            startActivityForResult(intent, RATE_US_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RATE_US_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val rating = data.getIntExtra("rating", 0)
            val reviewText = data.getStringExtra("review") ?: "No comments"
            val reviewerName = data.getStringExtra("reviewer_name") ?: "You"

            // Add new review to the list
            reviews.add(Review(reviewerName, reviewText, rating))
            reviewAdapter.notifyItemInserted(reviews.size - 1)
        }
    }

    private fun fetchReviews(maidName: String) {
        ApiClient.apiService.getReviews(maidName)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                    if (response.isSuccessful && response.body()?.status == "success") {
                        val fetchedReviews = response.body()?.reviews ?: emptyList()
                        reviews.clear()
                        reviews.addAll(fetchedReviews)
                        reviewAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@ReviewActivity, "No reviews found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Toast.makeText(this@ReviewActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
