package com.isil.android.pa3.presentation.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.isil.android.pa3.PA3App

// Crea cada ViewModel inyectándole sus casos de uso
object ViewModelFactory {

    private val c get() = PA3App.container

    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer { LoginViewModel(c.loginUseCase) }
        initializer { HomeViewModel(c.obtenerSesionUseCase) }
        initializer { AjustesViewModel(c.obtenerSesionUseCase, c.logoutUseCase) }
        initializer { ProductViewModel(c.listarProductosUseCase, c.eliminarProductoUseCase) }
        initializer {
            ProductFormViewModel(
                c.obtenerProductoUseCase,
                c.crearProductoUseCase,
                c.actualizarProductoUseCase
            )
        }
    }
}