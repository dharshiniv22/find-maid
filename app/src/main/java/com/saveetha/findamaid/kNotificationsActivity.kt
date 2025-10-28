package com.saveetha.findamaid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.adapters.kNotificationAdapter
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.kNotificationServer
import com.saveetha.findamaid.server.NotificationsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class kNotificationsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: kNotificationAdapter
    private val notificationList = mutableListOf<kNotificationServer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knotifications)

        recyclerView = findViewById(R.id.rvNotifications)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = kNotificationAdapter(notificationList) { item, status ->
            updateStatus(item, status)
        }
        recyclerView.adapter = adapter

        // ✅ Get user ID from SessionManager
        val userId = SessionManager.getUserId(this)
        if (userId == 0) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        fetchNotifications(userId)
    }

    private fun fetchNotifications(userId: Int) {
        ApiClient.apiService.kGetNotifications(userId)
            .enqueue(object : Callback<NotificationsResponse> {
                override fun onResponse(
                    call: Call<NotificationsResponse>,
                    response: Response<NotificationsResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == true) {
                        notificationList.clear()
                        notificationList.addAll(response.body()?.data ?: emptyList())
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(
                            this@kNotificationsActivity,
                            response.body()?.message ?: "Failed to fetch notifications",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<NotificationsResponse>, t: Throwable) {
                    Toast.makeText(
                        this@kNotificationsActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun updateStatus(item: kNotificationServer, status: String) {
        ApiClient.apiService.kUpdateStatus(item.id, status)
            .enqueue(object : Callback<Map<String, Any>> {
                override fun onResponse(
                    call: Call<Map<String, Any>>,
                    response: Response<Map<String, Any>>
                ) {
                    if (response.isSuccessful) {
                        item.status = status
                        adapter.updateStatus(item)
                        Toast.makeText(
                            this@kNotificationsActivity,
                            "Status updated to $status",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@kNotificationsActivity,
                            "Failed to update status",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(
                        this@kNotificationsActivity,
                        "Error: ${t.localizedMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
