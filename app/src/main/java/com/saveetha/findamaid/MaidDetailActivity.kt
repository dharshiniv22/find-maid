package com.saveetha.findamaid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.MaidDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaidDetailActivity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var maidImage: ImageView
    private lateinit var maidName: TextView
    private lateinit var maidAge: TextView
    private lateinit var maidLocation: TextView
    private lateinit var maidExperience: TextView
    private lateinit var maidLanguage: TextView
    private lateinit var maidContact: TextView
    private lateinit var maidSalary: TextView
    private lateinit var maidHours: TextView
    private lateinit var bookNowButton: Button
    private lateinit var progressBar: ProgressBar

    private var maidId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maid_details)

        // Get maid ID from intent
        maidId = intent.getIntExtra("maid_id", 0)

        // Initialize views
        backArrow = findViewById(R.id.back_arrow)
        maidImage = findViewById(R.id.maidImage)
        maidName = findViewById(R.id.maidName)
        maidAge = findViewById(R.id.maidAge)
        maidLocation = findViewById(R.id.maidLocation)
        maidExperience = findViewById(R.id.maidExperience)
        maidLanguage = findViewById(R.id.maidLanguage)
        maidContact = findViewById(R.id.maidContact)
        maidSalary = findViewById(R.id.maidSalary)
        maidHours = findViewById(R.id.maidHours)
        bookNowButton = findViewById(R.id.bookNowButton)
        progressBar = ProgressBar(this)
        progressBar.visibility = View.GONE

        backArrow.setOnClickListener { onBackPressed() }

        // Navigate to BookingActivity on button click, sending maid_id
        bookNowButton.setOnClickListener {
            val intent = Intent(this, BookingActivity::class.java)
            intent.putExtra("maid_post_id", maidId)
            startActivity(intent)
        }

        fetchMaidDetails()
    }

    private fun fetchMaidDetails() {
        progressBar.visibility = View.VISIBLE

        val call = ApiClient.apiService.getMaidById(maidId)
        call.enqueue(object : Callback<MaidDetailResponse> {
            override fun onResponse(
                call: Call<MaidDetailResponse>,
                response: Response<MaidDetailResponse>
            ) {
                progressBar.visibility = View.GONE
                val body = response.body()
                if (body != null && body.status && body.maid != null) {
                    val maid = body.maid

                    // Populate fields safely
                    maidName.text = maid.name ?: "Not specified"
                    maidAge.text = maid.age ?: "Not specified"
                    maidLocation.text = maid.location ?: "Not specified"
                    maidExperience.text = maid.experience ?: "Not specified"
                    maidLanguage.text = maid.language ?: "Not specified"
                    maidContact.text = maid.phone_number ?: "Not specified"
                    maidSalary.text = maid.expected_salary ?: "Not specified"
                    maidHours.text = maid.working_hours ?: "Not specified"

                    Glide.with(this@MaidDetailActivity)
                        .load(maid.photo_url)
                        .placeholder(R.drawable.samplemaid)
                        .into(maidImage)

                } else {
                    Toast.makeText(this@MaidDetailActivity, body?.message ?: "Maid not found", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MaidDetailResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@MaidDetailActivity, "Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
