package com.isil.android.pa3.data.repository

import com.isil.android.pa3.data.datasource.ApiService
import com.isil.android.pa3.data.datasource.TokenManager
import com.isil.android.pa3.data.model.LoginRequest
import com.isil.android.pa3.domain.model.Sesion
import com.isil.android.pa3.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun login(username: String, password: String): Result<Sesion> {
        return safeApiCall { apiService.login(LoginRequest(username, password)) }
            .map { response ->
                // Guardamos el token generado por la API
                tokenManager.saveSession(
                    token = response.token,
                    nombre = response.usuario.nombre,
                    username = response.usuario.username
                )
                Sesion(response.token, response.usuario.nombre, response.usuario.username)
            }
    }

    override fun obtenerSesion(): Sesion? {
        val token = tokenManager.getToken() ?: return null
        return Sesion(
            token = token,
            nombre = tokenManager.getNombre().orEmpty(),
            username = tokenManager.getUsername().orEmpty()
        )
    }

    /** Cierre de sesión: elimina el token guardado */
    override fun logout() {
        tokenManager.clearToken()
    }
}