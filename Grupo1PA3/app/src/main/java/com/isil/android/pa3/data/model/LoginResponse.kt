package com.isil.android.pa3.data.model

/** Respuesta de POST api/auth/login */
data class LoginResponse(
    val token: String,
    val usuario: UsuarioResponse
)

data class UsuarioResponse(
    val id: Int,
    val nombre: String,
    val username: String
)