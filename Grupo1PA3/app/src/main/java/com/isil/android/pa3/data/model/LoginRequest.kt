package com.isil.android.pa3.data.model

/** Body enviado a POST api/auth/login */
data class LoginRequest(
    val username: String,
    val password: String
)