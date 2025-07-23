package com.saveetha.findmaid

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findmaid.adapters.MaidNotificationAdapter
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.server.MaidNotificationResponse
import com.saveetha.findmaid.server.MaidRequestData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaidRequestsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MaidNotificationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maid_requests)

        recyclerView = findViewById(R.id.maidRequestRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchNotifications()
    }

    private fun fetchNotifications() {
        val pref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val ownerId = pref.getInt("user_id", -1)

        if (ownerId == -1) {
            Toast.makeText(this, "User ID not found in session", Toast.LENGTH_SHORT).show()
            return
        }

        val call = ApiClient.apiService.getMaidNotifications(ownerId.toString())

        call.enqueue(object : Callback<MaidNotificationResponse> {
            override fun onResponse(
                call: Call<MaidNotificationResponse>,
                response: Response<MaidNotificationResponse>
            ) {
                if (response.isSuccessful) {
                    val notifications = response.body()
                    if (notifications != null && notifications.status) {
                        val bookingList: List<MaidRequestData> = notifications.bookings ?: emptyList()
                        adapter = MaidNotificationAdapter(bookingList)
                        recyclerView.adapter = adapter
                    } else {
                        Toast.makeText(this@MaidRequestsActivity, "No booking data found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MaidRequestsActivity, "Server response failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MaidNotificationResponse>, t: Throwable) {
                Toast.makeText(this@MaidRequestsActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
