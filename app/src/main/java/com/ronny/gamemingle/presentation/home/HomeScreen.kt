package com.ronny.gamemingle.presentation.home

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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.ronny.gamemingle.presentation.common.ListItem
import com.ronny.gamemingle.presentation.common.ShimmerEffect
import com.ronny.gamemingle.presentation.navigation.Route
import com.ronny.gamemingle.ui.theme.DarkHigh
import com.ronny.gamemingle.ui.theme.DarkLow
import com.ronny.gamemingle.ui.theme.ICON_SIZE
import com.ronny.gamemingle.ui.theme.MEDIUM_PADDING
import com.ronny.gamemingle.ui.theme.SMALL_PADDING
import java.util.Locale

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val gamesList = viewModel.gamesList.collectAsLazyPagingItems()
    var openFilterDialog by remember {
        mutableStateOf(false)
    }
    val categoryList = listOf("mmorpg", "shooter", "strategy", "moba",
        "racing", "sports", "social", "sandbox", "open-world", "survival", "pvp",
        "pve", "pixel", "voxel", "zombie", "turn-based", "first-person", "third-Person",
        "top-down", "tank", "space", "sailing", "side-scroller", "superhero", "permadeath",
        "card", "battle-royale", "mmo", "mmofps", "mmotps", "3d", "2d", "anime", "fantasy",
        "sci-fi", "fighting", "action-rpg", "action", "military", "martial-arts", "flight",
        "low-spec", "tower-defense", "horror", "mmorts")

    var categoryMap by remember {
        mutableStateOf(
            categoryList.map {
                ListItem(
                    title = it,
                    isSelected = false
                )
            }
        )
    }
    val platform = listOf("PC", "Browser", "All")
    val (selectedPlatform, setSelectedPlatform) = remember { mutableStateOf("All") }
    val sortBy = listOf("Release-Date", "Popularity", "Alphabetical", "Relevance")
    val (selectedSortBy, setSelectedSortBy) = remember { mutableStateOf("Relevance") }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkHigh)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(DarkHigh)){
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
                selectedPlatform,
                setSelectedPlatform,
                sortBy,
                selectedSortBy,
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
                                navController.navigate(Route.DetailsScreen.createRoute(it.toString()))
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