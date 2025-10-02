package com.mauro.composehoroscopo.presentation

// CORRECCIÓN: Imports necesarios para la animación
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable // CORRECCIÓN: Import para hacer un Composable clicable
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
import androidx.compose.runtime.remember // CORRECCIÓN: Import
import androidx.compose.runtime.rememberCoroutineScope // CORRECCIÓN: Import
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate // CORRECCIÓN: Import para aplicar la rotación
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
import androidx.navigation.navArgument // Import para navArgument
import androidx.navigation.NavType // Import para NavType

import com.mauro.composehoroscopo.AppDestinations
import com.mauro.composehoroscopo.BottomNavigationItem
import com.mauro.composehoroscopo.R
import com.mauro.composehoroscopo.domain.model.HoroscopeInfo
import com.mauro.composehoroscopo.domain.model.HoroscopeModel // ¡NUEVO! Importa HoroscopeModel
import com.mauro.composehoroscopo.presentation.detail.HoroscopeDetailScreen // ¡NUEVO! Importa HoroscopeDetailScreen
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme
import kotlinx.coroutines.launch // CORRECCIÓN: Import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
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
            // CORRECCIÓN: La lambda ahora llama a navController.navigate
            HoroscopeContent(onHoroscopeClick = { horoscopeInfo ->
                navController.navigate("${AppDestinations.HOROSCOPE_DETAIL_ROUTE}/${horoscopeInfo.horoscopeModel.name}")
            })
        }
        composable(AppDestinations.FAVORITES_ROUTE) {
            LuckScreen()
        }
        composable(AppDestinations.SETTINGS_ROUTE) {
            PalmistryScreen()
        }

        // --- ¡NUEVO DESTINO PARA HOROSCOPE_DETAIL_SCREEN! ---
        composable(
            route = "${AppDestinations.HOROSCOPE_DETAIL_ROUTE}/{${AppDestinations.HOROSCOPE_DETAIL_ARG}}",
            arguments = listOf(
                navArgument(AppDestinations.HOROSCOPE_DETAIL_ARG) {
                    type = NavType.EnumType(HoroscopeModel::class.java)
                }
            )
        ) { backStackEntry ->
            val horoscopeType = backStackEntry.arguments
                ?.getString(AppDestinations.HOROSCOPE_DETAIL_ARG)
                ?.let { HoroscopeModel.valueOf(it) } // Convertimos el String de nuevo a HoroscopeModel

            horoscopeType?.let {
                HoroscopeDetailScreen(
                    navController = navController,
                    horoscopeType = it // Pasamos el tipo de horóscopo
                )
            } ?: run {
                // Manejo de error si el argumento no se pudo extraer o es inválido
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Error: Horóscopo no especificado o inválido",
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        // --- FIN DEL NUEVO DESTINO ---
    }
}


@Composable
fun HoroscopeContent(onHoroscopeClick: (HoroscopeInfo) -> Unit) { // CORRECCIÓN: Recibe la acción de clic
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
            // CORRECCIÓN: Pasamos la lambda de clic al item
            HoroscopeItem(horoscope = horoscope, onItemClick = { onHoroscopeClick(horoscope) })
        }
    }
}

@Composable
fun HoroscopeItem(
    horoscope: HoroscopeInfo,
    modifier: Modifier = Modifier,
    onItemClick: (HoroscopeInfo) -> Unit // CORRECCIÓN: Recibe una función a ejecutar al final de la animación
) {
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Card(
        modifier = modifier.clickable {
            scope.launch {
                rotation.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = LinearEasing
                    )
                )
                onItemClick(horoscope) // Ejecutamos la acción de clic después de la animación
                rotation.snapTo(0f) // Reseteamos la rotación
            }
        },
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
                modifier = Modifier
                    .size(120.dp)
                    .rotate(rotation.value)
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
                            // popUpTo debería usar la ruta de inicio, no el ID
                            popUpTo(AppDestinations.HOME_ROUTE) { saveState = true }
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
        val previewNavController = rememberNavController()
        MainScreen(navController = previewNavController)
    }
}

@Preview(showBackground = true, name = "Main Screen Light")
@Composable
fun MainScreenLightPreview() {
    ComposehoroscopoTheme(darkTheme = false) {
        val previewNavController = rememberNavController()
        MainScreen(navController = previewNavController)
    }
}


@Preview(name = "Horoscope Item Vertical")
@Composable
fun HoroscopeItemPreview() {
    ComposehoroscopoTheme(darkTheme = true) {
        HoroscopeItem(horoscope = HoroscopeInfo.Aries, onItemClick = {})
    }
}


@Preview(showBackground = true, name = "Horoscope Grid Preview")
@Composable
fun HoroscopeContentPreview() {
    ComposehoroscopoTheme(darkTheme = true) {
        HoroscopeContent(onHoroscopeClick = {})
    }
}