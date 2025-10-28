package com.saveetha.findamaid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class MaidFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_maid, container, false)

        val buttonPostRequest = view.findViewById<Button>(R.id.buttonPostRequest)

        buttonPostRequest.setOnClickListener {
            val intent = Intent(activity, PostRequest::class.java)
            startActivity(intent)
        }

        return view
    }
}
