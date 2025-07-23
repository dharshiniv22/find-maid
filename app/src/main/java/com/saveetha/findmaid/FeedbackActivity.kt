package com.saveetha.findmaid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class FeedbackActivity : AppCompatActivity() {

    private lateinit var starViews: List<ImageView>
    private var selectedRating = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_us)

        starViews = listOf(
            findViewById(R.id.star1),
            findViewById(R.id.star2),
            findViewById(R.id.star3),
            findViewById(R.id.star4),
            findViewById(R.id.star5)
        )

        for (i in starViews.indices) {
            starViews[i].setOnClickListener {
                selectedRating = i + 1
                updateStars(selectedRating)

                // Return the selected rating to the previous activity
                val resultIntent = Intent()
                resultIntent.putExtra("selected_rating", selectedRating)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun updateStars(rating: Int) {
        for (i in starViews.indices) {
            starViews[i].setImageResource(
                if (i < rating) R.drawable.ratestar_gold else R.drawable.ratestar
            )
        }
    }
}
