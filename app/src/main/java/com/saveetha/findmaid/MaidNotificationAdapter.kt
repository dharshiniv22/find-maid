package com.saveetha.findmaid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saveetha.findmaid.R
import com.saveetha.findmaid.server.MaidRequestData

class MaidNotificationAdapter(
    private val requestList: List<MaidRequestData>
) : RecyclerView.Adapter<MaidNotificationAdapter.RequestViewHolder>() {

    class RequestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageMaidPhoto: ImageView = view.findViewById(R.id.imageMaidPhoto)
        val textMaidName: TextView = view.findViewById(R.id.textMaidName)
        val textRequestStatus: TextView = view.findViewById(R.id.textRequestStatus)
        val textRequestedAt: TextView = view.findViewById(R.id.textRequestedAt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_notification, parent, false)
        return RequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requestList[position]

        holder.textMaidName.text = request.maid_post.name
        holder.textRequestStatus.text = "Status: ${request.status}"
        holder.textRequestedAt.text = "Requested at: ${request.requested_at}"

        Glide.with(holder.itemView.context)
            .load(request.maid_post.photo)
            .placeholder(R.drawable.samplemaid)
            .into(holder.imageMaidPhoto)
    }

    override fun getItemCount(): Int = requestList.size
}
