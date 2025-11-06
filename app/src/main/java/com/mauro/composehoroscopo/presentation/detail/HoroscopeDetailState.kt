package com.mauro.composehoroscopo.presentation.detail

import com.mauro.composehoroscopo.domain.model.HoroscopeModel

sealed class HoroscopeDetailState {
    data object Loading:HoroscopeDetailState()
    data class Error(val error:String):HoroscopeDetailState()
    // El orden de los parámetros es importante. La predicción es el texto principal, el signo es su nombre
    // y horoscopeModel es el enum que usaremos para la imagen y otras referencias.
    data class Success(val prediction:String, val sign:String, val horoscopeModel: HoroscopeModel):HoroscopeDetailState()
}