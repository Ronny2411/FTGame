package com.ronny.gamemingle.domain.usecases

import androidx.paging.PagingData
import com.ronny.gamemingle.domain.model.GameListItem
import com.ronny.gamemingle.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GetFilteredGameListUseCase(
    private val repository: GameRepository
) {
    operator fun invoke(
        tag: String,
        platform: String,
        sortBy: String
    ): Flow<PagingData<GameListItem>> {
        return repository.getFilteredGameList(
            tag = tag,
            platform = platform,
            sortBy = sortBy
        )
    }
}