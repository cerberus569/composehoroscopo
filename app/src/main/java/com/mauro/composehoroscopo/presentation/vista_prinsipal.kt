package com.mauro.composehoroscopo.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.mauro.composehoroscopo.domain.model.HoroscopeInfo
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// CORRECCIÓN 1: MainScreen ahora RECIBE un NavController en lugar de crearlo.
// Esto permite que la preview funcione, ya que podemos pasarle un controlador "falso".
fun MainScreen(navController: NavHostController) {
    // CORRECCIÓN 2: Se elimina la creación del NavController de aquí.
    // val navController = rememberNavController()

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
                    navController = navController, // Se usa el NavController recibido
                    iconSize = 22.dp
                )
            }
        ) { innerPadding ->
            // Se pasa el NavController recibido al Host de navegación
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


@Composable
fun HoroscopeContent() {
    val horoscopeList = listOf(
        HoroscopeInfo.Aries, HoroscopeInfo.Taurus, HoroscopeInfo.Gemini,
        HoroscopeInfo.Cancer, HoroscopeInfo.Leo, HoroscopeInfo.Virgo,
        HoroscopeInfo.Libra, HoroscopeInfo.Scorpio, HoroscopeInfo.Sagittarius,
        HoroscopeInfo.Capricorn, HoroscopeInfo.Aquarius, HoroscopeInfo.Pisces
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(horoscopeList) { horoscope ->
            HoroscopeItem(horoscope = horoscope)
        }
    }
}

@Composable
fun HoroscopeItem(horoscope: HoroscopeInfo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = horoscope.img),
                contentDescription = stringResource(id = horoscope.name),
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = horoscope.name),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
    }
}


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


@Preview(showBackground = true, name = "Main Screen Dark")
@Composable
fun MainScreenDarkPreview() {
    ComposehoroscopoTheme(darkTheme = true) {
        // CORRECCIÓN 3: Creamos un NavController de prueba aquí, solo para la preview.
        val previewNavController = rememberNavController()
        // CORRECCIÓN 4: Pasamos el controlador de prueba a nuestra MainScreen.
        MainScreen(navController = previewNavController)
    }
}

@Preview(showBackground = true, name = "Main Screen Light")
@Composable
fun MainScreenLightPreview() {
    ComposehoroscopoTheme(darkTheme = false) {
        // Hacemos lo mismo para la preview con tema claro.
        val previewNavController = rememberNavController()
        MainScreen(navController = previewNavController)
    }
}

// Esta preview no necesita cambios porque no depende del NavController.
@Preview(name = "Horoscope Item Vertical")
@Composable
fun HoroscopeItemPreview() {
    ComposehoroscopoTheme(darkTheme = true) {
        HoroscopeItem(horoscope = HoroscopeInfo.Aries)
    }
}

// Esta preview no necesita cambios porque no depende del NavController.
@Preview(showBackground = true, name = "Horoscope Grid Preview")
@Composable
fun HoroscopeContentPreview() {
    ComposehoroscopoTheme(darkTheme = true) {
        HoroscopeContent()
    }
}