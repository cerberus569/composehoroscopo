package com.mauro.composehoroscopo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.compose.ui.text.font.Font


import androidx.compose.ui.text.font.FontStyle


import com.mauro.composehoroscopo.R



// Set of Material typography styles to start with
val dancingScriptFamily = FontFamily(
    Font(R.font.dancing, FontWeight.Normal), // Asume que tienes dancing_script_regular.ttf
    // Puedes añadir más variaciones si las tienes:
    // Font(R.font.dancing_script_bold, FontWeight.Bold),
    // Font(R.font.dancing_script_italic, FontWeight.Normal, FontStyle.Italic),
    // Font(R.font.dancing_script_bold_italic, FontWeight.Bold, FontStyle.Italic)
)

// 2. Actualiza tu objeto Typography para usar la nueva fuente
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = dancingScriptFamily, // <--- ¡Aquí usas tu nueva fuente!
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    // Puedes definir otros estilos de texto aquí usando tu fuente o FontFamily.Default
    titleLarge = TextStyle(
        fontFamily = dancingScriptFamily, // Ejemplo para títulos
        fontWeight = FontWeight.Bold, // Si tienes la variante bold
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default, // Puedes seguir usando la fuente por defecto para otros estilos
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),

            headlineMedium = TextStyle(
            fontFamily = dancingScriptFamily, // <--- ¡ASEGÚRATE DE QUE ESTO ESTÉ AQUÍ!
    fontWeight = FontWeight.Bold,    // O FontWeight.Normal, según lo que quieras y si tu fuente lo soporta
    fontSize = 28.sp,                // O el tamaño que definiste
    lineHeight = 36.sp,
    letterSpacing = 0.sp
    // ... más propiedades si es necesario
    )
    /* Otros estilos de texto predeterminados
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)