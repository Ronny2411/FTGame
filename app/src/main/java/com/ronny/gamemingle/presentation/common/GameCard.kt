package com.ronny.gamemingle.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.ronny.gamemingle.R
import com.ronny.gamemingle.domain.model.GameListItem
import com.ronny.gamemingle.ui.theme.DarkLow
import com.ronny.gamemingle.ui.theme.EXTRA_SMALL_PADDING
import com.ronny.gamemingle.ui.theme.HERO_ITEM_HEIGHT
import com.ronny.gamemingle.ui.theme.ICON_SIZE
import com.ronny.gamemingle.ui.theme.MEDIUM_PADDING
import com.ronny.gamemingle.ui.theme.SMALL_PADDING
import com.ronny.gamemingle.ui.theme.titleColor


@Composable
fun GameCard(
    game: GameListItem,
    onClick: (Int) -> Unit,
) {
    val painter = rememberAsyncImagePainter(model = "${game.thumbnail}",
        error = painterResource(id = R.drawable.placeholder),
        placeholder = painterResource(id = R.drawable.placeholder))

    Card(
            modifier = Modifier
                .height(HERO_ITEM_HEIGHT)
                .clickable { game.id?.let { onClick(it) } },
        ) {
            Surface(shape = RoundedCornerShape(
                topStart = EXTRA_SMALL_PADDING,
                topEnd = EXTRA_SMALL_PADDING)) {
                Image(painter = painter,
                    contentDescription = stringResource(R.string.game_image),
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth()
                        .background(Color.Black)
                )
            }
            Surface(modifier = Modifier
                .fillMaxHeight(1f)
                .fillMaxWidth()
                .background(DarkLow),
                color = Color.Black.copy(alpha = ContentAlpha.medium),
                shape = RoundedCornerShape(
                    bottomStart = EXTRA_SMALL_PADDING,
                    bottomEnd = EXTRA_SMALL_PADDING
                )
            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .padding(MEDIUM_PADDING)) {
                    Text(text = game.title.toString(),
                        color = MaterialTheme.colors.titleColor,
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(text = game.short_description.toString(),
                        color = Color.White.copy(alpha = ContentAlpha.medium),
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Row(modifier = Modifier
                        .padding(top = SMALL_PADDING, bottom = SMALL_PADDING)
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = game.publisher.toString(),
                            color = Color.White.copy(alpha = ContentAlpha.medium),
                            fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        )
                        if (game.platform == "Web Browser"){
                            Icon(painter = painterResource(id = R.drawable.ic_browser),
                                contentDescription = stringResource(R.string.web_browser),
                                modifier = Modifier.size(ICON_SIZE),
                                tint = Color.White.copy(alpha = ContentAlpha.medium)
                            )
                        } else if (game.platform == "PC (Windows)"){
                            Icon(painter = painterResource(id = R.drawable.ic_windows),
                                contentDescription = stringResource(R.string.windows),
                                modifier = Modifier.size(ICON_SIZE),
                                tint = Color.White.copy(alpha = ContentAlpha.medium)
                            )
                        } else {
                            Row {
                                Icon(painter = painterResource(id = R.drawable.ic_windows),
                                    contentDescription = stringResource(R.string.windows),
                                    modifier = Modifier
                                        .size(ICON_SIZE)
                                        .padding(end = EXTRA_SMALL_PADDING),
                                    tint = Color.White.copy(alpha = ContentAlpha.medium)
                                )
                                Icon(painter = painterResource(id = R.drawable.ic_browser),
                                    contentDescription = stringResource(R.string.web_browser),
                                    modifier = Modifier
                                        .size(ICON_SIZE)
                                        .padding(start = EXTRA_SMALL_PADDING),
                                    tint = Color.White.copy(alpha = ContentAlpha.medium)
                                )
                            }
                        }
                    }
                }
            }
        }
}

@Preview
@Composable
fun GameCardPrev() {
    GameCard(game = GameListItem(
        id= 573,
        title= "Titan Revenge",
    thumbnail= "https://www.freetogame.com/g/573/thumbnail.jpg",
    short_description= "A 3D Norse-themed browser MMORPG developed and published by Game Hollywood Games",
    game_url= "https://www.freetogame.com/open/titan-revenge",
    genre= "MMORPG",
    platform= "Web Browser",
    publisher= "Game Hollywood Games",
    developer= "Game Hollywood Games",
    release_date= "2023-12-20",
    freetogame_profile_url= "https://www.freetogame.com/titan-revenge"
    )) {

    }
}