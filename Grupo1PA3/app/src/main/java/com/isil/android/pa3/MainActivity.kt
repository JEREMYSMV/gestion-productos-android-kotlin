package com.isil.android.pa3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.isil.android.pa3.presentation.navigation.AppNavigation
import com.isil.android.pa3.ui.theme.Grupo1PA3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()   // Splash del sistema
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Grupo1PA3Theme {
                AppNavigation()  // Splash -> Login -> Menús
            }
        }
    }
}