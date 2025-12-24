package com.example.doanltdd



import android.content.Context
import com.example.doanltdd.Data.Entity.User

class User_SharedPreferences(context: Context) {

    private val prefs =
        context.getSharedPreferences("USER_LOGIN", Context.MODE_PRIVATE)

    fun saveLogin(user: User) {
        prefs.edit().apply {
            putBoolean("isLogin", true)
            putInt("userId", user.id)
            putString("username", user.username)
            putString("email", user.email)
            putString("role", user.role)
            apply()
        }
    }

    fun isLoggedIn(): Boolean =
        prefs.getBoolean("isLogin", false)

    fun getUserId(): Int =
        prefs.getInt("userId", -1)

    fun getUsername(): String? =
        prefs.getString("username", null)

    fun getRole(): String? =
        prefs.getString("role", null)

    fun logout() {
        prefs.edit().clear().apply()
    }
}
