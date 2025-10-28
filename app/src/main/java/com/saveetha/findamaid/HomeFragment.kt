package com.saveetha.findamaid

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

        // Category navigation
        clothesButton.setOnClickListener { moveToCategoryPage("Clothes Washing") }
        cookingImage.setOnClickListener { moveToCategoryPage("Cooking") }
        homeCleaningImage.setOnClickListener { moveToCategoryPage("Home Cleaning") }
        dishwashingImage.setOnClickListener { moveToCategoryPage("Dishwashing") }
        allWorkImage.setOnClickListener { moveToCategoryPage("All work") }

        // Open notifications page
        notificationBell.setOnClickListener {
            startActivity(Intent(requireContext(), kNotificationsActivity::class.java))
        }

        // Search bar focus listener
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                startActivity(Intent(requireContext(), SearchBar::class.java))
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
