package com.mauro.composehoroscopo.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mauro.composehoroscopo.AppDestinations
import com.mauro.composehoroscopo.BottomNavigationItem
import com.mauro.composehoroscopo.R
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val itemsList = (1..50).map { "Elemento número $it" }

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

    // APLICA EL TEMA, ASEGÚRATE DE QUE dynamicColor ESTÉ EN false PARA USAR TUS COLORES
    ComposehoroscopoTheme(dynamicColor = false) {
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
                    // --- CAMBIO PARA LA BARRA SUPERIOR (TopAppBar) ---
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        // Fondo de la barra: el mismo que el fondo de la pantalla
                        containerColor = MaterialTheme.colorScheme.background,
                        // Color del título: tu color secundario (naranja)
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
            // El color de fondo del contenido ya se aplica automáticamente desde el Scaffold
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
                // El color del texto se toma de onBackground o onSurface automáticamente
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
    navController: NavController,
    iconSize: Dp = 24.dp
) {
    NavigationBar(
        // --- CAMBIO 1: FONDO DE LA BARRA DE NAVEGACIÓN ---
        // Usa el color de fondo principal de la app.
        containerColor = MaterialTheme.colorScheme.background,
        // Opcional: Esto eleva visualmente un poco la barra, puedes quitarlo si no te gusta.
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
                // --- CAMBIO 2: COLORES DE LOS ÍCONOS Y TEXTOS ---
                colors = NavigationBarItemDefaults.colors(
                    // Color para el ícono y texto SELECCIONADO: tu color secundario (naranja)
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedTextColor = MaterialTheme.colorScheme.secondary,

                    // Color para el ícono y texto NO SELECCIONADO: el color `onPrimary` que definiste
                    unselectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary,

                    // Indicador: lo hacemos transparente para que no aparezca la "píldora"
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}



// --- ACTUALIZACIÓN DEL PREVIEW ---
// --- BLOQUE DE PREVIEWS ACTUALIZADO (Pega esto al final de MainScreen.kt) ---

@Preview(showBackground = true, name = "Main Screen Light")
@Composable
fun MainScreenPreview() {
    val navController = rememberNavController()
    // Forzamos el tema claro para este preview
    ComposehoroscopoTheme(darkTheme = false) {
        MainScreen(navController = navController)
    }
}

@Preview(showBackground = true, name = "Main Screen Dark")
@Composable
fun MainScreenDarkPreview() {
    val navController = rememberNavController()
    // Forzamos el tema oscuro para este preview
    ComposehoroscopoTheme(darkTheme = true) {
        MainScreen(navController = navController)
    }
}

@Preview(showBackground = true, name = "Content Preview")
@Composable
fun HoroscopeContentPreview() {
    ComposehoroscopoTheme {
        HoroscopeContent(
            itemsList = (1..5).map { "Item de ejemplo $it" },
            contentPadding = PaddingValues(0.dp)
        )
    }
}

// El preview de la barra de navegación por sí sola también es útil.
@Preview(showBackground = true, name = "Bottom Nav Bar Light")
@Composable
fun AppBottomNavigationBarPreview() {
    val navController = rememberNavController()
    val navigationItems = listOf(
        BottomNavigationItem("Horóscopo", R.drawable.ic_horoscope, AppDestinations.HOME_ROUTE),
        BottomNavigationItem("Suerte", R.drawable.ic_cards, AppDestinations.FAVORITES_ROUTE),
        BottomNavigationItem("Quiromancia", R.drawable.ic_hand, AppDestinations.SETTINGS_ROUTE)
    )
    ComposehoroscopoTheme(darkTheme = false) {
        AppBottomNavigationBar(
            items = navigationItems,
            navController = navController,
            iconSize = 22.dp
        )
    }
}

@Preview(showBackground = true, name = "Bottom Nav Bar Dark")
@Composable
fun AppBottomNavigationBarDarkPreview() {
    val navController = rememberNavController()
    val navigationItems = listOf(
        BottomNavigationItem("Horoscope", R.drawable.ic_horoscope, AppDestinations.HOME_ROUTE),
        BottomNavigationItem("suerte", R.drawable.ic_cards, AppDestinations.FAVORITES_ROUTE),
        BottomNavigationItem("Quiromancia", R.drawable.ic_hand, AppDestinations.SETTINGS_ROUTE)
    )
    ComposehoroscopoTheme(darkTheme = true) {
        AppBottomNavigationBar(
            items = navigationItems,
            navController = navController,
            iconSize = 22.dp
        )
    }
}