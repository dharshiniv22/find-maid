package com.saveetha.findamaid

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.LocationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var pincodeEditText: EditText
    private lateinit var dateEditText: EditText
    private lateinit var notesEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var shareLocationButton: Button

    private var userId: Int = -1
    private var maidPostId: Int = -1
    private var latitude: String = ""
    private var longitude: String = ""

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        // Initialize Views
        nameEditText = findViewById(R.id.nameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        pincodeEditText = findViewById(R.id.pincodeEditText)
        dateEditText = findViewById(R.id.dateEditText)
        notesEditText = findViewById(R.id.notesEditText)
        submitButton = findViewById(R.id.submitButton)
        shareLocationButton = findViewById(R.id.shareLocationButton)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Get user_id from SharedPreferences
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        userId = sharedPref.getInt("user_id", -1)

        // Get maid_post_id from Intent
        maidPostId = intent.getIntExtra("maid_post_id", -1)

        if (userId == -1 || maidPostId == -1) {
            Toast.makeText(this, "User or maid info missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Date & Time Picker
        dateEditText.setOnClickListener { showDateTimePicker() }

        // Fetch current location on button click
        shareLocationButton.setOnClickListener { fetchCurrentLocation() }

        // Submit Booking
        submitButton.setOnClickListener { submitBooking() }
    }

    /** Fetch current GPS location and auto-fill address/pincode */
    private fun fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                latitude = location.latitude.toString()
                longitude = location.longitude.toString()

                // Reverse Geocode
                try {
                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (!addresses.isNullOrEmpty()) {
                        addressEditText.setText(addresses[0].getAddressLine(0) ?: "")
                        pincodeEditText.setText(addresses[0].postalCode ?: "")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Failed to fetch address", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Unable to fetch location. Try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /** Show Date & Time picker */
    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        calendar.set(Calendar.MINUTE, minute)
                        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                        dateEditText.setText(sdf.format(calendar.time))
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    /** Submit booking to PHP API */
    private fun submitBooking() {
        val name = nameEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()
        val address = addressEditText.text.toString().trim()
        val pincode = pincodeEditText.text.toString().trim()
        val preferredTime = dateEditText.text.toString().trim()
        val notes = notesEditText.text.toString().trim()

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || pincode.length != 6 || preferredTime.isEmpty()) {
            Toast.makeText(this, "Enter all required fields correctly", Toast.LENGTH_SHORT).show()
            return
        }

        val params = HashMap<String, String>()
        params["user_id"] = userId.toString()
        params["maid_post_id"] = maidPostId.toString()
        params["name"] = name
        params["phone_number"] = phone
        params["address"] = address
        params["pincode"] = pincode
        params["latitude"] = latitude
        params["longitude"] = longitude
        params["preferred_time"] = preferredTime
        params["notes"] = notes

        ApiClient.apiService.submitAddress(params).enqueue(object : Callback<LocationResponse> {
            override fun onResponse(call: Call<LocationResponse>, response: Response<LocationResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.status == true) {
                        Toast.makeText(this@BookingActivity, "✅ Booking submitted!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@BookingActivity, "❌ Failed: ${body?.message ?: "Unknown error"}", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@BookingActivity, "❌ Server Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Toast.makeText(this@BookingActivity, "❌ Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    /** Handle location permission result */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocation()
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
