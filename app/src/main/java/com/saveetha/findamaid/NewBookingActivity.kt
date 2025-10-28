package com.saveetha.findamaid
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import java.io.IOException

class NewBookingActivity : AppCompatActivity() {

    private lateinit var addressEditText: EditText
    private lateinit var pincodeEditText: EditText
    private lateinit var shareLocationButton: Button
    private lateinit var submitButton: Button
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking) // 👈 your XML file (keep same name or rename with "new")

        addressEditText = findViewById(R.id.addressEditText)
        pincodeEditText = findViewById(R.id.pincodeEditText)
        shareLocationButton = findViewById(R.id.shareLocationButton)
        submitButton = findViewById(R.id.submitButton)

        // 🔹 Handle Google Maps button
        shareLocationButton.setOnClickListener {
            Toast.makeText(this, "Google Maps integration not added yet", Toast.LENGTH_SHORT).show()
            // Later you can open Google Maps Intent here
        }

        // 🔹 Handle Submit button
        submitButton.setOnClickListener {
            val address = addressEditText.text.toString().trim()
            val pincode = pincodeEditText.text.toString().trim()

            if (address.isEmpty() || pincode.isEmpty()) {
                Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show()
            } else {
                sendBookingRequest(address, pincode)
            }
        }
    }

    private fun sendBookingRequest(address: String, pincode: String) {
        val url = "http://YOUR_IP/findamaid/new_booking_submit.php"
        // Replace YOUR_IP with your PC/laptop local IP

        val formBody = FormBody.Builder()
            .add("user_id", "1")          // 🔹 Replace with actual logged-in user_id
            .add("maid_post_id", "10")    // 🔹 Replace with selected maid_post_id
            .add("address", address)
            .add("pincode", pincode)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@NewBookingActivity, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()

                runOnUiThread {
                    Toast.makeText(this@NewBookingActivity, "Booking submitted!", Toast.LENGTH_SHORT).show()

                    // ✅ Open Notifications Activity after booking success
                    val intent = Intent(this@NewBookingActivity, NewFetchNotificationsActivity::class.java)
                    startActivity(intent)
                }
            }
        })
    }
}
