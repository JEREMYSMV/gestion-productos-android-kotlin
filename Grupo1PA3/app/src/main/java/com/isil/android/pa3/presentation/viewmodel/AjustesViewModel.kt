package com.isil.android.pa3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.isil.android.pa3.domain.model.Sesion
import com.isil.android.pa3.domain.usecase.auth.LogoutUseCase
import com.isil.android.pa3.domain.usecase.auth.ObtenerSesionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AjustesViewModel(
    obtenerSesionUseCase: ObtenerSesionUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _sesion = MutableStateFlow(obtenerSesionUseCase())
    val sesion: StateFlow<Sesion?> = _sesion.asStateFlow()

    // Cierra sesión eliminando el token del TokenManager
    fun cerrarSesion(onSesionCerrada: () -> Unit) {
        logoutUseCase()
        _sesion.value = null
        onSesionCerrada()
    }
}