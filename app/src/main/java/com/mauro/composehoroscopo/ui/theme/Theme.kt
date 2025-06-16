package com.mauro.composehoroscopo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color // Importa Color si usas Color.Transparent o similares
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.mauro.composehoroscopo.ui.theme.Typography

// --- IMPORTA TUS COLORES PERSONALIZADOS DESDE Color.kt ---
// Estos son los que quieres priorizar en tu tema.
import com.mauro.composehoroscopo.ui.theme.Primary         // #1f1f1f
import com.mauro.composehoroscopo.ui.theme.PrimaryDark     // #181818
import com.mauro.composehoroscopo.ui.theme.Secondary       // #ee9361
import com.mauro.composehoroscopo.ui.theme.Accent          // #FFFFFF (igual que White)
import com.mauro.composehoroscopo.ui.theme.Gold            // #ffe78f
import com.mauro.composehoroscopo.ui.theme.Black           // #000000
import com.mauro.composehoroscopo.ui.theme.White           // #FFFFFF
import com.mauro.composehoroscopo.ui.theme.Teal700Custom   // #FF018786

// También importa los colores por defecto de M3 si planeas usarlos como fallback o para contenedores
import com.mauro.composehoroscopo.ui.theme.Purple80
import com.mauro.composehoroscopo.ui.theme.PurpleGrey80
import com.mauro.composehoroscopo.ui.theme.Pink80
import com.mauro.composehoroscopo.ui.theme.Purple40
import com.mauro.composehoroscopo.ui.theme.PurpleGrey40
import com.mauro.composehoroscopo.ui.theme.Pink40


// --- IMPORTA TU OBJETO Typography DESDE Type.kt (o como se llame tu archivo de tipografía) ---
// Asegúrate de que el nombre del objeto (aquí asumo "Typography") y la ruta sean correctos
import com.mauro.composehoroscopo.ui.theme.Typography


// --- DEFINE TUS PALETAS DE COLORES PERSONALIZADAS ---
// Reemplazamos las DarkColorScheme y LightColorScheme originales por estas.
private val AppLightColorScheme = lightColorScheme(
    primary = Primary,                  // Tu #1f1f1f (color oscuro)
    onPrimary = Accent,                 // Tu #FFFFFF (para texto sobre Primary)
    primaryContainer = Purple40,        // Puedes usar los M3 defaults para contenedores o tus propios
    onPrimaryContainer = White,         // Asumiendo que Purple40 es oscuro

    secondary = Secondary,              // Tu #ee9361 (naranja)
    onSecondary = Black,                // Texto sobre tu Secondary
    secondaryContainer = Pink40,        // Ejemplo de uso de M3 default
    onSecondaryContainer = White,       // Asumiendo que Pink40 es oscuro

    tertiary = Gold,                    // Tu #ffe78f (dorado)
    onTertiary = Black,                 // Texto sobre tu Gold
    tertiaryContainer = Teal700Custom,  // Usando tu Teal
    onTertiaryContainer = White,        // Asumiendo que Teal700Custom es oscuro

    error = Color(0xFFB00020),          // Color de error estándar
    onError = White,
    errorContainer = Color(0xFFFCD8DF),
    onErrorContainer = Color(0xFF141213),

    background = White,                 // Fondo principal de la app
    onBackground = Black,               // Texto sobre el fondo

    surface = White,                    // Superficies de componentes (Cards, etc.)
    onSurface = Black,                  // Texto sobre superficies
    surfaceVariant = PurpleGrey40,      // Variante de superficie (ej. divisor, outline sutil)
    onSurfaceVariant = White,           // Texto sobre variante de superficie

    outline = PurpleGrey40              // Color para bordes
)

private val AppDarkColorScheme = darkColorScheme(
    primary = Gold,                     // Tu #ffe78f (dorado, claro para tema oscuro)
    onPrimary = Black,                  // Texto sobre Gold
    primaryContainer = Purple80,        // Puedes usar los M3 defaults para contenedores
    onPrimaryContainer = Black,         // Asumiendo que Purple80 es claro

    secondary = Secondary,              // Tu #ee9361 (naranja, podría necesitar ajuste para oscuro)
    onSecondary = Black,
    secondaryContainer = Pink80,        // Ejemplo de uso de M3 default
    onSecondaryContainer = Black,       // Asumiendo que Pink80 es claro

    tertiary = Teal700Custom,           // Tu #FF018786 (verde azulado oscuro)
    onTertiary = White,                 // Texto sobre Teal700Custom
    tertiaryContainer = PurpleGrey80,
    onTertiaryContainer = Black,

    error = Color(0xFFCF6679),          // Color de error estándar para oscuro
    onError = Black,
    errorContainer = Color(0xFFB3261E),
    onErrorContainer = Color(0xFFF9DEDC),

    background = PrimaryDark,           // Tu #181818 (fondo oscuro)
    onBackground = White,               // Texto sobre fondo oscuro

    surface = PrimaryDark,              // Superficies de componentes en tema oscuro
    onSurface = White,                  // Texto sobre superficies oscuras
    surfaceVariant = PurpleGrey80,      // Variante de superficie oscura
    onSurfaceVariant = Black,           // Texto sobre variante de superficie

    outline = PurpleGrey80              // Color para bordes en tema oscuro
)

@Composable
fun ComposehoroscopoTheme( // Nombre de tu función de tema
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Mantienes tu preferencia por dynamicColor
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // AHORA USAMOS NUESTRAS PALETAS PERSONALIZADAS: AppDarkColorScheme y AppLightColorScheme
        darkTheme -> AppDarkColorScheme
        else -> AppLightColorScheme
    }

    // Control del color de la barra de estado y navegación (opcional pero recomendado)
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Configura el color de la barra de estado para que coincida con tu tema
            // Aquí se usa el color de fondo, pero podría ser el primario u otro.
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme

            // Opcional: Controlar la barra de navegación si es visible
            // window.navigationBarColor = colorScheme.background.toArgb()
            // WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // <--- AQUÍ se enlaza tu objeto Typography personalizado
        // Si tienes un archivo Shapes.kt y quieres usar formas personalizadas:
        // shapes = Shapes,
        content = content
    )
}