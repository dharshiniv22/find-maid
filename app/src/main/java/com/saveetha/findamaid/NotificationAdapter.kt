package com.saveetha.findamaid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saveetha.findamaid.R
import com.saveetha.findamaid.server.ServerNotification

class NotificationAdapter(
    private val notifications: List<ServerNotification>,
    private val onAcceptClick: (ServerNotification) -> Unit,
    private val onRejectClick: (ServerNotification) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.maid_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.bind(notifications[position])
    }

    override fun getItemCount(): Int = notifications.size

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgMaidPhoto: ImageView = itemView.findViewById(R.id.imgMaidPhoto)
        private val tvMaidName: TextView = itemView.findViewById(R.id.tvMaidName)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val tvRequestedAt: TextView = itemView.findViewById(R.id.tvRequestedAt)
        private val btnAccept: Button = itemView.findViewById(R.id.btnAccept)
        private val btnReject: Button = itemView.findViewById(R.id.btnReject)

        fun bind(notification: ServerNotification) {
            tvMaidName.text = notification.maid_post.name
            tvCategory.text = notification.maid_post.category
            tvAddress.text = notification.address
            tvStatus.text = "Status: ${notification.status.capitalize()}"
            tvRequestedAt.text = "Requested at: ${notification.requested_at}"

            // Load maid photo
            if (!notification.maid_post.photo_url.isNullOrEmpty()) {
                Glide.with(itemView.context)
                    .load(notification.maid_post.photo_url)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgMaidPhoto)
            } else {
                imgMaidPhoto.setImageResource(R.mipmap.ic_launcher)
            }

            // Click Listeners
            btnAccept.setOnClickListener {
                onAcceptClick(notification)
            }

            btnReject.setOnClickListener {
                onRejectClick(notification)
            }
        }
    }
}
