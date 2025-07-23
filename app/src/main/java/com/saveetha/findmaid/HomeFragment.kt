package com.saveetha.findmaid

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val clothesButton = view.findViewById<ImageView>(R.id.clothes_button)
        val cookingImage = view.findViewById<ImageView>(R.id.cooking)
        val homeCleaningImage = view.findViewById<ImageView>(R.id.homecleaning)
        val dishwashingImage = view.findViewById<ImageView>(R.id.dishwishing)
        val allWorkImage = view.findViewById<ImageView>(R.id.AllServices)
        val notificationBell = view.findViewById<ImageView>(R.id.notificationButton)
        val searchView = view.findViewById<SearchView>(R.id.SearchView)

        clothesButton.setOnClickListener {
            moveToCategoryPage("Clothes Washing")
        }

        cookingImage.setOnClickListener {
            moveToCategoryPage("Cooking")
        }

        homeCleaningImage.setOnClickListener {
            moveToCategoryPage("Home Cleaning")
        }

        dishwashingImage.setOnClickListener {
            moveToCategoryPage("Dishwashing")
        }

        allWorkImage.setOnClickListener {
            moveToCategoryPage("All work")
        }

        notificationBell.setOnClickListener {
            val intent = Intent(requireContext(), MaidRequestsActivity::class.java)
            startActivity(intent)
        }

        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val intent = Intent(requireContext(), SearchBar::class.java)
                startActivity(intent)
                searchView.clearFocus()
            }
        }

        return view
    }

    private fun moveToCategoryPage(category: String) {
        val intent = Intent(requireContext(), CategoryMaidListActivity::class.java)
        intent.putExtra("category", category)
        startActivity(intent)
    }
}
