package com.ronny.gamemingle.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ronny.gamemingle.data.remote.FilteredGameListPagingSource
import com.ronny.gamemingle.data.remote.GameApi
import com.ronny.gamemingle.data.remote.GameListPagingSource
import com.ronny.gamemingle.domain.model.Game
import com.ronny.gamemingle.domain.model.GameListItem
import com.ronny.gamemingle.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GameRepositoryImpl(
    private val gameApi: GameApi
): GameRepository {
    override fun getGameList(): Flow<PagingData<GameListItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                GameListPagingSource(
                    gameApi = gameApi
                )
            }
        ).flow
    }

    override suspend fun getGame(id: Int): Game {
        return gameApi.getGame(id = id)
    }

    override fun getFilteredGameList(
        tag: String,
        platform: String,
        sortBy: String,
    ): Flow<PagingData<GameListItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                FilteredGameListPagingSource(
                    gameApi = gameApi,
                    tag = tag,
                    platform = platform,
                    sortBy = sortBy
                )
            }
        ).flow
    }
}