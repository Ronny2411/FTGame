package com.ronny.gamemingle.presentation.navigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.ronny.gamemingle.R
import com.ronny.gamemingle.presentation.common.AnimateDetailSimmerItem
import com.ronny.gamemingle.presentation.common.EmptyScreen
import com.ronny.gamemingle.presentation.details.DetailsScreen
import com.ronny.gamemingle.presentation.details.DetailsViewModel
import com.ronny.gamemingle.presentation.home.HomeScreen
import com.ronny.gamemingle.presentation.home.HomeViewModel
import com.ronny.gamemingle.ui.theme.DarkHigh

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Route.HomeScreen.route){
        composable(Route.HomeScreen.route){
            HomeScreen(navController)
        }
        composable(Route.DetailsScreen.route){navBackStackEntry->
            val gameId = navBackStackEntry.arguments?.getString("id")
            Log.d("gameId",gameId.toString())
            val viewModel: DetailsViewModel = hiltViewModel()
            LaunchedEffect(key1 = true){
                try {
                    if (gameId != null) {
                        viewModel.getGame(gameId.toInt())
                    }
                } catch (e: Exception){
                    viewModel.error.value = true
                    viewModel.inProgress.value = false
                }
            }
            if (viewModel.inProgress.value){
                AnimateDetailSimmerItem()
            }
            if (viewModel.error.value){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(DarkHigh)) {
                    Image(painter = painterResource(id = R.drawable.freetogame_logo),
                        contentDescription = stringResource(R.string.app_logo),
                        modifier = Modifier
                            .size(height = 50.dp, width = 150.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    EmptyScreen()
                }
            }
            if (viewModel.game.value.id!=null) {
                val game = viewModel.game.value
                DetailsScreen(game = game, navController)
            }
        }
    }
}