package com.ronny.gamemingle.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ronny.gamemingle.domain.model.GameListItem

class FilteredGameListPagingSource(
    private val gameApi: GameApi,
    private val tag: String,
    private val platform: String,
    private val sortBy: String,
): PagingSource<Int, GameListItem>() {

    private var totalGamesCount = 0

    override fun getRefreshKey(state: PagingState<Int, GameListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GameListItem> {
        val page = params.key ?: 1
        return try {
            if (tag.isNotEmpty()){
                val gameResponse = gameApi.getFilteredGameList(
                    tag = tag,
                    platform = platform,
                    sortBy = sortBy
                )
                totalGamesCount+=gameResponse.size
                val games = gameResponse.distinctBy { it.title } //Remove Duplicate
                LoadResult.Page(
                    data = games,
                    nextKey = if (totalGamesCount == gameResponse.size) null else page + 1,
                    prevKey = null
                )
            } else {
                val gameResponse = gameApi.getFilteredGameList(
                    platform = platform,
                    sortBy = sortBy
                )
                totalGamesCount+=gameResponse.size
                val games = gameResponse.distinctBy { it.title } //Remove Duplicate
                LoadResult.Page(
                    data = games,
                    nextKey = if (totalGamesCount == gameResponse.size) null else page + 1,
                    prevKey = null
                )
            }

        } catch (e: Exception){
            e.printStackTrace()
            LoadResult.Error(
                throwable = e
            )
        }
    }
}