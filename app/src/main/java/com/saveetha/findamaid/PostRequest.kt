package com.saveetha.findamaid

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.api.ApiService
import com.saveetha.findamaid.server.PostRequestResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class PostRequest : AppCompatActivity() {

    private lateinit var imageViewUpload: ImageView
    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText
    private lateinit var editTextLocation: EditText
    private lateinit var editTextExperience: EditText
    private lateinit var editTextLanguage: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextSalary: EditText
    private lateinit var spinnerHours: Spinner
    private lateinit var spinnerCategory: Spinner
    private lateinit var buttonSubmit: Button

    private val PICK_IMAGE_REQUEST = 1
    private val PERMISSION_REQUEST_CODE = 100
    private var selectedImageUri: Uri? = null
    private var userId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_request)

        val sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE)
        userId = sharedPreferences.getInt("user_id", -1)

        // Initialize views
        imageViewUpload = findViewById(R.id.imageViewUpload)
        editTextName = findViewById(R.id.editTextName)
        editTextAge = findViewById(R.id.editTextAge)
        editTextLocation = findViewById(R.id.editTextLocation)
        editTextExperience = findViewById(R.id.editTextExperience)
        editTextLanguage = findViewById(R.id.editTextLanguage)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextSalary = findViewById(R.id.editTextSalary)
        spinnerHours = findViewById(R.id.spinnerHours)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        buttonSubmit = findViewById(R.id.buttonSubmit)

        // Spinner setup
        val hoursOptions = arrayOf("1 hour", "2 hours", "3 hours", "4 hours", "5+ hours")
        val categoryOptions = arrayOf("Clothes Washing", "Cooking", "Home Cleaning", "Dishwashing", "All Work")
        spinnerHours.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, hoursOptions)
        spinnerCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryOptions)

        // Image upload click
        imageViewUpload.setOnClickListener {
            if (hasImagePermission()) openImagePicker()
            else requestImagePermission()
        }

        // Submit button click
        buttonSubmit.setOnClickListener {
            val name = editTextName.text.toString().trim()
            val age = editTextAge.text.toString().trim()
            val location = editTextLocation.text.toString().trim()
            val experience = editTextExperience.text.toString().trim()
            val language = editTextLanguage.text.toString().trim()
            val phone = editTextPhone.text.toString().trim()
            val salary = editTextSalary.text.toString().trim()
            val hours = spinnerHours.selectedItem.toString()
            val category = spinnerCategory.selectedItem.toString()

            if (userId == -1 || name.isEmpty() || age.isEmpty() || location.isEmpty() ||
                experience.isEmpty() || language.isEmpty() || phone.isEmpty() ||
                salary.isEmpty() || selectedImageUri == null
            ) {
                Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            submitMaidRequest(userId, name, age, location, experience, language, phone, salary, hours, category)
        }
    }

    private fun hasImagePermission(): Boolean {
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestImagePermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), PERMISSION_REQUEST_CODE)
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) openImagePicker()
            else Toast.makeText(this, "Permission Denied. Cannot select image.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedImageUri = data?.data
            imageViewUpload.setImageURI(selectedImageUri)
        }
    }

    private fun submitMaidRequest(
        userId: Int, name: String, age: String, location: String, experience: String,
        language: String, phone: String, salary: String, hours: String, category: String
    ) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Submitting...")
        progressDialog.show()

        val file = uriToFile(selectedImageUri!!)
        val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
        val photoPart = MultipartBody.Part.createFormData("photo", file.name, requestFile)

        val userIdPart = RequestBody.create("text/plain".toMediaTypeOrNull(), userId.toString())
        val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
        val agePart = RequestBody.create("text/plain".toMediaTypeOrNull(), age)
        val locationPart = RequestBody.create("text/plain".toMediaTypeOrNull(), location)
        val experiencePart = RequestBody.create("text/plain".toMediaTypeOrNull(), experience)
        val languagePart = RequestBody.create("text/plain".toMediaTypeOrNull(), language)
        val phonePart = RequestBody.create("text/plain".toMediaTypeOrNull(), phone)
        val salaryPart = RequestBody.create("text/plain".toMediaTypeOrNull(), salary)
        val hoursPart = RequestBody.create("text/plain".toMediaTypeOrNull(), hours)
        val categoryPart = RequestBody.create("text/plain".toMediaTypeOrNull(), category)

        val uploadCall = ApiClient.apiService.uploadMaidDetails(
            userIdPart, namePart, agePart, locationPart, experiencePart,
            languagePart, phonePart, salaryPart, hoursPart, categoryPart, photoPart
        )

        uploadCall.enqueue(object : Callback<PostRequestResponse> {
            override fun onResponse(call: Call<PostRequestResponse>, response: Response<PostRequestResponse>) {
                progressDialog.dismiss()
                if (response.isSuccessful && response.body()?.status == true) {
                    Toast.makeText(this@PostRequest, "Request Submitted Successfully!", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this@PostRequest, response.body()?.message ?: "Upload Failed", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<PostRequestResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(this@PostRequest, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun uriToFile(uri: Uri): File {
        val inputStream = contentResolver.openInputStream(uri)!!
        val file = File(cacheDir, getFileName(uri))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        return file
    }

    private fun getFileName(uri: Uri): String {
        var name = "image.jpg"
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex >= 0) name = it.getString(nameIndex)
        }
        return name
    }
}
