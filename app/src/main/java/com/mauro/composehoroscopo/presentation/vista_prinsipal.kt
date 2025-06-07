package com.mauro.composehoroscopo.presentation

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource // <-- CAMBIO AQUÍ: Import necesario
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mauro.composehoroscopo.AppDestinations
import com.mauro.composehoroscopo.BottomNavigationItem
import com.mauro.composehoroscopo.R // <-- CAMBIO AQUÍ: Importa la clase R de tu proyecto
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val itemsList = (1..50).map { "Elemento número $it" }

    // <-- CAMBIO AQUÍ: La lista ahora usa stringResource para obtener los textos
    // Esto funciona porque la lista se está declarando DENTRO de un Composable (@Composable).
    val navigationItems = listOf(
        BottomNavigationItem(
            label = stringResource(id = R.string.horoscope),
            icon = Icons.Filled.Home,
            route = AppDestinations.HOME_ROUTE
        ),
        BottomNavigationItem(
            label = stringResource(id = R.string.luck),
            icon = Icons.Filled.Favorite,
            route = AppDestinations.FAVORITES_ROUTE
        ),
        BottomNavigationItem(
            label = stringResource(id = R.string.palmistry),
            icon = Icons.Filled.Settings,
            route = AppDestinations.SETTINGS_ROUTE
        )
    )

    ComposehoroscopoTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            // <-- CAMBIO AQUÍ: El título también usa un recurso de string
                            text = stringResource(id = R.string.main_title_horoscope),
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
}

@Composable
fun HoroscopeContent(itemsList: List<String>, contentPadding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
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
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            IconButton(
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
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
                        contentDescription = item.label, // El contentDescription también se actualiza
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

// --- CAMBIOS EN LOS PREVIEWS ---
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    ComposehoroscopoTheme {
        MainScreen(navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun HoroscopeContentPreview() {
    ComposehoroscopoTheme {
        HoroscopeContent(itemsList = (1..5).map { "Item $it" }, contentPadding = PaddingValues(0.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AppBottomNavigationBarPreview() {
    val navController = rememberNavController()
    // <-- CAMBIO AQUÍ: El preview también debe usar los recursos para ser consistente
    val navigationItems = listOf(
        BottomNavigationItem(stringResource(id = R.string.horoscope), Icons.Filled.Home, AppDestinations.HOME_ROUTE),
        BottomNavigationItem(stringResource(id = R.string.luck), Icons.Filled.Favorite, AppDestinations.FAVORITES_ROUTE),
        BottomNavigationItem(stringResource(id = R.string.palmistry), Icons.Filled.Settings, AppDestinations.SETTINGS_ROUTE)
    )
    ComposehoroscopoTheme {
        AppBottomNavigationBar(items = navigationItems, navController = navController)
    }
}