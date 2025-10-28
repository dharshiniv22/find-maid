package com.saveetha.findamaid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.saveetha.findamaid.api.ApiClient
import com.saveetha.findamaid.server.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPage : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        userName = findViewById(R.id.userName)
        password = findViewById(R.id.password)
        val loginBtn = findViewById<Button>(R.id.button)
        val signupBtn = findViewById<Button>(R.id.button1)

        loginBtn.setOnClickListener {
            val userNameStr = userName.text.toString().trim()
            val passwordStr = password.text.toString().trim()
            if (userNameStr.isEmpty()) { userName.error = "Username can't be empty"; return@setOnClickListener }
            if (passwordStr.isEmpty()) { password.error = "Password can't be empty"; return@setOnClickListener }

            loginUser(userNameStr, passwordStr)
        }

        signupBtn.setOnClickListener {
            startActivity(Intent(this, Signup::class.java))
        }
    }

    private fun loginUser(username: String, password: String) {
        ApiClient.apiService.loginUser(username, password)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse?.status == true && loginResponse.data != null) {
                            val user = loginResponse.data

                            // Save user info to SharedPreferences
                            val sp = getSharedPreferences("UserSession", Context.MODE_PRIVATE)
                            sp.edit().apply {
                                putInt("user_id", user.id)
                                putString("username", username) // Save actual login username
                                putString("email", user.email ?: "")
                                putString("contact_number", user.contact_number ?: "")
                                putBoolean("is_logged_in", true)
                                apply()
                            }

                            Toast.makeText(this@LoginPage, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginPage, NavigationActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginPage, loginResponse?.message ?: "Invalid credentials", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginPage, "Server Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginPage, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
