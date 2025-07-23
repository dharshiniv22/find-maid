package com.saveetha.findmaid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.server.MaidDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaidDetailActivity : AppCompatActivity() {

    private lateinit var maidImage: ImageView
    private lateinit var maidName: TextView
    private lateinit var maidAge: TextView
    private lateinit var maidLocation: TextView
    private lateinit var maidExperience: TextView
    private lateinit var maidContact: TextView
    private lateinit var maidSalary: TextView
    private lateinit var maidHours: TextView
    private lateinit var backArrow: ImageView
    private lateinit var bookNowButton: Button

    private var maidPostId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maid_details)

        // ✅ Check login
        val sharedPreferences = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        if (!sharedPreferences.getBoolean("is_logged_in", false)) {
            Toast.makeText(this, "Please log in to view details", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginPage::class.java))
            finish()
            return
        }

        // View bindings
        maidImage = findViewById(R.id.maidImage)
        maidName = findViewById(R.id.maidName)
        maidAge = findViewById(R.id.maidAge)
        maidLocation = findViewById(R.id.maidLocation)
        maidExperience = findViewById(R.id.maidExperience)
        maidContact = findViewById(R.id.maidContact)
        maidSalary = findViewById(R.id.maidSalary)
        maidHours = findViewById(R.id.maidHours)
        backArrow = findViewById(R.id.back_arrow)
        bookNowButton = findViewById(R.id.bookNowButton)

        bookNowButton.isEnabled = false

        // Get maid_post_id from previous screen
        maidPostId = intent.getIntExtra("maid_id", -1) // 🔄 This is the correct field coming in

        if (maidPostId != -1) {
            fetchMaidDetails(maidPostId)
        } else {
            Toast.makeText(this, "Invalid maid ID", Toast.LENGTH_SHORT).show()
            finish()
        }

        backArrow.setOnClickListener {
            finish()
        }
    }

    private fun fetchMaidDetails(maidId: Int) {
        ApiClient.apiService.getMaidById(maidId).enqueue(object : Callback<MaidDetailResponse> {
            override fun onResponse(call: Call<MaidDetailResponse>, response: Response<MaidDetailResponse>) {
                if (response.isSuccessful && response.body()?.status == true) {
                    val maid = response.body()!!.maid

                    // Fill UI
                    maidName.text = maid.name
                    maidAge.text = "${maid.age} years"
                    maidLocation.text = maid.location
                    maidExperience.text = maid.experience
                    maidContact.text = maid.phone_number
                    maidSalary.text = "₹${maid.expected_salary} / month"
                    maidHours.text = maid.working_hours

                    // Image
                    Glide.with(this@MaidDetailActivity)
                        .load(maid.photo_url)
                        .placeholder(R.drawable.samplemaid)
                        .into(maidImage)

                    // Enable booking
                    bookNowButton.isEnabled = true

                    bookNowButton.setOnClickListener {
                        val userId = getSharedPreferences("UserSession", Context.MODE_PRIVATE).getInt("user_id", -1)
                        if (userId != -1) {
                            val intent = Intent(this@MaidDetailActivity, BookingActivity::class.java)
                            intent.putExtra("maid_post_id", maidPostId)  // ✅ Correct key
                            intent.putExtra("user_id", userId)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@MaidDetailActivity, "User not logged in", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this@MaidDetailActivity, "Failed to load maid details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MaidDetailResponse>, t: Throwable) {
                Toast.makeText(this@MaidDetailActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
