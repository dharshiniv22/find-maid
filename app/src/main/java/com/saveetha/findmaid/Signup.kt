package com.saveetha.findmaid

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.saveetha.findmaid.api.ApiClient
import com.saveetha.findmaid.server.SignupResponse
import com.saveetha.findmaid.util.FileUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class Signup : AppCompatActivity() {

    private lateinit var imageViewProfile: ImageView
    private val PICK_IMAGE_REQUEST = 100
    private val STORAGE_PERMISSION_CODE = 101
    private var selectedImageUri: Uri? = null

    private lateinit var nameEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        imageViewProfile = findViewById(R.id.imageViewProfile)
        signupButton = findViewById(R.id.button2)
        nameEditText = findViewById(R.id.userName)
        usernameEditText = findViewById(R.id.password)
        emailEditText = findViewById(R.id.editTextText2)
        contactEditText = findViewById(R.id.editTextText3)
        passwordEditText = findViewById(R.id.editTextText5)

        imageViewProfile.setOnClickListener {
            checkStoragePermissionAndOpenGallery()
        }

        signupButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val contact = contactEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            when {
                name.isEmpty() -> nameEditText.error = "Name cannot be empty"
                username.isEmpty() -> usernameEditText.error = "Username cannot be empty"
                email.isEmpty() -> emailEditText.error = "Email cannot be empty"
                contact.isEmpty() -> contactEditText.error = "Contact number cannot be empty"
                password.isEmpty() -> passwordEditText.error = "Password cannot be empty"
                else -> signupUser(name, username, email, password, contact)
            }
        }
    }

    private fun signupUser(name: String, username: String, email: String, password: String, contact: String) {
        val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val usernamePart = RequestBody.create("text/plain".toMediaTypeOrNull(), username)
        val emailPart = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
        val passwordPart = RequestBody.create("text/plain".toMediaTypeOrNull(), password)
        val contactPart = RequestBody.create("text/plain".toMediaTypeOrNull(), contact)

        var imagePart: MultipartBody.Part? = null
        if (selectedImageUri != null) {
            val imagePath = FileUtils.getPath(this, selectedImageUri!!)
            if (imagePath != null) {
                val imageFile = File(imagePath)
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), imageFile)
                imagePart = MultipartBody.Part.createFormData("profile_image", imageFile.name, requestFile)
            }
        }

        ApiClient.apiService.signupUser(namePart, usernamePart, emailPart, passwordPart, contactPart, imagePart)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    if (response.isSuccessful && response.body()?.status == true) {
                        val res = response.body()
                        val user = res?.user

                        if (user != null) {
                            // ✅ Save session
                            val sharedPref = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putInt("user_id", user.id)
                                putString("username", user.username)
                                putString("name", user.name)
                                putString("email", user.email)
                                putString("phone", user.contact_number)
                                putString("profile_image", user.profile_image ?: "")
                                apply()
                            }

                            Log.d("Signup", "Signed up as ${user.username}")

                            Toast.makeText(this@Signup, "Signup successful", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this@Signup, HomeFragment::class.java)
                            intent.putExtra("isFromSignup", true)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@Signup, "Signup success but no user data received", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@Signup, "Signup failed: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Toast.makeText(this@Signup, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun checkStoragePermissionAndOpenGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    STORAGE_PERMISSION_CODE
                )
            } else {
                openGallery()
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            } else {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            Toast.makeText(this, "Permission is required to select image", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            imageViewProfile.setImageURI(selectedImageUri)
        }
    }
}
