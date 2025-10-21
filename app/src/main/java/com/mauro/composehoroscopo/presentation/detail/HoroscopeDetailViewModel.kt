package com.mauro.composehoroscopo.presentation.detail

import android.util.Log
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
        println("=====> ViewModel: INICIO - horoscopeType = $horoscopeType")
        Log.d("HoroscopeDetail", "=====> ViewModel: INICIO - horoscopeType = $horoscopeType")

        viewModelScope.launch {
            _state.value = HoroscopeDetailState.Loading
            val signName = horoscopeType.name.lowercase()

            println("=====> ViewModel: Sign lowercase = $signName")
            Log.d("HoroscopeDetail", "=====> ViewModel: Sign lowercase = $signName")

            val result = withContext(Dispatchers.IO) {
                getPredictionUseCase(signName)
            }

            println("=====> ViewModel: Result = $result")
            Log.d("HoroscopeDetail", "=====> ViewModel: Result = $result")

            if(result != null){
                println("=====> ViewModel: SUCCESS - horoscope = ${result.horoscope}")
                println("=====> ViewModel: SUCCESS - sign = ${result.sign}")
                Log.d("HoroscopeDetail", "=====> ViewModel: SUCCESS - horoscope = ${result.horoscope}")
                Log.d("HoroscopeDetail", "=====> ViewModel: SUCCESS - sign = ${result.sign}")

                _state.value = HoroscopeDetailState.Success(
                    prediction = result.horoscope,
                    sign = result.sign,
                    horoscopeModel = horoscopeType
                )
            } else {
                println("=====> ViewModel: ERROR - result is null")
                Log.e("HoroscopeDetail", "=====> ViewModel: ERROR - result is null")

                _state.value = HoroscopeDetailState.Error("Ha ocurrido un error, inténtelo más tarde")
            }
        }
    }
}