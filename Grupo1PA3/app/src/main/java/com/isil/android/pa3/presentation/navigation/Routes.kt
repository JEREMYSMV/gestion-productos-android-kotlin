package com.isil.android.pa3.presentation.navigation

object Routes {
    // Navegación principal
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val MAIN = "main"

    // Menús (barra inferior)
    const val HOME = "home"
    const val PRODUCTOS = "productos"
    const val AJUSTES = "ajustes"

    // Formulario de producto: id = -1 para nuevo, id > 0 para editar
    const val PRODUCT_FORM = "product_form?id={id}"
    fun productForm(id: Int = -1) = "product_form?id=$id"
}