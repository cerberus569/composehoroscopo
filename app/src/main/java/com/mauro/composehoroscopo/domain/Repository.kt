package com.mauro.composehoroscopo.domain

import com.mauro.composehoroscopo.domain.model.PredictionModel

// Se mantiene el nombre de la interfaz como 'Repository' según tu petición.
// (Aunque la convención es 'HoroscopeRepository', la respetamos aquí).
interface Repository {
    // Se mantiene el nombre de la función 'getPrediction' según tu petición.
    suspend fun getPrediction(sign:String): PredictionModel?
}