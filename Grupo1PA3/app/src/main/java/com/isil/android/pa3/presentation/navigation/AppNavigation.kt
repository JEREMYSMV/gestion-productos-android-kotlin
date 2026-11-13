package com.isil.android.pa3.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.isil.android.pa3.PA3App
import com.isil.android.pa3.presentation.screens.LoginScreen
import com.isil.android.pa3.presentation.screens.MainScreen
import com.isil.android.pa3.presentation.screens.SplashScreen

// Navegación raíz: Splash -> Login -> Main (3 menús)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.SPLASH) {

        composable(Routes.SPLASH) {
            SplashScreen(onFinish = {
                // Si hay token guardado entra directo; si no, va al login
                val haySesion = PA3App.container.obtenerSesionUseCase() != null
                navController.navigate(if (haySesion) Routes.MAIN else Routes.LOGIN) {
                    popUpTo(Routes.SPLASH) { inclusive = true }
                }
            })
        }

        composable(Routes.LOGIN) {
            LoginScreen(onLoginSuccess = {
                navController.navigate(Routes.MAIN) {
                    popUpTo(Routes.LOGIN) { inclusive = true }
                }
            })
        }

        composable(Routes.MAIN) {
            MainScreen(onLogout = {
                // Al cerrar sesión se vuelve al login y se limpia el historial
                navController.navigate(Routes.LOGIN) {
                    popUpTo(Routes.MAIN) { inclusive = true }
                }
            })
        }
    }
}