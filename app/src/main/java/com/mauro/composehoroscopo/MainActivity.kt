package com.mauro.composehoroscopo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.mauro.composehoroscopo.presentation.AppBottomNavigationBar
import com.mauro.composehoroscopo.presentation.AppDestinations
import com.mauro.composehoroscopo.presentation.BottomNavigationItem
import com.mauro.composehoroscopo.presentation.HoroscopeContent
import com.mauro.composehoroscopo.presentation.MainScreen
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposehoroscopoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    fun MainScreen(navController: NavController),
                    fun HoroscopeContent(),
                    fun AppBottomNavigationBar(),

                }
            }
        }
    }
}

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