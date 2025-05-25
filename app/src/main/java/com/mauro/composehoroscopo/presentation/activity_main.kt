package com.mauro.composehoroscopo.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Scaffold // Específico para Scaffold si es necesario
import androidx.compose.material3.NavigationBar // Específico para NavigationBar
import androidx.compose.material3.NavigationBarItem // Específico para NavigationBarItem
import androidx.compose.material3.Icon // MUY IMPORTANTE para M3 Icon
import androidx.compose.material3.Text // MUY IMPORTANTE para M3 Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Elimina el import duplicado de NavigationBarItem si Android Studio no lo hace automáticamente.
// import androidx.compose.material3.NavigationBarItem // Ya estaba arriba

@Composable
fun MainScreen() {
    val items = listOf(
        "Aries", "Tauro", "Géminis", "Cáncer", "Leo", "Virgo",
        "Libra", "Escorpio", "Sagitario", "Capricornio", "Acuario", "Piscis"
    )
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar { // Correcto para M3
                BottomNavigationButton(
                    icon = Icons.Filled.Home,
                    label = "Inicio",
                    selected = selectedIndex == 0
                ) { selectedIndex = 0 }

                BottomNavigationButton(
                    icon = Icons.Filled.Favorite,
                    label = "Favoritos",
                    selected = selectedIndex == 1
                ) { selectedIndex = 1 }

                BottomNavigationButton(
                    icon = Icons.Filled.Person,
                    label = "Perfil",
                    selected = selectedIndex == 2
                ) { selectedIndex = 2 }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text( // Asegúrate que este Text también use el import de M3
                    text = "Horóscopo",
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(items) { sign ->
                    Text( // Y este Text también
                        text = sign,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationButton(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    NavigationBarItem( // Debería ser androidx.compose.material3.NavigationBarItem
        icon = { Icon(icon, contentDescription = label) }, // Debería ser androidx.compose.material3.Icon
        label = { Text(label) }, // Debería ser androidx.compose.material3.Text
        selected = selected,
        onClick = onClick
    )
}