package com.isil.android.pa3.domain.repository

import com.isil.android.pa3.domain.model.Product

// Contrato que la capa data implementa (ProductRepositoryImpl)
interface ProductRepository {
    suspend fun listar(): Result<List<Product>>
    suspend fun obtener(id: Int): Result<Product>
    suspend fun crear(product: Product): Result<Product>
    suspend fun actualizar(product: Product): Result<Product>
    suspend fun eliminar(id: Int): Result<Unit>
}