package com.saveetha.findmaid

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.server.LocationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BookingActivity : AppCompatActivity() {

    private lateinit var shareLocationButton: Button
    private lateinit var submitButton: Button
    private lateinit var addressEditText: EditText
    private lateinit var pincodeEditText: EditText

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var userId: Int = -1
    private var maidPostId: Int = -1

    private var latitude: String = ""
    private var longitude: String = ""

    companion object {
        private const val LOCATION_PERMISSION_REQUEST = 101
        private const val GPS_REQUEST_CODE = 102
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        shareLocationButton = findViewById(R.id.shareLocationButton)
        submitButton = findViewById(R.id.submitButton)
        addressEditText = findViewById(R.id.addressEditText)
        pincodeEditText = findViewById(R.id.pincodeEditText)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // ✅ Get user_id from SharedPreferences
        val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        userId = sharedPref.getInt("user_id", -1)

        // ✅ Get maid_post_id from Intent
        maidPostId = intent.getIntExtra("maid_post_id", -1)

        if (userId == -1 || maidPostId == -1) {
            Toast.makeText(this, "User or maid information missing", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        shareLocationButton.setOnClickListener {
            checkAndPromptEnableGPS()
        }

        submitButton.setOnClickListener {
            val address = addressEditText.text.toString().trim()
            val pincode = pincodeEditText.text.toString().trim()

            if (address.isEmpty() || pincode.length != 6) {
                Toast.makeText(this, "Please enter valid address and 6-digit pincode", Toast.LENGTH_SHORT).show()
            } else {
                submitLocation(address, pincode, latitude, longitude)
            }
        }
    }

    private fun checkAndPromptEnableGPS() {
        val locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)

        val settingsClient = LocationServices.getSettingsClient(this)
        val task = settingsClient.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            checkLocationPermission()
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                try {
                    exception.startResolutionForResult(this, GPS_REQUEST_CODE)
                } catch (sendEx: IntentSender.SendIntentException) {
                    Toast.makeText(this, "Could not prompt to enable GPS", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Enable GPS manually", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
        } else {
            fetchCurrentLocation()
        }
    }

    private fun fetchCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
            return
        }

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { location ->
                if (location != null) {
                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()

                    val geocoder = Geocoder(this, Locale.getDefault())
                    val addresses: List<Address>? =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)

                    if (!addresses.isNullOrEmpty()) {
                        val fullAddress = addresses[0].getAddressLine(0)
                        val postalCode = addresses[0].postalCode
                        addressEditText.setText(fullAddress ?: "")
                        pincodeEditText.setText(postalCode ?: "")
                    }

                    Toast.makeText(this, "📍 Location fetched", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Location is null", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to get location: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun submitLocation(address: String, pincode: String, latitude: String, longitude: String) {
        val params = HashMap<String, String>()
        params["user_id"] = userId.toString()
        params["maid_post_id"] = maidPostId.toString()
        params["address"] = address
        params["pincode"] = pincode
        params["latitude"] = latitude
        params["longitude"] = longitude

        ApiClient.apiService.submitAddress(params).enqueue(object : Callback<LocationResponse> {
            override fun onResponse(call: Call<LocationResponse>, response: Response<LocationResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.status == true) {
                        Toast.makeText(this@BookingActivity, "✅ Booking submitted!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@BookingActivity,
                            "❌ Failed: ${body?.message ?: "Unknown error"}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("Booking", "❌ Server response error: $errorBody")
                    Toast.makeText(
                        this@BookingActivity,
                        "❌ Server Error: ${response.code()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<LocationResponse>, t: Throwable) {
                Log.e("Booking", "❌ Network Error", t)
                Toast.makeText(this@BookingActivity, "❌ Network Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            fetchCurrentLocation()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GPS_REQUEST_CODE) {
            checkLocationPermission()
        }
    }
}
