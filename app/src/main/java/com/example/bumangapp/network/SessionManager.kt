package com.example.bumangapp.network

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("bumang_prefs", Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val USER_NAME = "user_name"
        const val USER_EMAIL = "user_email"
        const val IS_PREMIUM = "is_premium"
        const val TEXT_SIZE = "text_size"
    }

    fun saveAuthToken(token: String) {
        prefs.edit().putString(USER_TOKEN, token).apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }

    fun saveUser(user: User) {
        prefs.edit().apply {
            putString(USER_NAME, user.name)
            putString(USER_EMAIL, user.email)
            putBoolean(IS_PREMIUM, user.getIsPremium())
            apply()
        }
    }

    fun isPremium(): Boolean {
        return prefs.getBoolean(IS_PREMIUM, false)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    fun saveTextSize(size: String) {
        prefs.edit().putString(TEXT_SIZE, size).apply()
    }

    fun fetchTextSize(): String {
        return prefs.getString(TEXT_SIZE, "Mediano") ?: "Mediano"
    }
}
