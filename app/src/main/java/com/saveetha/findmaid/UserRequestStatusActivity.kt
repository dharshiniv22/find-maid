package com.saveetha.findmaid

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findmaid.adapters.MaidNotificationAdapter
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.server.MaidRequestData
import com.saveetha.findmaid.server.MaidRequestsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRequestStatusActivity : AppCompatActivity() {

    private lateinit var userRequestRecyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maid_requests) // Your XML layout file

//        userRequestRecyclerView = findViewById(R.id.userRequestRecyclerView)
//        userRequestRecyclerView.layoutManager = LinearLayoutManager(this)
//
//        getUserNotifications()
    }

    private fun getUserNotifications() {
        val apiService = ApiClient.apiService
        val call = apiService.getUserNotifications(userId = 1) // Replace with actual dynamic user ID if needed

        call.enqueue(object : Callback<MaidRequestsResponse> {
            override fun onResponse(
                call: Call<MaidRequestsResponse>,
                response: Response<MaidRequestsResponse>
            ) {
                if (response.isSuccessful && response.body()?.status == true) {
                    val notifications: List<MaidRequestData>? = response.body()?.notifications
                    if (!notifications.isNullOrEmpty()) {
                        userRequestRecyclerView.adapter = MaidNotificationAdapter(notifications)
                    } else {
                        Toast.makeText(
                            this@UserRequestStatusActivity,
                            "No notifications found.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@UserRequestStatusActivity,
                        "Failed to load notifications.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MaidRequestsResponse>, t: Throwable) {
                Toast.makeText(
                    this@UserRequestStatusActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
