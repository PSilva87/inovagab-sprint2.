package br.com.fiap.inovagab

import android.content.Context

class SessionManager(context: Context) {
    private val preferences = context.getSharedPreferences("inovagab_session", Context.MODE_PRIVATE)

    fun save(token: String, role: String) {
        preferences.edit().putString("token", token).putString("role", role).apply()
    }

    fun token(): String? = preferences.getString("token", null)
    fun role(): String = preferences.getString("role", "") ?: ""
    fun clear() = preferences.edit().clear().apply()
}
