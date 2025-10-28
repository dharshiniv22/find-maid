package com.saveetha.findamaid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.saveetha.findamaid.R
import com.saveetha.findamaid.server.NotificationItem

class NUserNotificationAdapter(
    private val context: Context,
    private val notificationList: MutableList<NotificationItem>
) : RecyclerView.Adapter<NUserNotificationAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivMaidImage: ImageView = view.findViewById(R.id.iv_maid_image)
        val tvMaidName: TextView = view.findViewById(R.id.tv_maid_name)
        val tvCategory: TextView = view.findViewById(R.id.tv_category)
        val tvRequestDate: TextView = view.findViewById(R.id.tv_request_date)
        val tvStatus: TextView = view.findViewById(R.id.tv_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.n_item_user_notification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = notificationList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notificationList[position]

        holder.tvMaidName.text = "Maid Name: ${item.maidName ?: "N/A"}"
        holder.tvCategory.text = "Category: ${item.category ?: "N/A"}"
        holder.tvRequestDate.text = "Requested Date: ${item.requestDate ?: "N/A"}"
        holder.tvStatus.text = "Status: ${item.status ?: "N/A"}"

        // Load image directly (photo_url from backend is already a full URL)
        Glide.with(context)
            .load(item.photo_url ?: "")
            .placeholder(R.drawable.samplemaid3)
            .error(R.drawable.samplemaid3)
            .transform(CenterCrop(), RoundedCorners(16))
            .into(holder.ivMaidImage)
    }
}
