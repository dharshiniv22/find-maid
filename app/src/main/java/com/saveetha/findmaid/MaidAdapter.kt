package com.saveetha.findmaid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.maidcard, parent, false)
        return MaidViewHolder(view)
    }

    override fun onBindViewHolder(holder: MaidViewHolder, position: Int) {
        val maid = maidList[position]
        val context = holder.itemView.context

        // ✅ Load image with Glide or fallback image
        if (!maid.photo.isNullOrEmpty()) {
            Glide.with(context)
                .load(maid.photo)
                .placeholder(R.drawable.samplemaid)
                .error(R.drawable.samplemaid)
                .into(holder.maidImage)

            // Accessibility: describe image
            holder.maidImage.contentDescription = "Photo of maid ${maid.name}"
        } else {
            holder.maidImage.setImageResource(R.drawable.samplemaid)
            holder.maidImage.contentDescription = "Default photo of maid"
        }

        // ✅ Set maid data to views
        holder.maidName.text = maid.name
        holder.maidAge.text = "Age : ${maid.age} years"
        holder.maidQualification.text = "Salary : ₹${maid.salary} / ${maid.hours}"

        // ✅ Accessibility: full item description for screen readers
        holder.itemView.contentDescription =
            "Maid ${maid.name}, age ${maid.age}, charges ₹${maid.salary} for ${maid.hours}"

        // ✅ 'More Details' button
        holder.btnMoreDetails.setOnClickListener {
            val intent = Intent(context, MaidDetailActivity::class.java)
            intent.putExtra("maid_id", maid.id)
            context.startActivity(intent)
        }

        // ✅ 'View All Reviews' button
        holder.btnViewReviews.setOnClickListener {
            val intent = Intent(context, ReviewActivity::class.java)
            intent.putExtra("maid_id", maid.id)
            intent.putExtra("maid_name", maid.name)
            context.startActivity(intent)
            listener.onViewAllReviewsClicked(maid)
        }
    }

    override fun getItemCount(): Int = maidList.size
}
