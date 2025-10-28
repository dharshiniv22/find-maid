//package com.saveetha.findamaid
//
//import android.content.Context
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.saveetha.findamaid.adapters.MaidNotificationAdapter
//import com.saveetha.findamaid.api.ApiClient
//import com.saveetha.findamaid.databinding.MaidRequestsBinding
//import com.saveetha.findamaid.server.MaidNotificationResponse
//import com.saveetha.findamaid.server.MaidRequestData
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//
//
//class MaidNotificationActivity : AppCompatActivity() {
//
//    private lateinit var binding: MaidRequestsBinding
//    private lateinit var adapter: MaidNotificationAdapter
//    private val notificationList = mutableListOf<MaidRequestData>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = MaidRequestsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.requestListView.layoutManager = LinearLayoutManager(this)
//        adapter = MaidNotificationAdapter(notificationList)
//        binding.requestListView.adapter = adapter
//
//        fetchNotifications()
//    }
//
//    private fun fetchNotifications() {
//        val api = ApiClient.apiService
//
//        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
//        val ownerId = sharedPref.getInt("user_id", -1)
//
//        api.getNotifications(
//            ownerId = ownerId.toString()
//        ).enqueue(object : Callback<MaidNotificationResponse> {
//            override fun onResponse(
//                call: Call<MaidNotificationResponse>,
//                response: Response<MaidNotificationResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val notifications = response.body()
//                    if (notifications != null && notifications.status) {
//                        val bookingList: List<MaidRequestData> = notifications.notifications ?: emptyList()
//                        adapter = MaidNotificationAdapter(bookingList)
//                        binding.requestListView.adapter = adapter
//                    } else {
//                        Toast.makeText(this@MaidNotificationActivity, "No booking data found", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(this@MaidNotificationActivity, "Server response failed", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<MaidNotificationResponse>, t: Throwable) {
//                Toast.makeText(this@MaidNotificationActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}
