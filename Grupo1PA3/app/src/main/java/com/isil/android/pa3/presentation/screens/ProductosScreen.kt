package com.isil.android.pa3.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.isil.android.pa3.domain.model.Product
import com.isil.android.pa3.presentation.viewmodel.ProductViewModel
import com.isil.android.pa3.presentation.viewmodel.ViewModelFactory
import java.util.Locale

// Menú Productos: consulta y eliminación (inserción/edición en ProductFormScreen)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    onNuevo: () -> Unit,
    onEditar: (Int) -> Unit,
    viewModel: ProductViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    var productAEliminar by remember { mutableStateOf<Product?>(null) }

    // Recarga la lista cada vez que se entra (o se vuelve del formulario)
    LaunchedEffect(Unit) { viewModel.cargarProductos() }

    // Muestra los mensajes (por ejemplo, al eliminar)
    LaunchedEffect(state.mensaje) {
        state.mensaje?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.mensajeMostrado()
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text("Productos") },
                windowInsets = WindowInsets(0, 0, 0, 0),
                actions = {
                    IconButton(onClick = viewModel::cargarProductos) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Recargar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNuevo) {
                Icon(Icons.Filled.Add, contentDescription = "Nuevo producto")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.isLoading && state.productos.isEmpty() ->
                    CircularProgressIndicator(Modifier.align(Alignment.Center))

                state.error != null && state.productos.isEmpty() ->
                    Column(
                        modifier = Modifier.align(Alignment.Center).padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.error ?: "", color = MaterialTheme.colorScheme.error)
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = viewModel::cargarProductos) { Text("Reintentar") }
                    }

                state.productos.isEmpty() ->
                    Text(
                        "No hay productos. Presiona + para agregar uno.",
                        modifier = Modifier.align(Alignment.Center)
                    )

                else -> LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.productos, key = { it.id }) { product ->
                        ProductItem(
                            product = product,
                            onEditar = { onEditar(product.id) },
                            onEliminar = { productAEliminar = product }
                        )
                    }
                }
            }
        }
    }

    // Confirmación antes de eliminar
    productAEliminar?.let { product ->
        AlertDialog(
            onDismissRequest = { productAEliminar = null },
            title = { Text("Eliminar producto") },
            text = { Text("¿Seguro que deseas eliminar \"${product.nombre}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.eliminarProducto(product)
                    productAEliminar = null
                }) { Text("Eliminar", color = MaterialTheme.colorScheme.error) }
            },
            dismissButton = {
                TextButton(onClick = { productAEliminar = null }) { Text("Cancelar") }
            }
        )
    }
}

// Tarjeta de cada producto con botones de editar y eliminar
@Composable
private fun ProductItem(
    product: Product,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onEditar)
    ) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(product.nombre, style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold)
                Text(
                    String.format(Locale.US, "S/ %.2f", product.precio),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AssistChip(onClick = {}, label = { Text(product.categoria) })
                    Spacer(Modifier.width(8.dp))
                    Text("Stock: ${product.stock}", style = MaterialTheme.typography.bodySmall)
                }
            }
            IconButton(onClick = onEditar) {
                Icon(Icons.Filled.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = onEliminar) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar",
                    tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}