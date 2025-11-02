package com.mauro.composehoroscopo.presentation.luck

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mauro.composehoroscopo.R
import com.mauro.composehoroscopo.ui.theme.ComposehoroscopoTheme
import kotlinx.coroutines.launch
import android.content.Intent


@Composable
fun HoroscopeLuckScreen(
    luckViewModel: LuckViewModel = hiltViewModel()
) {
    val state by luckViewModel.state.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Estados para las animaciones
    var rouletteRotation by remember { mutableFloatStateOf(0f) }
    var showCard by remember { mutableStateOf(false) }
    var showPrediction by remember { mutableStateOf(false) }
    var cardScale by remember { mutableFloatStateOf(0f) }
    var cardTranslationY by remember { mutableFloatStateOf(1000f) }
    var previewAlpha by remember { mutableFloatStateOf(1f) }
    var predictionAlpha by remember { mutableFloatStateOf(0f) }

    // Animatable para la rotación de la ruleta
    val rotationAnimatable = remember { Animatable(0f) }
    val scaleAnimatable = remember { Animatable(0f) }
    val slideAnimatable = remember { Animatable(1000f) }

    fun spinRoulette() {
        scope.launch {
            showCard = false
            showPrediction = false
            previewAlpha = 1f
            predictionAlpha = 0f

            // Rotación de la ruleta
            val targetRotation = rouletteRotation + (360 * 4) + (0..360).random()
            rotationAnimatable.animateTo(
                targetValue = targetRotation,
                animationSpec = tween(
                    durationMillis = 2000,
                    easing = FastOutSlowInEasing
                )
            )
            rouletteRotation = targetRotation

            // Obtener nueva predicción
            luckViewModel.getRandomLuck()

            // Animación de deslizamiento de la carta hacia arriba
            showCard = true
            slideAnimatable.snapTo(1000f)
            slideAnimatable.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
            )
            cardTranslationY = slideAnimatable.value

            // Animación de crecimiento de la carta
            scaleAnimatable.snapTo(0f)
            scaleAnimatable.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            )
            cardScale = scaleAnimatable.value

            // Esperar un momento y ocultar la carta
            kotlinx.coroutines.delay(500)
            showCard = false

            // Transición de preview a prediction
            launch {
                previewAlpha = 0f
            }
            kotlinx.coroutines.delay(200)
            showPrediction = true
            predictionAlpha = 1f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = stringResource(id = R.string.luck),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
            )

            // Vista previa (ruleta)
            Box(
                modifier = Modifier
                    .weight(1f)
                    .alpha(previewAlpha),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.card_back_small),
                    contentDescription = "Roulette Background",
                    modifier = Modifier.size(300.dp),
                    contentScale = ContentScale.Fit
                )

                Image(
                    painter = painterResource(id = R.drawable.ruleta),
                    contentDescription = "Roulette",
                    modifier = Modifier
                        .size(250.dp)
                        .rotate(rotationAnimatable.value)
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures { change, dragAmount ->
                                change.consume()
                                if (Math.abs(dragAmount) > 50) {
                                    spinRoulette()
                                }
                            }
                        },
                    contentScale = ContentScale.Fit
                )
            }

            // Vista de predicción
            if (showPrediction) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .alpha(predictionAlpha),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    state.currentLuck?.let { luck ->
                        Image(
                            painter = painterResource(id = luck.image),
                            contentDescription = "Lucky Card",
                            modifier = Modifier
                                .size(200.dp)
                                .padding(bottom = 24.dp),
                            contentScale = ContentScale.Fit
                        )

                        Text(
                            text = stringResource(id = luck.text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = {
                                val prediction = context.getString(luck.text)
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, prediction)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                context.startActivity(shareIntent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Compartir")
                        }
                    }
                }
            }

            // Carta animada en reverso
            if (showCard) {
                Image(
                    painter = painterResource(id = R.drawable.card_reverse),
                    contentDescription = "Card Reverse",
                    modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer {
                            translationY = slideAnimatable.value
                            scaleX = scaleAnimatable.value
                            scaleY = scaleAnimatable.value
                        },
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Texto de instrucción
        if (!showPrediction) {
            Text(
                text = "Desliza la ruleta para descubrir tu suerte",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HoroscopeLuckScreenPreview() {
    ComposehoroscopoTheme(darkTheme = true) {
        HoroscopeLuckScreen()
    }
}