package com.mauro.composehoroscopo.presentation



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite // Ejemplo de icono
import androidx.compose.material.icons.filled.Home // Ejemplo de icono
import androidx.compose.material.icons.filled.Settings // Ejemplo de icono
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// Define tus rutas de navegación (puedes poner esto en un archivo separado)
object AppDestinations {
    const val HOME_ROUTE = "home"
    const val FAVORITES_ROUTE = "favorites"
    const val SETTINGS_ROUTE = "settings"
}

data class BottomNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
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

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Horóscopo",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                style = MaterialTheme.typography.bodyLarge,
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
        containerColor = MaterialTheme.colorScheme.surfaceVariant, // Color de fondo de la barra
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant // Color del contenido (iconos, texto)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            IconButton(
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                },
                modifier = Modifier.weight(1f) // Distribuye el espacio equitativamente
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (currentRoute == item.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    // Para la preview, necesitamos un NavController de prueba
    val navController = rememberNavController()
    MaterialTheme { // Asegúrate de tener un tema aplicado
        MainScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun HoroscopeContentPreview() {
    MaterialTheme {
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
    MaterialTheme {
        AppBottomNavigationBar(items = navigationItems, navController = navController)
    }
}