package com.saveetha.findamaid

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.MaidNotificationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifActivity : AppCompatActivity() {

    private lateinit var rvNotif: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val ownerId = 10// Replace with real user ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif)

        rvNotif = findViewById(R.id.rvNotif)
        progressBar = findViewById(R.id.progressBar)
        rvNotif.layoutManager = LinearLayoutManager(this)

        loadNotifications()
    }

    private fun loadNotifications() {
        progressBar.visibility = View.VISIBLE
        val call = ApiClient.apiService.getNotifications(ownerId.toString())
        call.enqueue(object : Callback<MaidNotificationResponse> {
            override fun onResponse(
                call: Call<MaidNotificationResponse>,
                response: Response<MaidNotificationResponse>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val notifList = response.body()?.notifications ?: emptyList()
                    rvNotif.adapter = NotifAdapter(notifList) {
                        loadNotifications()
                    }
                } else {
                    Toast.makeText(this@NotifActivity, "Failed to load notifications", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<MaidNotificationResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@NotifActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
