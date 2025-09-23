package com.mauro.composehoroscopo.domain

import com.mauro.composehoroscopo.domain.model.PredictionModel

interface Repository {
    suspend fun getPrediction(sign:String): PredictionModel?
}