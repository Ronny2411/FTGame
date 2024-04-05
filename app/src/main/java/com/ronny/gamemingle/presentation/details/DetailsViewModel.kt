package com.ronny.gamemingle.presentation.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ronny.gamemingle.domain.model.Game
import com.ronny.gamemingle.domain.usecases.GameUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val gameUseCase: GameUseCases
): ViewModel() {

    var game = mutableStateOf(Game())

    var inProgress = mutableStateOf(false)

    var error = mutableStateOf(false)

    suspend fun getGame(id: Int){
        inProgress.value = true
        game.value = gameUseCase.getGameUseCase(id = id)
        inProgress.value = false
    }
}