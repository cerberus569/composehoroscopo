package com.mauro.composehoroscopo

// Archivo: Navigation.kt


import androidx.compose.ui.graphics.vector.ImageVector // <-- ¡IMPORTANTE!

// Objeto para definir las rutas de forma centralizada
object AppDestinations {
    const val HOME_ROUTE = "home"
    const val FAVORITES_ROUTE = "favorites"
    const val SETTINGS_ROUTE = "settings"
}

// Modelo de datos para cada ítem de la barra de navegación
data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)