package com.isil.android.pa3.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.isil.android.pa3.presentation.navigation.Routes

private data class MenuItem(val route: String, val label: String, val icon: ImageVector)

private val menus = listOf(
    MenuItem(Routes.HOME, "Inicio", Icons.Filled.Home),
    MenuItem(Routes.PRODUCTOS, "Productos", Icons.Filled.ShoppingCart),
    MenuItem(Routes.AJUSTES, "Ajustes", Icons.Filled.Settings)
)

// Contenedor con los 3 menús: Inicio, Productos y Ajustes
@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) { HomeScreen() }

            composable(Routes.PRODUCTOS) {
                ProductosScreen(
                    onNuevo = { navController.navigate(Routes.productForm()) },
                    onEditar = { id -> navController.navigate(Routes.productForm(id)) }
                )
            }

            // Formulario: id = -1 nuevo, id > 0 editar
            composable(
                route = Routes.PRODUCT_FORM,
                arguments = listOf(navArgument("id") {
                    type = NavType.IntType
                    defaultValue = -1
                })
            ) { backStackEntry ->
                ProductFormScreen(
                    productId = backStackEntry.arguments?.getInt("id") ?: -1,
                    onBack = { navController.popBackStack() }
                )
            }

            composable(Routes.AJUSTES) { AjustesScreen(onLogout = onLogout) }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = backStackEntry?.destination?.route

    NavigationBar {
        menus.forEach { item ->
            // El formulario de producto pertenece al menú Productos
            val seleccionado = rutaActual == item.route ||
                    (item.route == Routes.PRODUCTOS && rutaActual == Routes.PRODUCT_FORM)
            NavigationBarItem(
                selected = seleccionado,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}