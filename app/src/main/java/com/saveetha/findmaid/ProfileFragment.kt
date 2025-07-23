package com.saveetha.findmaid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.server.ProfileResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.RequestBody
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

    // Change to local testing IP for emulator if needed
    private val IMAGE_BASE_URL = "http://10.0.2.2/findmaid/uploads/"

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

        fetchProfileFromServer()

        bookingRequestsButton.setOnClickListener {
            val intent = Intent(requireContext(), UserRequestStatusActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    private fun fetchProfileFromServer() {
        val sharedPref = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "")

        if (username.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Username not found in session", Toast.LENGTH_SHORT).show()
            return
        }

        val requestBody: RequestBody = "username=$username"
            .toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())

        ApiClient.apiService.getProfile(requestBody).enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    val user = response.body()?.data
                    if (user != null) {
                        Log.d("ProfileData", "Loaded: ${user.name}, ${user.email}, ${user.contact_number}, ${user.username}")

                        userName.text = user.name
                        userEmail.text = user.email
                        userContact.text = user.contact_number
                        userUsername.text = "Username: ${user.username}"

                        val imageUrl = IMAGE_BASE_URL + (user.profile_image ?: "")
                        Glide.with(requireContext())
                            .load(imageUrl)
                            .circleCrop()
                            .placeholder(R.drawable.profileimg)
                            .error(R.drawable.profileimg)
                            .into(profileImage)

                        // Save latest profile info to session
                        sharedPref.edit().apply {
                            putString("user_name", user.name)
                            putString("email", user.email)
                            putString("contact_number", user.contact_number)
                            putString("username", user.username)
                            putString("profile_image", user.profile_image)
                            apply()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to load profile", Toast.LENGTH_SHORT).show()
                    Log.e("ProfileAPI", "Response error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("ProfileError", "Throwable: ${t.message}", t)
            }
        })
    }
}
