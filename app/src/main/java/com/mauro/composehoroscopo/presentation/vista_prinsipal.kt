package com.mauro.composehoroscopo.presentation

import androidx.compose.foundation.Image // CAMBIO: Import necesario
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row // CAMBIO: Import necesario
import androidx.compose.foundation.layout.Spacer // CAMBIO: Import necesario
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width // CAMBIO: Import necesario
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card // CAMBIO: Import necesario para un mejor diseño
import androidx.compose.material3.CardDefaults // CAMBIO: Import necesario
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mauro.composehoroscopo.AppDestinations
import com.mauro.composehoroscopo.BottomNavigationItem
import com.mauro.composehoroscopo.R
import com.mauro.composehoroscopo.domain.model.HoroscopeInfo // CAMBIO: Importamos nuestro modelo
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val navigationItems = listOf(
        BottomNavigationItem(
            label = stringResource(id = R.string.horoscope),
            iconResId = R.drawable.ic_horoscope,
            route = AppDestinations.HOME_ROUTE
        ),
        BottomNavigationItem(
            label = stringResource(id = R.string.luck),
            iconResId = R.drawable.ic_cards,
            route = AppDestinations.FAVORITES_ROUTE
        ),
        BottomNavigationItem(
            label = stringResource(id = R.string.palmistry),
            iconResId = R.drawable.ic_hand,
            route = AppDestinations.SETTINGS_ROUTE
        )
    )

    ComposehoroscopoTheme(dynamicColor = false, darkTheme = true) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.main_title_horoscope),
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.secondary
                    )
                )
            },
            bottomBar = {
                AppBottomNavigationBar(
                    items = navigationItems,
                    navController = navController,
                    iconSize = 22.dp
                )
            }
        ) { innerPadding ->
            AppNavigationHost(navController = navController, paddingValues = innerPadding)
        }
    }
}

@Composable
fun AppNavigationHost(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.HOME_ROUTE,
        modifier = Modifier.padding(paddingValues)
    ) {
        composable(AppDestinations.HOME_ROUTE) {
            // CAMBIO: Ahora HoroscopeContent mostrará los datos reales
            HoroscopeContent()
        }
        composable(AppDestinations.FAVORITES_ROUTE) {
            LuckScreen()
        }
        composable(AppDestinations.SETTINGS_ROUTE) {
            PalmistryScreen()
        }
    }
}

// --- CAMBIO PRINCIPAL: ACTUALIZACIÓN DE HoroscopeContent ---
@Composable
fun HoroscopeContent() {
    // Obtenemos todos los objetos de nuestro sealed class de forma programática.
    // Esto es muy útil porque si en el futuro añades un nuevo signo, se agregará a la lista automáticamente.
    val horoscopeList = HoroscopeInfo::class.sealedSubclasses.mapNotNull { it.objectInstance }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        // Aumentamos el espaciado para que las tarjetas se vean mejor
        verticalArrangement = Arrangement.spacedBy(16.dp),
        // Añadimos padding vertical para que no se pegue a la barra superior e inferior
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Usamos la lista real de horóscopos
        items(horoscopeList) { horoscope ->
            // Llamamos a un nuevo Composable para dibujar cada elemento
            HoroscopeItem(horoscope = horoscope)
        }
    }
}

// --- NUEVO COMPOSABLE: HoroscopeItem ---
// Es una buena práctica crear un Composable específico para los elementos de una lista.
// Esto hace el código más limpio y reutilizable.
@Composable
fun HoroscopeItem(horoscope: HoroscopeInfo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = horoscope.img),
                contentDescription = stringResource(id = horoscope.name), // Descripción para accesibilidad
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp)) // Espacio entre imagen y texto
            Text(
                text = stringResource(id = horoscope.name),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


// --- PANTALLAS DE EJEMPLO (Sin cambios) ---
@Composable
fun LuckScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Pantalla de la Suerte", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun PalmistryScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Pantalla de Quiromancia", style = MaterialTheme.typography.headlineMedium)
    }
}


// --- BARRA DE NAVEGACIÓN (Sin cambios) ---
@Composable
fun AppBottomNavigationBar(
    items: List<BottomNavigationItem>,
    navController: NavHostController,
    iconSize: Dp = 24.dp
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 4.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        contentDescription = item.label,
                        modifier = Modifier.size(iconSize)
                    )
                },
                label = {
                    Text(text = item.label)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}


// --- PREVIEWS ACTUALIZADOS ---

// Las Previews de MainScreen ya funcionan correctamente, porque `MainScreen`
// renderiza el `NavHost`, que por defecto muestra `HoroscopeContent`.
// Así que ahora verás la lista real en las previews.
@Preview(showBackground = true, name = "Main Screen Dark")
@Composable
fun MainScreenDarkPreview() {
    ComposehoroscopoTheme(darkTheme = true) {
        MainScreen()
    }
}

@Preview(showBackground = true, name = "Main Screen Light")
@Composable
fun MainScreenLightPreview() {
    ComposehoroscopoTheme(darkTheme = false) {
        MainScreen()
    }
}

// CAMBIO: AÑADIMOS UNA PREVIEW ESPECÍFICA PARA NUESTRO NUEVO HoroscopeItem
// Esto es muy útil para diseñar y probar el item de forma aislada.
@Preview(showBackground = true)
@Composable
fun HoroscopeItemPreview() {
    ComposehoroscopoTheme(darkTheme = true) {
        // Usamos un horóscopo de ejemplo para la preview
        HoroscopeItem(horoscope = HoroscopeInfo.Aries)
    }
}