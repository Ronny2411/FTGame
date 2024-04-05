package com.ronny.gamemingle.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ronny.gamemingle.R
import com.ronny.gamemingle.ui.theme.ABOUT_PLACEHOLDER_HEIGHT
import com.ronny.gamemingle.ui.theme.DarkHigh
import com.ronny.gamemingle.ui.theme.DarkLow
import com.ronny.gamemingle.ui.theme.DarkMedium
import com.ronny.gamemingle.ui.theme.EXTRA_SMALL_PADDING
import com.ronny.gamemingle.ui.theme.HERO_ITEM_HEIGHT
import com.ronny.gamemingle.ui.theme.ICON_SIZE
import com.ronny.gamemingle.ui.theme.INFO_ICON_SIZE
import com.ronny.gamemingle.ui.theme.LARGE_PADDING
import com.ronny.gamemingle.ui.theme.MEDIUM_PADDING
import com.ronny.gamemingle.ui.theme.NAME_PLACEHOLDER_HEIGHT
import com.ronny.gamemingle.ui.theme.PAGE_INDICATOR_WIDTH
import com.ronny.gamemingle.ui.theme.RATING_PLACEHOLDER_HEIGHT
import com.ronny.gamemingle.ui.theme.RATING_PLACEHOLDER_WIDTH
import com.ronny.gamemingle.ui.theme.SMALL_PADDING
import com.ronny.gamemingle.ui.theme.ShimmerDarkGrey
import com.ronny.gamemingle.ui.theme.ShimmerMediumGrey

@Composable
fun ShimmerEffect() {
    LazyColumn(
        contentPadding = PaddingValues(all = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ){
        items(count = 2){
            AnimateSimmerItem()
        }
    }
}

@Composable
fun AnimateSimmerItem() {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    ShimmerItem(alpha = alphaAnim)
}

@Composable
fun AnimateDetailSimmerItem() {
    val transition = rememberInfiniteTransition()
    val alphaAnim by transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    DetailShimmerItem(alpha = alphaAnim)
}

@Composable
fun ShimmerItem(alpha: Float) {
    Surface(modifier = Modifier
        .height(HERO_ITEM_HEIGHT)
        .fillMaxWidth(),
        color = ShimmerDarkGrey,
        shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
    ) {
        Column(modifier = Modifier
            .padding(MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom) {
            Surface(modifier = Modifier
                .alpha(alpha)
                .fillMaxWidth(1f)
                .fillMaxHeight(0.55f)
                .height(NAME_PLACEHOLDER_HEIGHT),
                color = DarkLow,
                shape = RoundedCornerShape(SMALL_PADDING)
            ) {}
            Spacer(modifier = Modifier.padding(SMALL_PADDING))
            Surface(modifier = Modifier
                .alpha(alpha)
                .fillMaxWidth(0.5f)
                .height(NAME_PLACEHOLDER_HEIGHT),
                color = DarkLow,
                shape = RoundedCornerShape(MEDIUM_PADDING)
            ) {}
            Spacer(modifier = Modifier.padding(SMALL_PADDING))
            repeat(2){
                Surface(modifier = Modifier
                    .alpha(alpha)
                    .fillMaxWidth()
                    .height(ABOUT_PLACEHOLDER_HEIGHT),
                    color = DarkLow,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}
                Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Surface(modifier = Modifier
                    .alpha(alpha)
                    .size(height = RATING_PLACEHOLDER_HEIGHT, width = RATING_PLACEHOLDER_WIDTH),
                    color = DarkLow,
                    shape = RoundedCornerShape(SMALL_PADDING)
                ) {}
                    Surface(modifier = Modifier
                        .alpha(alpha)
                        .size(RATING_PLACEHOLDER_HEIGHT),
                        color = DarkLow,
                        shape = RoundedCornerShape(SMALL_PADDING)
                    ) {}

            }
        }
    }
}

@Composable
fun DetailShimmerItem(alpha: Float) {
    Surface(modifier = Modifier
        .fillMaxSize(),
        color = ShimmerDarkGrey
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(DarkHigh)){
                Image(painter = painterResource(id = R.drawable.freetogame_logo),
                    contentDescription = stringResource(R.string.app_logo),
                    modifier = Modifier
                        .size(height = 50.dp, width = 150.dp)
                        .align(Alignment.Center)
                )
            }
            Column(modifier = Modifier
                .padding(SMALL_PADDING)) {
                Surface(modifier = Modifier
                    .alpha(alpha)
                    .fillMaxWidth(1f)
                    .fillMaxHeight(0.30f)
                    .height(NAME_PLACEHOLDER_HEIGHT),
                    color = DarkLow,
                    shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                ) {}
                Spacer(modifier = Modifier.padding(SMALL_PADDING))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Surface(modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth(0.5f)
                        .height(NAME_PLACEHOLDER_HEIGHT),
                        color = DarkLow,
                        shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                    ) {}
                    Surface(modifier = Modifier
                        .alpha(alpha)
                        .size(NAME_PLACEHOLDER_HEIGHT),
                        color = DarkLow,
                        shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                    ) {}
                }
                Spacer(modifier = Modifier.padding(SMALL_PADDING))
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Surface(modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth(0.27f)
                        .height(PAGE_INDICATOR_WIDTH),
                        color = DarkLow,
                        shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                    ) {}
                    Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
                    Surface(modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth(0.4f)
                        .height(PAGE_INDICATOR_WIDTH),
                        color = DarkLow,
                        shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                    ) {}
                    Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
                    Surface(modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth(0.75f)
                        .height(PAGE_INDICATOR_WIDTH),
                        color = DarkLow,
                        shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                    ) {}
                }
                Spacer(modifier = Modifier.padding(SMALL_PADDING))
                Surface(modifier = Modifier
                    .alpha(alpha)
                    .fillMaxWidth()
                    .height(PAGE_INDICATOR_WIDTH),
                    color = DarkLow,
                    shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                ) {}
                Spacer(modifier = Modifier.padding(SMALL_PADDING))
                repeat(12){
                    Surface(modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth()
                        .height(MEDIUM_PADDING),
                        color = DarkLow,
                        shape = RoundedCornerShape(EXTRA_SMALL_PADDING)
                    ) {}
                    Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
                }
            }
        }
    }
}


@Preview
@Composable
fun DetailShimmerItemPrev() {
    AnimateDetailSimmerItem()
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ShimmerItemPrev() {
    AnimateSimmerItem()
}