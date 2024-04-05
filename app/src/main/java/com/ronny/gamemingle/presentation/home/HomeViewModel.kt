package com.ronny.gamemingle.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ronny.gamemingle.domain.usecases.GameUseCases
import com.ronny.gamemingle.presentation.common.ListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val gameUseCases: GameUseCases
): ViewModel() {
    var gamesList = gameUseCases.getGameListUseCase().cachedIn(viewModelScope)

    val categoryList = listOf("mmorpg", "shooter", "strategy", "moba",
        "racing", "sports", "social", "sandbox", "open-world", "survival", "pvp",
        "pve", "pixel", "voxel", "zombie", "turn-based", "first-person", "third-Person",
        "top-down", "tank", "space", "sailing", "side-scroller", "superhero", "permadeath",
        "card", "battle-royale", "mmo", "mmofps", "mmotps", "3d", "2d", "anime", "fantasy",
        "sci-fi", "fighting", "action-rpg", "action", "military", "martial-arts", "flight",
        "low-spec", "tower-defense", "horror", "mmorts")

    var filterTag = mutableStateOf(
        categoryList.map {
            ListItem(
                title = it,
                isSelected = false
            )
        }
    )
    var filterPlatform by mutableStateOf("All")
    var filterSortBy by mutableStateOf("Relevance")

    var isFilterOpen by mutableStateOf(false)

    fun filterGameList(tag: String, platform: String, sortBy: String){
        gamesList = gameUseCases.getFilteredGameListUseCase(
            tag = tag,
            platform = platform,
            sortBy = sortBy
        ).cachedIn(viewModelScope)
        filterPlatform = platform
        filterSortBy = sortBy
        isFilterOpen = true
    }

    fun resetGameList(){
        gamesList = gameUseCases.getGameListUseCase().cachedIn(viewModelScope)
    }
}