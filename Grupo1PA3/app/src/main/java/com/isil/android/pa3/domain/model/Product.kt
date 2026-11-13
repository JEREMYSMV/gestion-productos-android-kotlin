package com.isil.android.pa3.domain.model

// Entidad de datos elegida: Product (bebidas de Un Finde Tranqui)
data class Product(
    val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val precio: Double,
    val stock: Int
)