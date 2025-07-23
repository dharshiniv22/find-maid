package com.saveetha.findmaid

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.saveetha.findmaid.adapters.MaidNotificationAdapter
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.databinding.MaidRequestsBinding
import com.saveetha.findmaid.server.MaidNotificationResponse
import com.saveetha.findmaid.server.MaidRequestData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaidNotificationActivity : AppCompatActivity() {

    private lateinit var binding: MaidRequestsBinding
    private lateinit var adapter: MaidNotificationAdapter
    private val notificationList = mutableListOf<MaidRequestData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MaidRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.requestListView.layoutManager = LinearLayoutManager(this)
        adapter = MaidNotificationAdapter(notificationList)
        binding.requestListView.adapter = adapter

        fetchNotifications()
    }

    private fun fetchNotifications() {
        val api = ApiClient.apiService

        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val ownerId = sharedPref.getInt("user_id", -1)

        api.getMaidNotifications(
            ownerId = ownerId.toString()
        ).enqueue(object : Callback<MaidNotificationResponse> {
            override fun onResponse(
                call: Call<MaidNotificationResponse>,
                response: Response<MaidNotificationResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    notificationList.clear()

                    // Based on role, populate list
                    val items = if (data.role == "maid") data.notifications else data.bookings
                    if (!items.isNullOrEmpty()) {
                        notificationList.addAll(items)
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@MaidNotificationActivity, "No notifications", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MaidNotificationActivity, "Failed to load", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MaidNotificationResponse>, t: Throwable) {
                Toast.makeText(this@MaidNotificationActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
