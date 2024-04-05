package com.ronny.gamemingle.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ronny.gamemingle.domain.usecases.GameUseCases
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

    fun filterGameList(tag: String, platform: String, sortBy: String){
        gamesList = gameUseCases.getFilteredGameListUseCase(
            tag = tag,
            platform = platform,
            sortBy = sortBy
        ).cachedIn(viewModelScope)
        Log.d("viewmodel","$tag $platform $sortBy")
    }
}