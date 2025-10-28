package com.saveetha.findamaid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.adapters.NUserNotificationAdapter
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.GenericResponse
import com.saveetha.findamaid.server.NNotificationResponse
import com.saveetha.findamaid.server.NotificationItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class N_Activity_User_Notification : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NUserNotificationAdapter
    private var notificationList = mutableListOf<NotificationItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.n_user_notification)

        recyclerView = findViewById(R.id.recycler_view_user_notifications)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NUserNotificationAdapter(this, notificationList)
        recyclerView.adapter = adapter

        fetchNotifications()
    }

    private fun fetchNotifications() {
        val userId = getLoggedInUserId()
        Log.d("DEBUG", "Fetching notifications for user_id: $userId")

        if (userId.isEmpty()) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClient.apiService.getNNotifications(userId)
            .enqueue(object : Callback<NNotificationResponse> {
                override fun onResponse(
                    call: Call<NNotificationResponse>,
                    response: Response<NNotificationResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == true) {
                        notificationList.clear()
                        response.body()?.notifications?.let { notificationList.addAll(it) }
                        adapter.notifyDataSetChanged()
                        Log.d("DEBUG", "Notifications loaded: ${notificationList.size}")
                    } else {
                        Toast.makeText(this@N_Activity_User_Notification, "No notifications found", Toast.LENGTH_SHORT).show()
                        Log.d("DEBUG", "No notifications found for user_id: $userId")
                    }
                }

                override fun onFailure(call: Call<NNotificationResponse>, t: Throwable) {
                    Toast.makeText(this@N_Activity_User_Notification, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getLoggedInUserId(): String {
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        return sharedPref.getString("user_id", "") ?: ""
    }

    fun updateNotificationStatus(requestId: String, status: String) {
        ApiClient.apiService.updateStatus(requestId, status)
            .enqueue(object : Callback<GenericResponse> {
                override fun onResponse(call: Call<GenericResponse>, response: Response<GenericResponse>) {
                    if (response.isSuccessful && response.body()?.status == true) {
                        Toast.makeText(this@N_Activity_User_Notification, "Status updated successfully", Toast.LENGTH_SHORT).show()
                        fetchNotifications()
                    } else {
                        Toast.makeText(this@N_Activity_User_Notification, "Failed to update status", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                    Toast.makeText(this@N_Activity_User_Notification, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
