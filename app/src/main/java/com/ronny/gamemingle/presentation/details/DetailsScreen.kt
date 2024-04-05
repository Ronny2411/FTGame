package com.ronny.gamemingle.presentation.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.ronny.gamemingle.R
import com.ronny.gamemingle.domain.model.Game
import com.ronny.gamemingle.domain.model.MinimumSystemRequirements
import com.ronny.gamemingle.domain.model.Screenshot
import com.ronny.gamemingle.ui.theme.DarkHigh
import com.ronny.gamemingle.ui.theme.DarkLow
import com.ronny.gamemingle.ui.theme.DarkMedium
import com.ronny.gamemingle.ui.theme.EXTRA_SMALL_PADDING
import com.ronny.gamemingle.ui.theme.INDICATOR_SIZE
import com.ronny.gamemingle.ui.theme.ICON_SIZE
import com.ronny.gamemingle.ui.theme.INFO_ICON_SIZE
import com.ronny.gamemingle.ui.theme.MEDIUM_PADDING
import com.ronny.gamemingle.ui.theme.SMALL_PADDING
import com.ronny.gamemingle.ui.theme.titleColor
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailsScreen(
    game: Game,
    navController: NavController
) {
    val pageSize = game.screenshots?.size?.plus(1) ?: 0
    val pages = mutableListOf<String>()
    pages.add(game.thumbnail.toString())
    for (i in 0..pageSize-2){
        game.screenshots?.get(i)?.image?.let { pages.add(it) }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkHigh)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .background(DarkHigh)){
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back_icon),
                modifier = Modifier
                    .padding(start = SMALL_PADDING)
                    .size(ICON_SIZE)
                    .align(Alignment.CenterStart)
                    .clickable { navController.popBackStack() },
                tint = DarkLow)
            Image(painter = painterResource(id = R.drawable.freetogame_logo),
                contentDescription = stringResource(R.string.app_logo),
                modifier = Modifier
                    .size(height = 50.dp, width = 150.dp)
                    .align(Alignment.Center)
            )
        }
        val pagerState = rememberPagerState(
        ) {
            pageSize
        }
        HorizontalPager(state = pagerState,
            modifier = Modifier
                .padding(start = SMALL_PADDING, end = SMALL_PADDING)
                .clip(RoundedCornerShape(EXTRA_SMALL_PADDING))) {
            PagerImage(page = pages[it])
        }
        PageIndicator(pageSize = pageSize, selectedPage = pagerState.currentPage,
            modifier = Modifier
                .padding(top = EXTRA_SMALL_PADDING, bottom = EXTRA_SMALL_PADDING)
                .align(Alignment.CenterHorizontally)
                )
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(SMALL_PADDING)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = game.title.toString(),
                    color = MaterialTheme.colors.titleColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                val uriHandler = LocalUriHandler.current
                Icon(painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = "",
                    tint = DarkLow,
                    modifier = Modifier
                        .size(ICON_SIZE)
                        .clickable {
                            uriHandler.openUri(game.game_url.toString())
                        })
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = MEDIUM_PADDING, top = MEDIUM_PADDING)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoBox(icon = painterResource(id = R.drawable.ic_genre),
                    iconColor = DarkLow,
                    bigText = "Genre",
                    smallText = game.genre.toString(),
                    textColor = MaterialTheme.colors.titleColor)
                InfoBox(icon = painterResource(id = R.drawable.ic_platform),
                    iconColor = DarkLow,
                    bigText = "Platform",
                    smallText = game.platform.toString(),
                    textColor = MaterialTheme.colors.titleColor)
                InfoBox(icon = painterResource(id = R.drawable.ic_publish),
                    iconColor = DarkLow,
                    bigText = "Publisher",
                    smallText = game.publisher.toString(),
                    textColor = MaterialTheme.colors.titleColor)
            }
            var tabIndex by remember { mutableStateOf(1) }
            val tabTitles = listOf("Description", "Minimum Requirements")
            val tabPagerState = rememberPagerState(initialPage = 0) {
                tabTitles.size
            }
            val coroutineScope = rememberCoroutineScope()
            Column {
                TabRow(selectedTabIndex = tabPagerState.currentPage,
                    containerColor = DarkLow,
                    modifier = Modifier.clip(
                        RoundedCornerShape(EXTRA_SMALL_PADDING)
                    )
                    ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title) },
                            selected = tabPagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    tabPagerState.animateScrollToPage(index)
                                    tabIndex = index
                                }
                            },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.LightGray
                        )
                    }
                }
                HorizontalPager(
                    state = tabPagerState,
                ) {tabIndex->
                    when (tabIndex) {
                        0 -> DescriptionDisplay(
                            desc = game.description.toString(),
                            developer = game.developer.toString(),
                            webSiteUrl = game.freetogame_profile_url.toString(),
                            releaseDate = game.release_date.toString())
                        1 -> game.minimum_system_requirements?.let { MinimumRequirementDisplay(it) }
                    }
                }
            }

        }

    }
}

@Composable
fun PageIndicator(
    pageSize : Int,
    modifier: Modifier = Modifier,
    selectedPage : Int,
    selectedColor : Color = DarkLow,
    unselectedColor: Color = Color.DarkGray
){
    Row (modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween){
        repeat(pageSize){page->
            Box(modifier = Modifier
                .size(INDICATOR_SIZE)
                .clip(CircleShape)
                .background(if (page == selectedPage) selectedColor else unselectedColor))
        }
    }
}

