package com.mauro.composehoroscopo.data

import android.util.Log
import com.mauro.composehoroscopo.data.network.HoroscopeApiService
import com.mauro.composehoroscopo.data.network.response.PredictionResponse // Importa la clase PredictionResponse
import com.mauro.composehoroscopo.domain.Repository
import com.mauro.composehoroscopo.domain.model.PredictionModel // Importa PredictionModel para el tipo de retorno

// ¡NO NECESITAS ESTE IMPORT SI LA FUNCIÓN TO DOMAIN ESTÁ DENTRO DE PredictionResponse!
// Si la función toDomain estuviera en un archivo separado, sí lo necesitarías.
// import com.mauro.composehoroscopo.data.network.response.toDomain

import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService) : Repository {

    override suspend fun getPrediction(sign: String): PredictionModel? {
        Log.d("HoroscopeApp", "RepositoryImpl: Calling API for sign: $sign") // Log para depuración

        return runCatching {
            // Se llama a la API con el signo recibido
            val response = apiService.getHoroscope(sign)
            // Se mapea la respuesta de la API a tu modelo de dominio
            // Aquí se está usando la función 'toDomain()' que está DEFINIDA DENTRO
            // de la clase PredictionResponse.
            response.toDomain()
        }
            .onFailure {
                Log.e("HoroscopeApp", "RepositoryImpl: Error calling API for sign $sign: ${it.message}", it)
            }
            .getOrNull() // Devuelve el resultado si es Success, o null si es Failure
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