package com.isil.android.pa3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.isil.android.pa3.domain.model.Sesion
import com.isil.android.pa3.domain.usecase.auth.ObtenerSesionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
    obtenerSesionUseCase: ObtenerSesionUseCase
) : ViewModel() {

    // Sesión con el token generado en el login
    private val _sesion = MutableStateFlow(obtenerSesionUseCase())
    val sesion: StateFlow<Sesion?> = _sesion.asStateFlow()
}