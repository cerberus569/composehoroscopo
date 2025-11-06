package com.mauro.composehoroscopo.domain.model // Ajusta el paquete si es necesario

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mauro.composehoroscopo.R

// Asegúrate de que HoroscopeModel esté definido como un enum class en el mismo paquete o importado.
// Ejemplo:
// enum class HoroscopeModel {
//     Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra, Scorpio, Sagittarius, Capricorn, Aquarius, Pisces
// }

sealed class HoroscopeInfo(@DrawableRes val img: Int, @StringRes val name: Int, val horoscopeModel: HoroscopeModel) { // ¡Asegúrate de que 'horoscopeModel' esté aquí!
    object Aries : HoroscopeInfo(R.drawable.aries, R.string.aries, HoroscopeModel.Aries)
    object Taurus : HoroscopeInfo(R.drawable.tauro, R.string.taurus, HoroscopeModel.Taurus)
    object Gemini : HoroscopeInfo(R.drawable.geminis, R.string.gemini, HoroscopeModel.Gemini)
    object Cancer : HoroscopeInfo(R.drawable.cancer, R.string.cancer, HoroscopeModel.Cancer)
    object Leo : HoroscopeInfo(R.drawable.leo, R.string.leo, HoroscopeModel.Leo)
    object Virgo : HoroscopeInfo(R.drawable.virgo, R.string.virgo, HoroscopeModel.Virgo)
    object Libra : HoroscopeInfo(R.drawable.libra, R.string.libra, HoroscopeModel.Libra)
    object Scorpio : HoroscopeInfo(R.drawable.escorpio, R.string.scorpio, HoroscopeModel.Scorpio)
    object Sagittarius : HoroscopeInfo(R.drawable.sagitario, R.string.sagittarius, HoroscopeModel.Sagittarius)
    object Capricorn : HoroscopeInfo(R.drawable.capricornio, R.string.capricorn, HoroscopeModel.Capricorn)
    object Aquarius : HoroscopeInfo(R.drawable.aquario, R.string.aquarius, HoroscopeModel.Aquarius)
    object Pisces : HoroscopeInfo(R.drawable.piscis, R.string.pisces, HoroscopeModel.Pisces)
}

