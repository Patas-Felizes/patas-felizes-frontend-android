package com.example.patasfelizes.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.darkColorScheme

// Definição de cores (como já existente)
val paws_blue = Color(0xFF84A1DB)
val paws_blue_desaturated = Color(0xFFEBEFF9)
val white = Color(0xFFFFFFFF)
val gray = Color(0xFF545252)
val red = Color(0xFFE24767)
val black = Color(0xFF000000)

val backgroundDark = Color(0x1B1E25)
val cardDark = Color(0x3B3E44)

@Composable
fun PatasFelizesTheme(
    isDarkTheme: Boolean = false, // Passar booleano para controlar o tema
    content: @Composable () -> Unit
) {
    val colors = if (isDarkTheme) {
        darkColorScheme(
            primary = paws_blue,
            onPrimary = white,
            secondary = paws_blue_desaturated,
            onSecondary = gray,
            onTertiary = black,
            background = white,
            onBackground = gray,
            onSurface = gray,
            error = red
        )
    } else {
        lightColorScheme(
            primary = paws_blue,
            onPrimary = white,
            secondary = paws_blue_desaturated,
            onSecondary = gray,
            onTertiary = black,
            background = white,
            onBackground = gray,
            onSurface = gray,
            error = red
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
