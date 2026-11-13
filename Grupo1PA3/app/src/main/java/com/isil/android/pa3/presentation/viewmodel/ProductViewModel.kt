package com.isil.android.pa3.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isil.android.pa3.domain.model.Product
import com.isil.android.pa3.domain.usecase.product.EliminarProductoUseCase
import com.isil.android.pa3.domain.usecase.product.ListarProductosUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Estado de la pantalla de lista de productos
data class ProductListUiState(
    val productos: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val mensaje: String? = null
)

// Consulta y eliminación de productos
class ProductViewModel(
    private val listarProductosUseCase: ListarProductosUseCase,
    private val eliminarProductoUseCase: EliminarProductoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    fun cargarProductos() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            listarProductosUseCase()
                .onSuccess { lista -> _uiState.update { it.copy(productos = lista, isLoading = false) } }
                .onFailure { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
        }
    }

    fun eliminarProducto(product: Product) {
        viewModelScope.launch {
            eliminarProductoUseCase(product.id)
                .onSuccess {
                    _uiState.update {
                        it.copy(
                            productos = it.productos.filter { p -> p.id != product.id },
                            mensaje = "Producto eliminado correctamente"
                        )
                    }
                }
                .onFailure { e -> _uiState.update { it.copy(mensaje = e.message) } }
        }
    }

    fun mensajeMostrado() = _uiState.update { it.copy(mensaje = null) }
}