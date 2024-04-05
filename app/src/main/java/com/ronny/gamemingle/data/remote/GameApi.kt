package com.ronny.gamemingle.data.remote

import com.ronny.gamemingle.data.remote.dto.GameList
import com.ronny.gamemingle.domain.model.Game
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {
    @GET("games")
    suspend fun getGameList(): GameList

    @GET("games")
    suspend fun getFilteredGameList(
        @Query("platform") platform: String,
        @Query("sort-by") sortBy: String
    ): GameList

    @GET("filter")
    suspend fun getFilteredGameList(
        @Query("tag") tag: String,
        @Query("platform") platform: String,
        @Query("sort-by") sortBy: String
    ): GameList

    @GET("game")
    suspend fun getGame(
        @Query("id") id: Int,
    ): Game
}