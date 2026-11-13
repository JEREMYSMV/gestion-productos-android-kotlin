package com.isil.android.pa3.di

import android.content.Context
import com.isil.android.pa3.data.datasource.ApiService
import com.isil.android.pa3.data.datasource.RetrofitClient
import com.isil.android.pa3.data.datasource.TokenManager
import com.isil.android.pa3.data.repository.AuthRepositoryImpl
import com.isil.android.pa3.data.repository.ProductRepositoryImpl
import com.isil.android.pa3.domain.repository.AuthRepository
import com.isil.android.pa3.domain.repository.ProductRepository
import com.isil.android.pa3.domain.usecase.auth.LoginUseCase
import com.isil.android.pa3.domain.usecase.auth.LogoutUseCase
import com.isil.android.pa3.domain.usecase.auth.ObtenerSesionUseCase
import com.isil.android.pa3.domain.usecase.product.ActualizarProductoUseCase
import com.isil.android.pa3.domain.usecase.product.CrearProductoUseCase
import com.isil.android.pa3.domain.usecase.product.EliminarProductoUseCase
import com.isil.android.pa3.domain.usecase.product.ListarProductosUseCase
import com.isil.android.pa3.domain.usecase.product.ObtenerProductoUseCase

// Inyección de dependencias manual: une data -> domain -> presentation
class AppContainer(context: Context) {

    // data / datasource
    private val tokenManager = TokenManager(context)
    private val apiService: ApiService = RetrofitClient.create(tokenManager)

    // data / repository
    private val authRepository: AuthRepository = AuthRepositoryImpl(apiService, tokenManager)
    private val productRepository: ProductRepository = ProductRepositoryImpl(apiService)

    // domain / usecase - auth
    val loginUseCase = LoginUseCase(authRepository)
    val obtenerSesionUseCase = ObtenerSesionUseCase(authRepository)
    val logoutUseCase = LogoutUseCase(authRepository)

    // domain / usecase - product (CRUD)
    val listarProductosUseCase = ListarProductosUseCase(productRepository)
    val obtenerProductoUseCase = ObtenerProductoUseCase(productRepository)
    val crearProductoUseCase = CrearProductoUseCase(productRepository)
    val actualizarProductoUseCase = ActualizarProductoUseCase(productRepository)
    val eliminarProductoUseCase = EliminarProductoUseCase(productRepository)
}