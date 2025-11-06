package com.mauro.composehoroscopo.data

import android.util.Log
import com.mauro.composehoroscopo.data.network.HoroscopeApiService
import com.mauro.composehoroscopo.data.network.response.PredictionResponse
import com.mauro.composehoroscopo.domain.Repository
import com.mauro.composehoroscopo.domain.model.PredictionModel
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService) : Repository {

    override suspend fun getPrediction(sign: String): PredictionModel? {
        println("=====> Repository: INICIO - sign recibido = $sign")
        Log.d("HoroscopeDetail", "=====> Repository: INICIO - sign recibido = $sign")

        return runCatching {
            println("=====> Repository: Llamando a API...")
            Log.d("HoroscopeDetail", "=====> Repository: Llamando a API...")

            val response = apiService.getHoroscope(sign)

            println("=====> Repository: Respuesta recibida = $response")
            Log.d("HoroscopeDetail", "=====> Repository: Respuesta recibida = $response")

            response.toDomain()
        }
            .onFailure {
                println("=====> Repository: ERROR = ${it.message}")
                Log.e("HoroscopeDetail", "=====> Repository: ERROR = ${it.message}", it)
            }
            .getOrNull()
    }
}

// **¡ESTA FUNCIÓN DE EXTENSIÓN HA SIDO ELIMINADA!**
// Ya no es necesaria aquí porque ya está definida como una función miembro
// dentro de la clase PredictionResponse.kt.
/*
fun PredictionResponse.toDomain(): PredictionModel {
    return PredictionModel(
        horoscope = this.horoscope,
        sign = this.sign
    )
}
*/