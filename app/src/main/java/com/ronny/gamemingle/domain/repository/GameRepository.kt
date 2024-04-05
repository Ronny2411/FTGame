package com.ronny.gamemingle.domain.repository

import androidx.paging.PagingData
import com.ronny.gamemingle.domain.model.Game
import com.ronny.gamemingle.domain.model.GameListItem
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getGameList(): Flow<PagingData<GameListItem>>
    suspend fun getGame(id: Int): Game
    fun getFilteredGameList(
        tag: String,
        platform: String,
        sortBy: String
    ): Flow<PagingData<GameListItem>>
}