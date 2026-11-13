package com.isil.android.pa3.data.model

import com.isil.android.pa3.domain.model.Product

// Body enviado al crear (POST) o actualizar (PUT) un producto
data class ProductRequest(
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val stock: Int
)

// Mapper: modelo de dominio -> modelo de datos
fun Product.toRequest() = ProductRequest(
    nombre = nombre,
    categoria = categoria,
    precio = precio,
    stock = stock
)