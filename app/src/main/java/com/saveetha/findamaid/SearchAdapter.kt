package com.saveetha.findamaid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saveetha.findamaid.server.ServerMaid

class SearchAdapter(
    private var maidList: List<ServerMaid>,
    private val onItemClick: (ServerMaid) -> Unit
) : RecyclerView.Adapter<SearchAdapter.MaidViewHolder>() {

    inner class MaidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val maidName: TextView = itemView.findViewById(R.id.tvMaidName)
        val maidAge: TextView = itemView.findViewById(R.id.tvMaidAge)
        val maidCategory: TextView = itemView.findViewById(R.id.tvCategory)
        val maidSalary: TextView = itemView.findViewById(R.id.tvMaidSalary)
        val maidPhoto: ImageView = itemView.findViewById(R.id.ivMaidPhoto)

        init {
            itemView.setOnClickListener {
                onItemClick(maidList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaidViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return MaidViewHolder(view)
    }

    override fun onBindViewHolder(holder: MaidViewHolder, position: Int) {
        val maid = maidList[position]
        holder.maidName.text = maid.name
        holder.maidAge.text = "Age: ${maid.age}"
        holder.maidCategory.text = "Category: ${maid.category}"
        holder.maidSalary.text = "Salary: ₹${maid.expected_salary}"

        Glide.with(holder.itemView.context)
            .load(maid.photo_url)
            .placeholder(R.drawable.samplemaid)
            .into(holder.maidPhoto)
    }

    override fun getItemCount(): Int = maidList.size

    fun updateList(newList: List<ServerMaid>) {
        maidList = newList
        notifyDataSetChanged()
    }
}
