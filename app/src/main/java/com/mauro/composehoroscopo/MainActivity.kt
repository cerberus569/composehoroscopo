// Archivo: MainActivity.kt
package com.mauro.composehoroscopo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.mauro.composehoroscopo.presentation.MainScreen
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 1. Aplicamos el tema general a toda la aplicación.
            ComposehoroscopoTheme {

                // 2. Creamos el controlador de navegación.
                // `rememberNavController()` crea y recuerda el controlador
                // para que no se pierda si la vista se redibuja (recomposición).
                // CORRECCIÓN 1: Se crea la instancia del NavController.
                val navController = rememberNavController()


                // 3. ¡AQUÍ ESTÁ LA MAGIA!
                // Llamamos a `MainScreen` y le pasamos el `navController` que acabamos de crear.
                // CORRECCIÓN 2: Se pasa el navController como parámetro.
                MainScreen(navController = navController)
            }
        }
    }
}