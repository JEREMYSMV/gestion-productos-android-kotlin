package com.isil.android.pa3.data.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

// Guarda, obtiene y elimina el token JWT en SharedPreferences
class TokenManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveSession(token: String, nombre: String, username: String) {
        prefs.edit {
            putString(KEY_TOKEN, token)
            putString(KEY_NOMBRE, nombre)
            putString(KEY_USERNAME, username)
        }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)

    fun getNombre(): String? = prefs.getString(KEY_NOMBRE, null)

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    /** Elimina el token (se usa al cerrar sesión) */
    fun clearToken() {
        prefs.edit { clear() }
    }

    companion object {
        private const val PREFS_NAME = "grupo1pa3_prefs"
        private const val KEY_TOKEN = "auth_token"
        private const val KEY_NOMBRE = "user_nombre"
        private const val KEY_USERNAME = "user_username"
    }
}