package com.isil.android.pa3.data.model

import com.isil.android.pa3.domain.model.Product

// Producto tal como lo devuelve la API
data class ProductResponse(
    val id: Int,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val stock: Int
)

// Mapper: modelo de datos -> modelo de dominio
fun ProductResponse.toDomain() = Product(
    id = id,
    nombre = nombre,
    categoria = categoria,
    precio = precio,
    stock = stock
)