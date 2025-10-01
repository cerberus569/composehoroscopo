package com.mauro.composehoroscopo.data

import android.util.Log
import com.mauro.composehoroscopo.data.network.HoroscopeApiService
import com.mauro.composehoroscopo.data.network.response.PredictionResponse
import com.mauro.composehoroscopo.domain.Repository
import com.mauro.composehoroscopo.domain.model.PredictionModel
import retrofit2.Retrofit
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: HoroscopeApiService) : Repository {

    override suspend fun getPrediction(sign: String): PredictionModel? {
        runCatching { apiService.getHoroscope(sign) }
            .onSuccess { return it.toDomain() }
            .onFailure { Log.i("aris", "Ha ocurrido un error ${it.message}") }
        return null
    }
}
