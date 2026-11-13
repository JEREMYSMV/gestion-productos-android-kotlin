package com.isil.android.pa3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isil.android.pa3.domain.model.Product
import com.isil.android.pa3.domain.usecase.product.ActualizarProductoUseCase
import com.isil.android.pa3.domain.usecase.product.CrearProductoUseCase
import com.isil.android.pa3.domain.usecase.product.ObtenerProductoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Estado del formulario de producto
data class ProductFormUiState(
    val id: Int = 0,
    val nombre: String = "",
    val categoria: String = "",
    val precio: String = "",
    val stock: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val guardado: Boolean = false
) {
    val esEdicion: Boolean get() = id > 0
}

// Inserción y actualización de productos
class ProductFormViewModel(
    private val obtenerProductoUseCase: ObtenerProductoUseCase,
    private val crearProductoUseCase: CrearProductoUseCase,
    private val actualizarProductoUseCase: ActualizarProductoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductFormUiState())
    val uiState: StateFlow<ProductFormUiState> = _uiState.asStateFlow()

    private var cargado = false

    // Si llega un id > 0 se cargan los datos para editar
    fun cargar(id: Int) {
        if (cargado || id <= 0) return
        cargado = true
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            obtenerProductoUseCase(id)
                .onSuccess { p ->
                    _uiState.update {
                        it.copy(
                            id = p.id,
                            nombre = p.nombre,
                            categoria = p.categoria,
                            precio = p.precio.toString(),
                            stock = p.stock.toString(),
                            isLoading = false
                        )
                    }
                }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun onNombreChange(v: String) = _uiState.update { it.copy(nombre = v, error = null) }
    fun onCategoriaChange(v: String) = _uiState.update { it.copy(categoria = v, error = null) }
    fun onPrecioChange(v: String) = _uiState.update { it.copy(precio = v, error = null) }
    fun onStockChange(v: String) = _uiState.update { it.copy(stock = v, error = null) }

    // Crea o actualiza según si es edición
    fun guardar() {
        val s = _uiState.value
        val precio = s.precio.replace(",", ".").toDoubleOrNull()
        val stock = s.stock.toIntOrNull()
        if (precio == null || stock == null) {
            _uiState.update { it.copy(error = "Ingresa un precio y stock válidos") }
            return
        }
        val product = Product(
            id = s.id,
            nombre = s.nombre.trim(),
            categoria = s.categoria.trim(),
            precio = precio,
            stock = stock
        )
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            val resultado = if (s.esEdicion) actualizarProductoUseCase(product)
            else crearProductoUseCase(product)
            resultado
                .onSuccess { _uiState.update { it.copy(isLoading = false, guardado = true) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }
}