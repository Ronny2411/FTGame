package com.ronny.gamemingle.presentation.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.ronny.gamemingle.R
import com.ronny.gamemingle.domain.model.GameListItem
import com.ronny.gamemingle.presentation.common.DisplayFilterFragment
import com.ronny.gamemingle.presentation.common.EmptyScreen
import com.ronny.gamemingle.presentation.common.GameCard
import com.ronny.gamemingle.presentation.common.ShimmerEffect
import com.ronny.gamemingle.presentation.navigation.Route
import com.ronny.gamemingle.ui.theme.DarkHigh
import com.ronny.gamemingle.ui.theme.DarkLow
import com.ronny.gamemingle.ui.theme.ICON_SIZE
import com.ronny.gamemingle.ui.theme.MEDIUM_PADDING
import com.ronny.gamemingle.ui.theme.SMALL_PADDING

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val gamesList = viewModel.gamesList.collectAsLazyPagingItems()
    var openFilterDialog by remember {
        mutableStateOf(false)
    }
    var categoryMap by remember {
        viewModel.filterTag
    }
    val platform = listOf("PC", "Browser", "All")
    var (selectedPlatform, setSelectedPlatform) = remember { mutableStateOf(viewModel.filterPlatform) }
    val sortBy = listOf("Release-Date", "Popularity", "Alphabetical", "Relevance")
    var (selectedSortBy, setSelectedSortBy) = remember { mutableStateOf(viewModel.filterSortBy) }
    viewModel.filterPlatform = selectedPlatform
    viewModel.filterSortBy = selectedSortBy
    val activity = LocalContext.current as Activity
    BackHandler {
        if (openFilterDialog){
            openFilterDialog = false
        }
        else if (viewModel.isFilterOpen){
            setSelectedPlatform("All")
            setSelectedSortBy("Relevance")
            for (i in 0..categoryMap.size - 1) {
                categoryMap[i].isSelected = false
            }
            viewModel.isFilterOpen = false
            viewModel.resetGameList()
        } else {
            activity.finish()
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkHigh)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(DarkHigh)){
            if(viewModel.isFilterOpen){
                Icon(imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back_icon),
                    modifier = Modifier
                        .padding(start = SMALL_PADDING)
                        .size(ICON_SIZE)
                        .align(Alignment.CenterStart)
                        .clickable {
                            setSelectedPlatform("All")
                            setSelectedSortBy("Relevance")
                            for (i in 0..categoryMap.size - 1) {
                                categoryMap[i].isSelected = false
                            }
                            viewModel.isFilterOpen = false
                            viewModel.resetGameList()
                        },
                    tint = DarkLow)
            }
            Image(painter = painterResource(id = R.drawable.freetogame_logo),
                contentDescription = stringResource(R.string.app_logo),
                modifier = Modifier
                    .size(height = 50.dp, width = 150.dp)
                    .align(Alignment.Center)
            )
            Icon(painter= painterResource(id = R.drawable.ic_filter),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = MEDIUM_PADDING)
                    .size(ICON_SIZE)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        openFilterDialog = !openFilterDialog
                    },
                tint = DarkLow
            )
        }
        if (openFilterDialog){
            DisplayFilterFragment(
                categoryMap,
                platform,
                viewModel.filterPlatform,
                setSelectedPlatform,
                sortBy,
                viewModel.filterSortBy,
                setSelectedSortBy,
                onResetClick = {
                    setSelectedPlatform("All")
                    setSelectedSortBy("Relevance")
                    for (i in 0..categoryMap.size-1){
                        categoryMap[i].isSelected = false
                    }
                },
                onSearchClick = {
                    openFilterDialog = false
                    val listItem =  categoryMap.filter { it.isSelected }
                    val tagList = listItem.map { it.title }
                    val tag = tagList.joinToString(separator = ".")
                    viewModel.filterGameList(
                        tag = tag,
                        platform = selectedPlatform.toLowerCase(),
                        sortBy = selectedSortBy.toLowerCase()
                    )
                },
                onCheckClicked = {i->
                     categoryMap = categoryMap.mapIndexed { index, listItem ->
                        if (i == index){
                            listItem.copy(isSelected = !listItem.isSelected)
                        } else listItem
                    }
                }
            )
        } else {
            val result = handlePagingResult(games = gamesList)
            if (result){
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(SMALL_PADDING),
                    verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
                ){
                    items(count = gamesList.itemCount){
                        gamesList[it]?.let {game->
                            GameCard(game = game) {
                                navController.navigate(
                                    Route.DetailsScreen.createRoute(it.toString())
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    games: LazyPagingItems<GameListItem>
): Boolean {
    val loadState = games.loadState
    val error = when{
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        else -> null
    }
    return when{
        loadState.refresh is LoadState.Loading->{
            ShimmerEffect()
            false
        }
        error!=null->{
            EmptyScreen(error = error)
            false
        }
        games.itemCount<1-> {
            EmptyScreen()
            false
        }
        else->true
    }
}