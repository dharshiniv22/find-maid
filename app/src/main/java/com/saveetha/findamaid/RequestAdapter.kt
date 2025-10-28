package com.saveetha.findamaid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class RequestAdapter(
    private val context: Context,
    private val users: List<String>,
    private val onActionClick: (String, String) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = users.size

    override fun getItem(position: Int): Any = users[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.request_item, parent, false)

        val usernameTextView = view.findViewById<TextView>(R.id.usernameTextView)
        val acceptButton = view.findViewById<Button>(R.id.acceptButton)
        val rejectButton = view.findViewById<Button>(R.id.rejectButton)

        val username = users[position]
        usernameTextView.text = username

        acceptButton.setOnClickListener {
            Toast.makeText(context, "$username Accepted", Toast.LENGTH_SHORT).show()
            onActionClick(username, "Accept")
        }

        rejectButton.setOnClickListener {
            Toast.makeText(context, "$username Rejected", Toast.LENGTH_SHORT).show()
            onActionClick(username, "Reject")
        }

        return view
    }
}
