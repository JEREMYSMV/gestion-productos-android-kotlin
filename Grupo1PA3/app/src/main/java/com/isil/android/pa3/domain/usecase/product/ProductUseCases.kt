package com.isil.android.pa3.domain.usecase.product

import com.isil.android.pa3.domain.model.Product
import com.isil.android.pa3.domain.repository.ProductRepository

// Consulta: lista todos los productos
class ListarProductosUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(): Result<List<Product>> = repository.listar()
}

// Consulta: obtiene un producto por id
class ObtenerProductoUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(id: Int): Result<Product> = repository.obtener(id)
}

// Inserción
class CrearProductoUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product): Result<Product> {
        validar(product)?.let { return Result.failure(Exception(it)) }
        return repository.crear(product)
    }
}

// Actualización
class ActualizarProductoUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(product: Product): Result<Product> {
        validar(product)?.let { return Result.failure(Exception(it)) }
        return repository.actualizar(product)
    }
}

// Eliminación
class EliminarProductoUseCase(private val repository: ProductRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> = repository.eliminar(id)
}

// Reglas de negocio del producto
private fun validar(product: Product): String? = when {
    product.nombre.isBlank() -> "El nombre es obligatorio"
    product.categoria.isBlank() -> "La categoría es obligatoria"
    product.precio < 0 -> "El precio no puede ser negativo"
    product.stock < 0 -> "El stock no puede ser negativo"
    else -> null
}