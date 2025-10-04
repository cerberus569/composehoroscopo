package com.mauro.composehoroscopo.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mauro.composehoroscopo.domain.model.HoroscopeModel
import com.mauro.composehoroscopo.domain.usecase.GetPredictionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HoroscopeDetailViewModel @Inject constructor(private val getPredictionUseCase: GetPredictionUseCase) :
    ViewModel() {

    private var _state = MutableStateFlow<HoroscopeDetailState>(HoroscopeDetailState.Loading)
    val state: StateFlow<HoroscopeDetailState> = _state

    fun getHoroscope(horoscopeType: HoroscopeModel) {
        viewModelScope.launch {
            _state.value = HoroscopeDetailState.Loading
            val result = withContext(Dispatchers.IO) { getPredictionUseCase(horoscopeType.name) } // Pasa el nombre para la API
            if(result != null){
                // ¡CORRECCIÓN APLICADA AQUÍ!
                // Usamos 'result.horoscope' para la predicción, ya que así se llama en tu PredictionModel.
                // Usamos 'result.sign' para el nombre del signo, que coincide con tu PredictionModel.
                _state.value = HoroscopeDetailState.Success(
                    prediction = result.horoscope, // <-- CAMBIO AQUÍ: antes era result.prediction
                    sign = result.sign,
                    horoscopeModel = horoscopeType
                )
            } else {
                _state.value = HoroscopeDetailState.Error("Ha ocurrido un error, inténtelo más tarde")
            }
        }
    }
}