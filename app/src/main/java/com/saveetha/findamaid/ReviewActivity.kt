package com.saveetha.findamaid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.ReviewResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {

    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var adapter: ReviewAdapter
    private lateinit var btnRateUs: Button
    private var postId: Int = 0
    private var maidId: Int = 0
    private var userId: Int = 0
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        reviewRecyclerView = findViewById(R.id.reviewRecyclerView)
        btnRateUs = findViewById(R.id.btn_rate_us)

        postId = intent.getIntExtra("post_id", 0)
        maidId = intent.getIntExtra("maid_id", 0)
        userId = intent.getIntExtra("user_id", 0)
        userName = intent.getStringExtra("user_name") ?: "Unknown"

        reviewRecyclerView.layoutManager = LinearLayoutManager(this)

        btnRateUs.setOnClickListener {
            val intent = Intent(this, RateUsActivity::class.java)
            intent.putExtra("maid_id", maidId)
            intent.putExtra("user_id", userId)
            intent.putExtra("user_name", userName)
            startActivity(intent)
        }

        fetchReviews()
    }

    override fun onResume() {
        super.onResume()
        fetchReviews()
    }

    private fun fetchReviews() {
        ApiClient.apiService.getReviews(postId)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        val reviewResponse = response.body()
                        if (reviewResponse != null && reviewResponse.status.equals("success")) {
                            adapter = ReviewAdapter(reviewResponse.reviews)
                            reviewRecyclerView.adapter = adapter
                        }
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}
