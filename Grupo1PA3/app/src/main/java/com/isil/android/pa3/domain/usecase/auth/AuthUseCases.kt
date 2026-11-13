package com.isil.android.pa3.domain.usecase.auth

import com.isil.android.pa3.domain.model.Sesion
import com.isil.android.pa3.domain.repository.AuthRepository

// Inicia sesión validando los campos antes de llamar a la API
class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(username: String, password: String): Result<Sesion> {
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(Exception("Ingresa usuario y contraseña"))
        }
        return repository.login(username.trim(), password)
    }
}

// Devuelve la sesión guardada (token) o null si no hay sesión
class ObtenerSesionUseCase(private val repository: AuthRepository) {
    operator fun invoke(): Sesion? = repository.obtenerSesion()
}

/** Cierra sesión eliminando el token */
class LogoutUseCase(private val repository: AuthRepository) {
    operator fun invoke() = repository.logout()
}