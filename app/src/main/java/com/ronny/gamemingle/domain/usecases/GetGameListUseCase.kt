package com.ronny.gamemingle.domain.usecases

import androidx.paging.PagingData
import com.ronny.gamemingle.domain.model.GameListItem
import com.ronny.gamemingle.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GetGameListUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(): Flow<PagingData<GameListItem>> {
        return repository.getGameList()
    }
}