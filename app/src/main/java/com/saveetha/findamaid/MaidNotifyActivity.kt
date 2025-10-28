package com.saveetha.findamaid

import MaidNotifyAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.saveetha.findamaid.databinding.MaidRequestsBinding
import com.saveetha.findamaid.server.MaidNotifyDataModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MaidNotifyActivity : AppCompatActivity() {

    private lateinit var binding: MaidRequestsBinding
    private lateinit var adapter: MaidNotifyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MaidRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.requestListView.layoutManager = LinearLayoutManager(this)

        val ownerId = 1 // Replace with actual logged-in owner ID
        fetchMaidNotifications(ownerId)
    }

    private fun fetchMaidNotifications(ownerId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://4n6j9p4w-80.inc1.devtunnels.ms/findamaid/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(MaidNotifyAPI::class.java)

        api.getMaidNotifications(ownerId).enqueue(object : Callback<MaidNotifyResponse> {
            override fun onResponse(
                call: Call<MaidNotifyResponse>,
                response: Response<MaidNotifyResponse>
            ) {
                if (response.isSuccessful && response.body()?.status == true) {
                    val bookings = response.body()?.bookings ?: emptyList()
                    adapter = MaidNotifyAdapter(
                        bookings,
                        onAccept = { id ->
                            Toast.makeText(
                                this@MaidNotifyActivity,
                                "Accepted ID: $id",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onReject = { id ->
                            Toast.makeText(
                                this@MaidNotifyActivity,
                                "Rejected ID: $id",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    binding.requestListView.adapter = adapter
                } else {
                    Toast.makeText(
                        this@MaidNotifyActivity,
                        "No bookings found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<MaidNotifyResponse>, t: Throwable) {
                Toast.makeText(
                    this@MaidNotifyActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

interface MaidNotifyAPI {
    @GET("get_maid_notifications.php")
    fun getMaidNotifications(@Query("owner_id") ownerId: Int): Call<MaidNotifyResponse>
}

data class MaidNotifyResponse(
    val status: Boolean,
    val owner_id: Int,
    val total_bookings: Int,
    val bookings: List<MaidNotifyDataModel>
)
