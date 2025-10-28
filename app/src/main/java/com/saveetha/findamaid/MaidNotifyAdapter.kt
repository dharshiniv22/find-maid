import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saveetha.findamaid.R
import com.saveetha.findamaid.server.MaidNotifyDataModel

class MaidNotifyAdapter(
    private val bookings: List<MaidNotifyDataModel>,
    private val onAccept: (Int) -> Unit,
    private val onReject: (Int) -> Unit
) : RecyclerView.Adapter<MaidNotifyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgMaidPhoto: ImageView = view.findViewById(R.id.imgMaidPhoto)
        val tvMaidName: TextView = view.findViewById(R.id.tvMaidName)
        val tvCategory: TextView = view.findViewById(R.id.tvCategory)
        val tvAddress: TextView = view.findViewById(R.id.tvAddress)
        val tvStatus: TextView = view.findViewById(R.id.tvStatus)
        val tvRequestedAt: TextView = view.findViewById(R.id.tvRequestedAt)
        val btnAccept: Button = view.findViewById(R.id.btnAccept)
        val btnReject: Button = view.findViewById(R.id.btnReject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.maid_notification, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bookings.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = bookings[position]
        holder.tvMaidName.text = item.notifyMaidPost.name
        holder.tvCategory.text = "Category: ${item.notifyMaidPost.category}"
        holder.tvAddress.text = "Address: ${item.address}, ${item.pincode}"
        holder.tvStatus.text = "Status: ${item.status}"
        holder.tvRequestedAt.text = "Requested At: ${item.requested_at}"

        Glide.with(holder.itemView.context)
            .load(item.notifyMaidPost.photo)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.imgMaidPhoto)

        holder.btnAccept.setOnClickListener { onAccept(item.request_id) }
        holder.btnReject.setOnClickListener { onReject(item.request_id) }
    }
}
