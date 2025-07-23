package com.saveetha.findmaid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ClothesWishing : AppCompatActivity(), MaidAdapter.OnViewAllReviewsClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes_washing)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val maidList = listOf<Maid>()

        recyclerView.adapter = MaidAdapter(maidList, this)
    }

    override fun onViewAllReviewsClicked(maid: Maid) {
        val intent = Intent(this, ReviewActivity::class.java)
        intent.putExtra("maidName", maid.name)
        startActivity(intent)
    }
}
