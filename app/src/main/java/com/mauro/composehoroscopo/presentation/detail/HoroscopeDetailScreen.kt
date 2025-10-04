package com.mauro.composehoroscopo.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mauro.composehoroscopo.R
import com.mauro.composehoroscopo.domain.model.HoroscopeModel
import com.mauro.composehoroscopo.domain.model.HoroscopeModel.*

@Composable
fun HoroscopeDetailScreen(
    navController: NavController,
    horoscopeType: HoroscopeModel, // Se pasa el tipo de horóscopo directamente
    horoscopeDetailViewModel: HoroscopeDetailViewModel = hiltViewModel()
) {
    // Recopila el estado del ViewModel
    val state by horoscopeDetailViewModel.state.collectAsState()

    // Carga los datos del horóscopo cuando la pantalla se crea
    LaunchedEffect(key1 = Unit) {
        horoscopeDetailViewModel.getHoroscope(horoscopeType)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top bar con botón de retroceso
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Atrás",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        // Contenido principal basado en el estado
        when (state) {
            HoroscopeDetailState.Loading -> LoadingState()
            is HoroscopeDetailState.Error -> ErrorState((state as HoroscopeDetailState.Error).error)
            is HoroscopeDetailState.Success -> SuccessState(state as HoroscopeDetailState.Success)
        }
    }
}

@Composable
fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ErrorState(errorMessage: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// Modifica la definición de SuccessState en tu archivo detail/HoroscopeDetailScreen.kt
@Composable
fun SuccessState(state: HoroscopeDetailState.Success) {
    val image = when (state.horoscopeModel) { // Ahora usas state.horoscopeModel
        Aries -> R.drawable.detail_aries
        Taurus -> R.drawable.detail_taurus
        Gemini -> R.drawable.detail_gemini
        Cancer -> R.drawable.detail_cancer
        Leo -> R.drawable.detail_leo
        Virgo -> R.drawable.detail_virgo
        Libra -> R.drawable.detail_libra
        Scorpio -> R.drawable.detail_scorpio
        Sagittarius -> R.drawable.detail_sagittarius
        Capricorn -> R.drawable.detail_capricorn
        Aquarius -> R.drawable.detail_aquarius
        Pisces -> R.drawable.detail_pisces
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ... (resto del código igual)
        Text(
            text = state.sign, // Usas el nombre del signo que pasas en el estado
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = state.prediction,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}