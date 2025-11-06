package com.mauro.composehoroscopo.domain.usecase


import com.mauro.composehoroscopo.domain.Repository
import javax.inject.Inject

class GetPredictionUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(sign: String) = repository.getPrediction(sign)
}