package com.ronny.gamemingle.di

import com.ronny.gamemingle.data.remote.GameApi
import com.ronny.gamemingle.data.repository.GameRepositoryImpl
import com.ronny.gamemingle.domain.repository.GameRepository
import com.ronny.gamemingle.domain.usecases.GameUseCases
import com.ronny.gamemingle.domain.usecases.GetFilteredGameListUseCase
import com.ronny.gamemingle.domain.usecases.GetGameListUseCase
import com.ronny.gamemingle.domain.usecases.GetGameUseCase
import com.ronny.gamemingle.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesGameApi(): GameApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GameApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGameRepository(
        gameApi: GameApi,
    ): GameRepository = GameRepositoryImpl(gameApi = gameApi)

    @Provides
    @Singleton
    fun provideGameUseCases(
        gameRepository: GameRepository
    ) = GameUseCases(
        getGameListUseCase = GetGameListUseCase(repository = gameRepository),
        getGameUseCase = GetGameUseCase(repository = gameRepository),
        getFilteredGameListUseCase = GetFilteredGameListUseCase(repository = gameRepository)
    )
}