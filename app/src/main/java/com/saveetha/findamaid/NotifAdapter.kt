package com.saveetha.findamaid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.MaidRequestData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotifAdapter(
    private val notifList: List<MaidRequestData>,
    private val onStatusUpdated: () -> Unit
) : RecyclerView.Adapter<NotifAdapter.NotifViewHolder>() {

    inner class NotifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMaidPhoto: ImageView = itemView.findViewById(R.id.imgMaidPhoto)
        val tvMaidName: TextView = itemView.findViewById(R.id.tvMaidName)
        val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val tvRequestedAt: TextView = itemView.findViewById(R.id.tvRequestedAt)
        val btnAccept: Button = itemView.findViewById(R.id.btnAccept)
        val btnReject: Button = itemView.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_maid_notification, parent, false)
        return NotifViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotifViewHolder, position: Int) {
        val notif = notifList[position]
        val context = holder.itemView.context

        holder.tvMaidName.text = notif.maid_post.name
        holder.tvCategory.text = notif.maid_post.category
        holder.tvAddress.text = "${notif.address}, ${notif.pincode}"
        holder.tvStatus.text = "Status: ${notif.status.replaceFirstChar { it.uppercase() }}"
        holder.tvRequestedAt.text = "Requested At: ${notif.requested_at}"

        Glide.with(context)
            .load(notif.maid_post.photo)
            .placeholder(R.drawable.samplemaid)
            .into(holder.imgMaidPhoto)

        if (notif.status == "pending") {
            holder.btnAccept.visibility = View.VISIBLE
            holder.btnReject.visibility = View.VISIBLE

            holder.btnAccept.setOnClickListener {
                updateStatus(notif.request_id, "accepted", context)
            }

            holder.btnReject.setOnClickListener {
                updateStatus(notif.request_id, "rejected", context)
            }
        } else {
            holder.btnAccept.visibility = View.GONE
            holder.btnReject.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = notifList.size

    private fun updateStatus(requestId: Int, status: String, context: Context) {
        ApiClient.apiService.updateBookingStatus(requestId, status)
            .enqueue(object : Callback<Map<String, Any>> {
                override fun onResponse(
                    call: Call<Map<String, Any>>,
                    response: Response<Map<String, Any>>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Updated to $status", Toast.LENGTH_SHORT).show()
                        onStatusUpdated()
                    } else {
                        Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
