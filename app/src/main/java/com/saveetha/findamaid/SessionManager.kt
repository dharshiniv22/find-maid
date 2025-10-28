package com.saveetha.findamaid

import android.content.Context

object SessionManager {
    private const val PREF_NAME = "UserSession"

    fun saveUser(context: Context, userId: Int, userName: String, email: String, contact: String) {
        val sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sp.edit().apply {
            putInt("user_id", userId)
            putString("user_name", userName)
            putString("email", email)
            putString("contact_number", contact)
            putBoolean("is_logged_in", true)
            apply()
        }
    }

    fun getUserId(context: Context): Int {
        val sp = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sp.getInt("user_id", 0)
    }
}
