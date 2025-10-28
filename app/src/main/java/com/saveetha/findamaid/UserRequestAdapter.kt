package com.saveetha.findamaid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saveetha.findamaid.R
import com.saveetha.findamaid.server.UserRequest

class UserRequestAdapter(private val userRequests: List<UserRequest>) :
    RecyclerView.Adapter<UserRequestAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val maidName: TextView = view.findViewById(R.id.textMaidName)
        val requestStatus: TextView = view.findViewById(R.id.textRequestStatus)
        val requestedAt: TextView = view.findViewById(R.id.textRequestedAt)
        val maidImage: ImageView = view.findViewById(R.id.imageMaidPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_notification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = userRequests.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val request = userRequests[position]
        holder.maidName.text = request.maid_post.name
        holder.requestStatus.text = "Status: ${request.status}"
        holder.requestedAt.text = "Requested at: ${request.requested_at}"
        Glide.with(holder.itemView.context)
            .load(request.maid_post.photo)
            .placeholder(R.drawable.samplemaid)
            .into(holder.maidImage)
    }
}
