package com.isil.android.pa3.domain.repository

import com.isil.android.pa3.domain.model.Sesion

// Contrato que la capa data implementa (AuthRepositoryImpl)
interface AuthRepository {
    suspend fun login(username: String, password: String): Result<Sesion>
    fun obtenerSesion(): Sesion?
    fun logout()
}