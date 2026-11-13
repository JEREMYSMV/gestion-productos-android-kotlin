package com.isil.android.pa3.data.repository

import com.isil.android.pa3.data.datasource.ApiService
import com.isil.android.pa3.data.model.toDomain
import com.isil.android.pa3.data.model.toRequest
import com.isil.android.pa3.domain.model.Product
import com.isil.android.pa3.domain.repository.ProductRepository

// Implementación del repositorio: llama a la API con Retrofit
class ProductRepositoryImpl(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun listar(): Result<List<Product>> =
        safeApiCall { apiService.listarProductos() }
            .map { lista -> lista.map { it.toDomain() } }

    override suspend fun obtener(id: Int): Result<Product> =
        safeApiCall { apiService.obtenerProducto(id) }
            .map { it.toDomain() }

    override suspend fun crear(product: Product): Result<Product> =
        safeApiCall { apiService.crearProducto(product.toRequest()) }
            .map { it.toDomain() }

    override suspend fun actualizar(product: Product): Result<Product> =
        safeApiCall { apiService.actualizarProducto(product.id, product.toRequest()) }
            .map { it.toDomain() }

    override suspend fun eliminar(id: Int): Result<Unit> =
        safeApiCall { apiService.eliminarProducto(id) }
            .map { }
}