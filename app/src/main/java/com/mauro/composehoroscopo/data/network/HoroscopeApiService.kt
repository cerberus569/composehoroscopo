package com.mauro.composehoroscopo.data.network

import com.mauro.composehoroscopo.data.network.response.PredictionResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HoroscopeApiService {

    // Se mantiene el nombre de la función 'getHoroscope' según tu petición.
    // **Importante:** La ruta @GET ha sido ajustada a "horoscope/{sign}".
    // Esto es un ajuste común para APIs. Si tu API real usa una ruta diferente
    // (por ejemplo, directamente "/{sign}"), por favor ajústala en consecuencia.
    @GET("horoscope/{sign}") // Asumiendo que tu endpoint es algo como /horoscope/aries
    suspend fun getHoroscope(@Path("sign") sign:String):PredictionResponse

}