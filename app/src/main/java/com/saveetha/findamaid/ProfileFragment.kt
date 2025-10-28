package com.saveetha.findamaid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.ProfileResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView
    private lateinit var userContact: TextView
    private lateinit var userUsername: TextView
    private lateinit var bookingRequestsButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        profileImage = view.findViewById(R.id.profileImage)
        userName = view.findViewById(R.id.userName)
        userEmail = view.findViewById(R.id.userEmail)
        userContact = view.findViewById(R.id.userPhone)
        userUsername = view.findViewById(R.id.userUsername)
        bookingRequestsButton = view.findViewById(R.id.bookingRequestsButton)

        bookingRequestsButton.setOnClickListener {
            startActivity(Intent(requireContext(), N_Activity_User_Notification::class.java))
        }

        fetchProfileFromServer()
        return view
    }

    private fun fetchProfileFromServer() {
        val sharedPref = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        if (username.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Username not found in session", Toast.LENGTH_SHORT).show()
            return
        }

        val requestBody: RequestBody =
            "username=$username".toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())

        ApiClient.apiService.getProfile(requestBody).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (!isAdded) return
                if (response.isSuccessful && response.body()?.success == true) {
                    val user = response.body()?.data
                    user?.let {
                        // Set text views
                        userName.text = it.name
                        userEmail.text = it.email
                        userContact.text = it.contact_number
                        userUsername.text = "Username: ${it.username}"

                        // Handle profile image safely
                        val imageUrl = it.profile_image?.takeIf { img -> img.isNotEmpty() }?.let { img ->
                            if (img.startsWith("http")) img
                            else ApiClient.IMAGE_URL.trimEnd('/') + "/" + img.trimStart('/')
                        } ?: "" // fallback to empty if null or empty

                        Log.d("ProfileImageURL", "Final Image URL: $imageUrl")

                        Glide.with(this@ProfileFragment)
                            .load(imageUrl.ifEmpty { R.drawable.profileimg }) // local placeholder if URL empty
                            .placeholder(R.drawable.profileimg)
                            .error(R.drawable.profileimg)
                            .transform(CenterCrop(), RoundedCorners(16))
                            .into(profileImage)

                        // Save user info + user_id locally
                        sharedPref.edit().apply {
                            putString("user_id", it.user_id.toString()) // critical for notifications
                            putString("user_name", it.name)
                            putString("email", it.email)
                            putString("contact_number", it.contact_number)
                            putString("username", it.username)
                            putString("profile_image", it.profile_image)
                            apply()
                        }

                        Log.d("ProfileData", "Profile loaded successfully: ${it.name}")
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT).show()
                    Log.e("ProfileAPI", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                if (!isAdded) return
                Toast.makeText(requireContext(), "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("ProfileError", "Failure: ${t.message}", t)
            }
        })
    }
}
