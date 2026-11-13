package com.isil.android.pa3.domain.model

// Sesión del usuario logueado (token generado por la API)
data class Sesion(
    val token: String,
    val nombre: String,
    val username: String
)