@Composable
fun PagerImage(page: String) {
    val painter = rememberAsyncImagePainter(model = page,
        error = painterResource(id = R.drawable.placeholder),
        placeholder = painterResource(id = R.drawable.placeholder)
    )
    Image(painter = painter,
        contentDescription = stringResource(R.string.game_image),
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(Color.Black)
    )
}

@Composable
fun InfoBox(
    icon: Painter,
    iconColor: Color,
    bigText: String,
    smallText: String,
    textColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        androidx.compose.material.Icon(
            painter = icon,
            contentDescription = stringResource(R.string.info_icon),
            tint = iconColor,
            modifier = Modifier
                .padding(end = SMALL_PADDING)
                .size(INFO_ICON_SIZE)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material.Text(
                text = bigText,
                color = textColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.Black
            )
            androidx.compose.material.Text(
                text = smallText,
                color = textColor,
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                modifier = Modifier.alpha(ContentAlpha.medium),
            )
        }
    }
}

@Composable
fun MinimumRequirementDisplay(minReq: MinimumSystemRequirements) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Text(text = "Processor: ",
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = minReq.processor.toString(),
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize)
        Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        Text(text = "Graphics: ",
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = minReq.graphics.toString(),
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize)
        Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        Text(text = "Ram: ",
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = minReq.memory.toString(),
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize)
        Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        Text(text = "Storage: ",
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = minReq.storage.toString(),
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize)
        Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        Text(text = "Operating System: ",
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(text = minReq.os.toString(),
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize)
    }
}

@Composable
fun DescriptionDisplay(
    desc: String,
    developer: String,
    webSiteUrl: String,
    releaseDate: String) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Text(text = desc,
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize)
        Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        Text(text = "Release Date: $releaseDate",
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize)
        Text(text = "Developed By: $developer",
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize)
        Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        val annotatedLinkString = buildAnnotatedString {
            val str = "For more Information visit our website"
            val startIndex = str.indexOf("website")
            val endIndex = startIndex + 7
            append(str)
            addStyle(
                style = SpanStyle(
                    color = Color(0xff64B5F6),
                    textDecoration = TextDecoration.Underline
                ), start = startIndex, end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = webSiteUrl,
                start = startIndex,
                end = endIndex
            )
        }
        val uriHandler = LocalUriHandler.current

        ClickableText(style = TextStyle(
            color = Color.White.copy(alpha = ContentAlpha.medium),
            fontSize = MaterialTheme.typography.subtitle1.fontSize
        ),
            text = annotatedLinkString,
            onClick = {
                annotatedLinkString
                    .getStringAnnotations("URL", it, it)
                    .firstOrNull()?.let { stringAnnotation ->
                        uriHandler.openUri(stringAnnotation.item)
                    }
            }
        )

    }
}

@Preview
@Composable
fun DetailsScreenPrev() {
    DetailsScreen(game = Game(
        id= 452,
        title= "Call Of Duty: Warzone",
    thumbnail= "https://www.freetogame.com/g/452/thumbnail.jpg",
    status= "Live",
    short_description= "A standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare.",
    description= "Call of Duty: Warzone is both a standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. Warzone features two modes — the general 150-player battle royle, and “Plunder”. The latter mode is described as a “race to deposit the most Cash”. In both modes players can both earn and loot cash to be used when purchasing in-match equipment, field upgrades, and more. Both cash and XP are earned in a variety of ways, including completing contracts.\r\n\r\nAn interesting feature of the game is one that allows players who have been killed in a match to rejoin it by winning a 1v1 match against other felled players in the Gulag.\r\n\r\nOf course, being a battle royale, the game does offer a battle pass. The pass offers players new weapons, playable characters, Call of Duty points, blueprints, and more. Players can also earn plenty of new items by completing objectives offered with the pass.",
    game_url= "https://www.freetogame.com/open/call-of-duty-warzone",
    genre= "Shooter",
    platform= "Windows",
    publisher= "Activision",
    developer= "Infinity Ward",
    release_date= "2020-03-10",
    freetogame_profile_url= "https://www.freetogame.com/call-of-duty-warzone",
    minimum_system_requirements= MinimumSystemRequirements(
        os= "Windows 7 64-Bit (SP1) or Windows 10 64-Bit",
        processor= "Intel Core i3-4340 or AMD FX-6300",
        memory= "8GB RAM",
    graphics= "NVIDIA GeForce GTX 670 / GeForce GTX 1650 or Radeon HD 7950",
    storage= "175GB HD space"
    ),
    screenshots= listOf(
    Screenshot(
        id= 1124,
        image= "https://www.freetogame.com/g/452/Call-of-Duty-Warzone-1.jpg"
    ),
    Screenshot(
        id= 1125,
        image= "https://www.freetogame.com/g/452/Call-of-Duty-Warzone-2.jpg"
    ),
    Screenshot(
        id= 1126,
        image= "https://www.freetogame.com/g/452/Call-of-Duty-Warzone-3.jpg"
    ),
    Screenshot(
        id= 1127,
        image= "https://www.freetogame.com/g/452/Call-of-Duty-Warzone-4.jpg"
    )
    )
    ),
        rememberNavController()
    )
}