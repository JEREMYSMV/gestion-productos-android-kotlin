package com.isil.android.pa3.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.isil.android.pa3.data.datasource.RetrofitClient
import com.isil.android.pa3.presentation.viewmodel.AjustesViewModel
import com.isil.android.pa3.presentation.viewmodel.ViewModelFactory
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

// Menú Ajustes: cierre de sesión eliminando el token
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AjustesScreen(
    onLogout: () -> Unit,
    viewModel: AjustesViewModel = viewModel(factory = ViewModelFactory.Factory)
) {
    val sesion by viewModel.sesion.collectAsState()
    var mostrarDialogo by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            TopAppBar(
                title = { Text("Ajustes") },
                windowInsets = WindowInsets(0, 0, 0, 0)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ListItem(
                headlineContent = { Text(sesion?.nombre ?: "-") },
                supportingContent = { Text("Usuario: ${sesion?.username ?: "-"}") },
                leadingContent = { Icon(Icons.Filled.Person, contentDescription = null) }
            )
            HorizontalDivider()
            ListItem(
                headlineContent = { Text("Servidor API") },
                supportingContent = { Text(RetrofitClient.BASE_URL) },
                leadingContent = { Icon(Icons.Filled.Info, contentDescription = null) }
            )
            ListItem(
                headlineContent = { Text("Versión") },
                supportingContent = { Text("1.0 · Un Finde Tranqui · Grupo1PA3") },
                leadingContent = { Icon(Icons.Filled.Info, contentDescription = null) }
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { mostrarDialogo = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Cerrar sesión")
            }
        }
    }

    // Confirmación antes de cerrar sesión
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Cerrar sesión") },
            text = { Text("Se eliminará el token guardado. ¿Deseas continuar?") },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogo = false
                    viewModel.cerrarSesion {
                        Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
                        onLogout()
                    }
                }) { Text("Cerrar sesión") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
            }
        )
    }
}

// Pantalla base reutilizable
@Composable
fun MenuPlaceholder(icono: ImageVector, titulo: String, descripcion: String) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(icono, contentDescription = null, modifier = Modifier.size(72.dp),
            tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(16.dp))
        Text(titulo, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(descripcion, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}