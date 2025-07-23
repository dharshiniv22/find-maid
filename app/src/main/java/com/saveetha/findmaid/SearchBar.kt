package com.saveetha.findmaid

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class SearchBar : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var noResultsText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MaidAdapter
    private val maidList = mutableListOf<Maid>()
    private val client = OkHttpClient()

    private val BASE_URL = "https://jqlfcv99-80.inc1.devtunnels.ms/findmaid/search_maid.php" // Replace with your actual IP

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_bar)

        searchEditText = findViewById(R.id.searchEditText)
        noResultsText = findViewById(R.id.noResultsText)
        recyclerView = findViewById(R.id.recyclerViewSearchResults)

        adapter = MaidAdapter(maidList, object : MaidAdapter.OnViewAllReviewsClickListener {
            override fun onViewAllReviewsClicked(maid: Maid) {
                // Optional click behavior
            }
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                performSearch()
                true
            } else {
                false
            }
        }
    }

    private fun performSearch() {
        val query = searchEditText.text.toString().trim()
        if (query.isEmpty()) {
            Toast.makeText(this, "Please enter search text", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "$BASE_URL?filter=${query.replace(" ", "%20")}"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@SearchBar, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(this@SearchBar, "Server Error", Toast.LENGTH_SHORT).show()
                        }
                        return
                    }

                    val json = JSONObject(response.body?.string() ?: "")
                    val status = json.optBoolean("status", false)
                    val results = json.optJSONArray("data") ?: JSONArray()

                    runOnUiThread {
                        if (status && results.length() > 0) {
                            noResultsText.visibility = View.GONE
                            maidList.clear()

                            for (i in 0 until results.length()) {
                                val obj = results.getJSONObject(i)
                                maidList.add(
                                    Maid(
                                        id = obj.getInt("id"),
                                        name = obj.getString("name"),
                                        age = obj.getInt("age"),
                                        qualification = "",
                                        experience = "",
                                        contact = "",
                                        salary = obj.getDouble("expected_salary").toString(),
                                        hours = obj.getString("working_hours"),
                                        location = obj.getString("location"),
                                        category = obj.getString("category"),
                                        photo = obj.optString("photo_url"),
                                        imageResId = obj.optString("photo_url")
                                    )
                                )
                            }

                            adapter.notifyDataSetChanged()
                        } else {
                            maidList.clear()
                            adapter.notifyDataSetChanged()
                            noResultsText.visibility = View.VISIBLE
                            noResultsText.text = "No results found for \"$query\""
                        }
                    }
                }
            }
        })
    }
}
