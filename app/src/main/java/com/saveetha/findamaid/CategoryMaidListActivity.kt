package com.saveetha.findamaid

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.MaidListResponse
import com.saveetha.findamaid.server.MaidData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMaidListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MaidAdapter
    private val maidList = mutableListOf<Maid>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_maid_list)

        recyclerView = findViewById(R.id.recyclerViewMaidList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MaidAdapter(maidList, object : MaidAdapter.OnViewAllReviewsClickListener {
            override fun onViewAllReviewsClicked(maid: Maid) {
                Toast.makeText(this@CategoryMaidListActivity, "Reviews for ${maid.name}", Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView.adapter = adapter

        val category = intent.getStringExtra("category")?.trim() ?: ""
        findViewById<TextView>(R.id.categoryTitle).text = "Maid List for $category"

        if (category.isNotEmpty()) {
            fetchMaidsByCategory(category)
        } else {
            Toast.makeText(this, "Category not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchMaidsByCategory(category: String) {
        ApiClient.apiService.getMaidsByCategory(category)
            .enqueue(object : Callback<MaidListResponse> {
                override fun onResponse(call: Call<MaidListResponse>, response: Response<MaidListResponse>) {
                    if (response.isSuccessful && response.body()?.status == true) {
                        val maids = response.body()?.maids ?: emptyList()
                        maidList.clear()
                        maidList.addAll(maids.map { maidData: MaidData ->
                            Maid(
                                id = maidData.id,
                                name = maidData.name,
                                maid_id = maidData.maid_id,
                                age = maidData.age,
                                qualification = "Experience Unknown",
                                experience = "",
                                contact = "",
                                salary = maidData.expected_salary.toString(),
                                hours = maidData.working_hours,
                                location = "",           // placeholder
                                category = category,      // coming from intent
                                photo = maidData.photo_url ?: "",               // no image in response
                                imageResId = ""           // no image
                            )
                        })
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@CategoryMaidListActivity, "No maids found", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<MaidListResponse>, t: Throwable) {
                    Toast.makeText(this@CategoryMaidListActivity, "Failed to load: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
