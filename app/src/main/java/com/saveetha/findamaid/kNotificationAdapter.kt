package com.saveetha.findamaid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.saveetha.findamaid.R
import com.saveetha.findamaid.server.kNotificationServer

class kNotificationAdapter(
    private val notificationList: MutableList<kNotificationServer>,
    private val onStatusChange: (kNotificationServer, String) -> Unit
) : RecyclerView.Adapter<kNotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMaidPost: TextView = itemView.findViewById(R.id.tvMaidPost)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvPincode: TextView = itemView.findViewById(R.id.tvPincode)
        val tvPreferredTime: TextView = itemView.findViewById(R.id.tvPreferredTime)
        val tvNotes: TextView = itemView.findViewById(R.id.tvNotes)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnAccept: Button = itemView.findViewById(R.id.btnAccept)
        val btnReject: Button = itemView.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.kitem_notification, parent, false)
        return NotificationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = notificationList[position]

        holder.tvMaidPost.text = "Maid Post #${item.maid_post_id}"
        holder.tvName.text = item.name
        holder.tvPhone.text = "Phone: ${item.phone_number ?: "N/A"}" // ✅ updated
        holder.tvCategory.text = "Category: ${item.maid_category ?: "N/A"}" // ✅ updated
        holder.tvAddress.text = item.address
        holder.tvPincode.text = item.pincode
        holder.tvPreferredTime.text = "Preferred Time: ${item.preferred_time ?: "N/A"}"
        holder.tvNotes.text = item.notes ?: ""
        holder.tvStatus.text = "Status: ${item.status}"

        // Dynamic status color
        holder.tvStatus.setTextColor(
            when (item.status.lowercase()) {
                "accepted" -> 0xFF4CAF50.toInt()
                "rejected" -> 0xFFF44336.toInt()
                else -> 0xFFFF5722.toInt()
            }
        )

        val buttonsEnabled = item.status.lowercase() == "pending"
        holder.btnAccept.isEnabled = buttonsEnabled
        holder.btnReject.isEnabled = buttonsEnabled

        holder.btnAccept.setOnClickListener { onStatusChange(item, "accepted") }
        holder.btnReject.setOnClickListener { onStatusChange(item, "rejected") }
    }

    override fun getItemCount(): Int = notificationList.size

    fun updateStatus(updatedItem: kNotificationServer) {
        val index = notificationList.indexOfFirst { it.id == updatedItem.id }
        if (index != -1) {
            notificationList[index] = updatedItem
            notifyItemChanged(index)
        }
    }
}
