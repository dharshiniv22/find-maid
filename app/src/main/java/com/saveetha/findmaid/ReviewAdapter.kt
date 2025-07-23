package com.saveetha.findmaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findmaid.model.Review

class ReviewAdapter(private val reviewList: List<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reviewerName: TextView = view.findViewById(R.id.reviewerName)
        val reviewText: TextView = view.findViewById(R.id.reviewText)
        val star1: ImageView = view.findViewById(R.id.star1)
        val star2: ImageView = view.findViewById(R.id.star2)
        val star3: ImageView = view.findViewById(R.id.star3)
        val star4: ImageView = view.findViewById(R.id.star4)
        val star5: ImageView = view.findViewById(R.id.star5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.reviewerName.text = review.reviewerName
        holder.reviewText.text = review.reviewText

        val starViews = listOf(holder.star1, holder.star2, holder.star3, holder.star4, holder.star5)
        for (i in starViews.indices) {
            if (i < review.starRating) {
                starViews[i].setImageResource(R.drawable.ratestar_gold) // filled star
            } else {
                starViews[i].setImageResource(R.drawable.ratestar) // empty star
            }
        }
    }

    override fun getItemCount(): Int = reviewList.size
}
