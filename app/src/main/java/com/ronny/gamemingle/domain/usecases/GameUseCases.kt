package com.ronny.gamemingle.domain.usecases

data class GameUseCases(
    val getGameListUseCase: GetGameListUseCase,
    val getGameUseCase: GetGameUseCase,
    val getFilteredGameListUseCase: GetFilteredGameListUseCase
)
