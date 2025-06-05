package com.mauro.composehoroscopo.presentation

import androidx.compose.foundation.layout.Arrangement
// import androidx.compose.foundation.layout.Box // No se usa directamente, se puede quitar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme // Se usará para acceder a typography y colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
// import androidx.compose.runtime.mutableStateOf // No se usa directamente, se puede quitar
// import androidx.compose.runtime.remember // No se usa directamente, se puede quitar
// import androidx.compose.runtime.setValue // No se usa directamente, se puede quitar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme // <--- IMPORTANTE: Importa TU tema
import com.mauro.composehoroscopo.AppDestinations
import com.mauro.composehoroscopo.BottomNavigationItem

// Define tus rutas de navegación (puedes poner esto en un archivo separado)

)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    // Lista de items para el LazyColumn (ejemplo)
    val itemsList = (1..50).map { "Elemento número $it" }

    // Items para la barra de navegación inferior
    val navigationItems = listOf(
        BottomNavigationItem("Inicio", Icons.Filled.Home, AppDestinations.HOME_ROUTE),
        BottomNavigationItem("Favoritos", Icons.Filled.Favorite, AppDestinations.FAVORITES_ROUTE),
        BottomNavigationItem("Ajustes", Icons.Filled.Settings, AppDestinations.SETTINGS_ROUTE)
    )

    // Nota: MainScreen en sí misma no necesita estar envuelta por ComposehoroscopoTheme aquí,
    // porque se asume que quien LLAMA a MainScreen (ej. tu MainActivity o tu sistema de navegación)
    // ya ha aplicado ComposehoroscopoTheme a un nivel superior.
    ComposehoroscopoTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Horóscopo",
                            style = MaterialTheme.typography.headlineMedium, // Hereda de ComposehoroscopoTheme
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer, // Hereda de ComposehoroscopoTheme
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer // Hereda de ComposehoroscopoTheme
                    )
                )
            },
            bottomBar = {
                AppBottomNavigationBar(
                    items = navigationItems,
                    navController = navController
                )
            }
        ) { innerPadding ->
            HoroscopeContent(
                itemsList = itemsList,
                contentPadding = innerPadding
            )
        }
    }
}

@Composable
fun HoroscopeContent(itemsList: List<String>, contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding) // Aplica el padding del Scaffold
            .padding(horizontal = 16.dp), // Padding adicional si lo deseas
        verticalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre items
    ) {
        items(itemsList) { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.bodyLarge, // Hereda de ComposehoroscopoTheme
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
        }
    }
}

@Composable
fun AppBottomNavigationBar(
    items: List<BottomNavigationItem>,
    navController: NavController
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant, // Hereda de ComposehoroscopoTheme
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant // Hereda de ComposehoroscopoTheme
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            IconButton(
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f) // Hereda
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall, // Hereda
                        color = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f) // Hereda
                    )
                }
            }
        }
    }
}

// --- CAMBIOS EN LOS PREVIEWS ---
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    ComposehoroscopoTheme { // <--- APLICA TU TEMA PERSONALIZADO AQUÍ
        MainScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun HoroscopeContentPreview() {
    ComposehoroscopoTheme { // <--- APLICA TU TEMA PERSONALIZADO AQUÍ
        HoroscopeContent(itemsList = (1..5).map { "Item $it" }, contentPadding = PaddingValues(0.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomNavigationBarPreview() {
    val navController = rememberNavController()
    val navigationItems = listOf(
        BottomNavigationItem("Inicio", Icons.Filled.Home, AppDestinations.HOME_ROUTE),
        BottomNavigationItem("Favoritos", Icons.Filled.Favorite, AppDestinations.FAVORITES_ROUTE),
        BottomNavigationItem("Ajustes", Icons.Filled.Settings, AppDestinations.SETTINGS_ROUTE)
    )
    ComposehoroscopoTheme { // <--- APLICA TU TEMA PERSONALIZADO AQUÍ
        AppBottomNavigationBar(items = navigationItems, navController = navController)
    }
}