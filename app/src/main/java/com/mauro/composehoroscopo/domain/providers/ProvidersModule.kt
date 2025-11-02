package com.mauro.composehoroscopo.domain.providers

import com.mauro.composehoroscopo.presentation.providers.RandomCardProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidersModule {

    @Provides
    @Singleton
    fun provideRandomCardProvider(): RandomCardProvider {
        return RandomCardProvider()
    }
}