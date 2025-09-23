package com.mauro.composehoroscopo.data.network

import com.mauro.composehoroscopo.data.network.response.PredictionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HoroscopeApiService {

    @GET("/{sign}")
    suspend fun getHoroscope(@Path("sign") sign:String):PredictionResponse

}