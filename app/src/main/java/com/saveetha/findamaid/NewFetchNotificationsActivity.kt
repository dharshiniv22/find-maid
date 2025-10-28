package com.saveetha.findamaid


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class NewFetchNotificationsActivity : AppCompatActivity() {

    private lateinit var notificationsList: ListView
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_fetch_notifications)

        notificationsList = findViewById(R.id.notificationsList)

        fetchNotifications(maidPostId = 10) // Example maid_post_id (replace with real one)
    }

    private fun fetchNotifications(maidPostId: Int) {
        val url = "http://YOUR_IP/findamaid/new_fetch_notifications.php?maid_post_id=$maidPostId"

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@NewFetchNotificationsActivity, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                try {
                    val jsonArray = JSONArray(responseData)
                    val notifications = mutableListOf<String>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val message = obj.getString("message")
                        val time = obj.getString("created_at")
                        notifications.add("$message\n($time)")
                    }

                    runOnUiThread {
                        val adapter = ArrayAdapter(
                            this@NewFetchNotificationsActivity,
                            android.R.layout.simple_list_item_1,
                            notifications
                        )
                        notificationsList.adapter = adapter
                    }

                } catch (e: Exception) {
                    runOnUiThread {
                        Toast.makeText(this@NewFetchNotificationsActivity, "Error parsing data", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
