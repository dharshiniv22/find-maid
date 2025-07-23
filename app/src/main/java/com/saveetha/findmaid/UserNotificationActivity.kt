package com.saveetha.findmaid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findmaid.adapters.MaidNotificationAdapter
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.server.MaidRequestsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserNotificationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_notifications)

        recyclerView = findViewById(R.id.userRequestRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val userId = intent.getStringExtra("id") ?: "0"

        ApiClient.apiService.getUserNotifications(userId)
            .enqueue(object : Callback<MaidRequestsResponse> {
                override fun onResponse(
                    call: Call<MaidRequestsResponse>,
                    response: Response<MaidRequestsResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == true) {
                        val notifications = response.body()?.notifications ?: emptyList()
                        recyclerView.adapter = MaidNotificationAdapter(notifications)
                    } else {
                        Toast.makeText(this@UserNotificationActivity, "No requests found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MaidRequestsResponse>, t: Throwable) {
                    Toast.makeText(this@UserNotificationActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
