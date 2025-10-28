package com.saveetha.findamaid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saveetha.findamaid.api.ApiClient

class MaidAdapter(
    private val maidList: List<Maid>,
    private val listener: OnViewAllReviewsClickListener
) : RecyclerView.Adapter<MaidAdapter.MaidViewHolder>() {

    interface OnViewAllReviewsClickListener {
        fun onViewAllReviewsClicked(maid: Maid)
    }

    inner class MaidViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val maidImage: ImageView = view.findViewById(R.id.maid_image)
        val maidName: TextView = view.findViewById(R.id.maid_name)
        val maidAge: TextView = view.findViewById(R.id.maid_age)
        val maidQualification: TextView = view.findViewById(R.id.maid_qualification)
        val btnMoreDetails: Button = view.findViewById(R.id.btn_more_details)
        val btnViewReviews: Button = view.findViewById(R.id.btn_view_reviews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaidViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.maidcard, parent, false)
        return MaidViewHolder(view)
    }

    override fun onBindViewHolder(holder: MaidViewHolder, position: Int) {
        val maid = maidList[position]
        val context = holder.itemView.context

        if (!maid.photo.isNullOrEmpty()) {
            Glide.with(context)
                .load(maid.photo)  // Full URL directly
                .placeholder(R.drawable.samplemaid)
                .error(R.drawable.samplemaid)
                .into(holder.maidImage)
        } else {
            holder.maidImage.setImageResource(R.drawable.samplemaid)
        }



        // Set text data
        holder.maidName.text = maid.name
        holder.maidAge.text = "Age : ${maid.age} years"
        holder.maidQualification.text = "Salary : ₹${maid.salary} / ${maid.hours}"

        // Open Maid Details
        holder.btnMoreDetails.setOnClickListener {
            val intent = Intent(context, MaidDetailActivity::class.java)
            intent.putExtra("maid_id", maid.id) // ✅ Use post ID from DB
            context.startActivity(intent)
        }
        // Open Reviews
        holder.btnViewReviews.setOnClickListener {
            val intent = Intent(context, ReviewActivity::class.java)
            intent.putExtra("maid_id", maid.maid_id)
            intent.putExtra("post_id", maid.id)
            intent.putExtra("maid_name", maid.name)
            context.startActivity(intent)

            listener.onViewAllReviewsClicked(maid)
        }
    }

    override fun getItemCount(): Int = maidList.size
}
