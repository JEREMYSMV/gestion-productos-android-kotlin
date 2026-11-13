package com.isil.android.pa3.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.isil.android.pa3.R
import com.isil.android.pa3.ui.theme.BrandAccent
import com.isil.android.pa3.ui.theme.BrandPrimary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinish: () -> Unit) {
    val escala = remember { Animatable(0.4f) }

    LaunchedEffect(Unit) {
        escala.animateTo(1f, tween(900))   // animación del logo
        delay(1500)                         // tiempo visible del splash
        onFinish()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(BrandPrimary).systemBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(R.drawable.uft_ciervo_blanco),
                contentDescription = "Logo UFT",
                modifier = Modifier.size(180.dp).scale(escala.value)
            )
            Spacer(Modifier.height(16.dp))
            Text("UN FINDE TRANQUI", style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold, color = Color.White)
            Text("DESDE 2024", color = Color.White)
        }
        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = BrandAccent, modifier = Modifier.size(28.dp))
            Spacer(Modifier.height(12.dp))
            Text("Grupo 1 · PA3 · ISIL", color = Color.White)
        }
    }
}