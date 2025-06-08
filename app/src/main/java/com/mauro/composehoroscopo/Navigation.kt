package com.mauro.composehoroscopo

// Ya no necesitamos esta importación porque no usaremos ImageVector directamente en la data class
// import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Objeto que contiene las constantes de las rutas de navegación de la app.
 * Usar un objeto centraliza las rutas, evita errores de tipeo y facilita cambios a futuro.
 */
object AppDestinations {
    const val HOME_ROUTE = "home"
    const val FAVORITES_ROUTE = "favorites"
    const val SETTINGS_ROUTE = "settings"
}

/**
 * Modelo de datos para cada ítem de la barra de navegación inferior.
 * Este es el "molde" que usan todas las partes de tu app para entender qué es un ítem de navegación.
 *
 * @param label El texto que se mostrará debajo del icono.
 * @param iconResId El ID del recurso del icono en la carpeta `drawable` (ej: R.drawable.ic_horoscope).
 * @param route La ruta de destino a la que navegar al hacer clic.
 */
data class BottomNavigationItem(
    val label: String,
    // --- CAMBIO CLAVE ---
    // Antes era: val icon: ImageVector
    // Ahora es un Int para guardar el ID del recurso (R.drawable.xxx)
    val iconResId: Int,
    val route: String
)