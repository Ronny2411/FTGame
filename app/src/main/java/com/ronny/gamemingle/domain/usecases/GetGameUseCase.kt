package com.ronny.gamemingle.domain.usecases

import com.ronny.gamemingle.domain.model.Game
import com.ronny.gamemingle.domain.repository.GameRepository

class GetGameUseCase(
    private val repository: GameRepository
) {
    suspend operator fun invoke(id: Int): Game {
        return repository.getGame(id = id)
    }
}