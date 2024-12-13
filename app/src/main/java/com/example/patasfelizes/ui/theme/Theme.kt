package com.example.patasfelizes.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Definindo as cores do sistema
val primaryColor = Color(0xFF84A1DB)     // Principal
val desaturated1 = Color(0xFFC3CFED)     // Desaturado 1
val desaturated2 = Color(0xFFD7DFF3)     // Desaturado 2
val desaturated3 = Color(0xFFEBEFF9)     // Desaturado 3
val whiteColor = Color(0xFFFFFFFF)       // Branco
val grayColor = Color(0xFF545252)        // Cinza
val redColor = Color(0xFFE24767)         // Vermelho
val blackColor = Color(0xFF000000)       // Preto

// Função Composable para o tema
@Composable
fun PatasFelizesTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = primaryColor,
            onPrimary = whiteColor,
            
            secondary = desaturated3,
            onSecondary = grayColor,
            
            tertiary = desaturated2,
            onTertiary = blackColor,
            
            background = whiteColor,
            onBackground = grayColor,
            
            surface = desaturated1,
            onSurface = grayColor,

            error = redColor
        ),
	typography = Typography,
        content = content
    )
}

