package com.saveetha.findamaid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.adapter.UserRequestAdapter
import com.saveetha.findamaid.server.UserRequest

class UserRequestStatusActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserRequestAdapter
    private val userRequestList = mutableListOf<UserRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_requests_status)

        recyclerView = findViewById(R.id.userRequestListView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = UserRequestAdapter(userRequestList)
        recyclerView.adapter = adapter

        fetchUserRequests()
    }

    private fun fetchUserRequests() {
        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_id", -1)

        if (userId != -1) {
//            ApiClient.apiService.getUserRequests(userId)
//                .enqueue(object : Callback<ApiResponse> {
//                    override fun onResponse(
//                        call: Call<ApiResponse>,
//                        response: Response<ApiResponse>
//                    ) {
//                        if (response.isSuccessful && response.body()?.status == true) {
//                            userRequestList.clear()
//                            userRequestList.addAll(response.body()!!.notifications)
//                            adapter.notifyDataSetChanged()
//                        } else {
//                            Toast.makeText(this@UserRequestStatusActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//
//                    override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                        Toast.makeText(this@UserRequestStatusActivity, "Network Error", Toast.LENGTH_SHORT).show()
//                        t.printStackTrace()
//                    }
//                })
        }
    }
}
