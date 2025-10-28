package com.saveetha.findamaid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.adapter.UserRequestAdapter
import com.saveetha.findamaid.server.UserRequest
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserNotificationActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserRequestAdapter
    private val requestList = mutableListOf<UserRequest>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_notifications)

        recyclerView = findViewById(R.id.userRequestListView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchMaidNotifications(userId = 1) // Replace with actual logged-in user_id
    }

    private fun fetchMaidNotifications(userId: Int) {
        val url = "http://10.0.2.2/findamaid/get_user_notification.php?user_id=$userId"

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@UserNotificationActivity, "Network error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val jsonObj = JSONObject(jsonString)
                    val notificationsArray = jsonObj.getJSONArray("notifications")
                    val listType = object : TypeToken<List<UserRequest>>() {}.type
                    val fetchedList: List<UserRequest> = Gson().fromJson(notificationsArray.toString(), listType)

                    runOnUiThread {
                        requestList.clear()
                        requestList.addAll(fetchedList)
                        adapter = UserRequestAdapter(requestList)
                        recyclerView.adapter = adapter
                    }
                }
            }
        })
    }
}
