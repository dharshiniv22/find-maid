package com.saveetha.findamaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.model.Review

class ReviewAdapter(private val reviewList: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    // ViewHolder class for holding views
    class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUserName: TextView = itemView.findViewById(R.id.tvUserName)
        val tvComment: TextView = itemView.findViewById(R.id.tvComment)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
    }

    // Inflate the item_review layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    // Bind data to views
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.tvUserName.text = review.maid_name
        holder.tvComment.text = review.comment
        holder.ratingBar.rating = review.rating.toFloat()
    }

    // Return total number of reviews
    override fun getItemCount(): Int = reviewList.size
}
