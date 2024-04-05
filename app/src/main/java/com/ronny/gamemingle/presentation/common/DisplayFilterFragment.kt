package com.ronny.gamemingle.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.ronny.gamemingle.ui.theme.DarkHigh
import com.ronny.gamemingle.ui.theme.DarkLow
import com.ronny.gamemingle.ui.theme.EXTRA_SMALL_PADDING
import com.ronny.gamemingle.ui.theme.titleColor

@Composable
fun DisplayFilterFragment(
    categoryMap: List<ListItem>,
    platform: List<String>,
    selectedPlatform: String,
    setSelectedPlatform: (String) -> Unit,
    sortBy: List<String>,
    selectedSortBy: String,
    setSelectedSortBy: (String) -> Unit,
    onSearchClick:() -> Unit,
    onCheckClicked:(Int) -> Unit,
    onResetClick:() -> Unit
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(DarkHigh)
        .nestedScroll(rememberNestedScrollInteropConnection())) {
        Text(text = "Select Category",
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        LazyHorizontalGrid(rows = GridCells.Fixed(5),
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .padding(EXTRA_SMALL_PADDING)){
            items(categoryMap.size){i->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = categoryMap[i].isSelected,
                        onCheckedChange = {
                            onCheckClicked(i)
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = DarkLow
                        ))
                    Text(text = categoryMap[i].title,
                        color = MaterialTheme.colors.titleColor,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize)
                }
            }
        }

        Text(text = "Select Platform",
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        RadioGroup(mItems = platform,
            selected = selectedPlatform,
            setSelected = setSelectedPlatform)

        Text(text = "Sort By",
            color = MaterialTheme.colors.titleColor,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(EXTRA_SMALL_PADDING))
        RadioGroup(mItems = sortBy,
            selected = selectedSortBy,
            setSelected = setSelectedSortBy)

        Row(modifier = Modifier
            .fillMaxSize()
            .padding(EXTRA_SMALL_PADDING),
            horizontalArrangement = Arrangement.End) {
            Button(onClick = {
                onSearchClick()
            },
                shape = RoundedCornerShape(EXTRA_SMALL_PADDING),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkLow
                ),
                modifier = Modifier
                    .padding(EXTRA_SMALL_PADDING)
            ) {
                Text(text = "Search")
            }

            Button(onClick = {
                onResetClick()
            },
                shape = RoundedCornerShape(EXTRA_SMALL_PADDING),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkLow
                ),
                modifier = Modifier
                    .padding(EXTRA_SMALL_PADDING)
            ) {
                Text(text = "Reset")
            }
        }
    }
}


data class ListItem(
    val title: String,
    var isSelected: Boolean
)

@Composable
fun RadioGroup(
    mItems: List<String>,
    selected: String,
    setSelected: (selected: String) -> Unit,
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(EXTRA_SMALL_PADDING),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            mItems.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item,
                        color = MaterialTheme.colors.titleColor,
                        fontSize = MaterialTheme.typography.subtitle2.fontSize)
                    RadioButton(
                        selected = selected == item,
                        onClick = {
                            setSelected(item)
                        },
                        enabled = true,
                        colors = RadioButtonDefaults.colors(
                            selectedColor = DarkLow,
                            unselectedColor = Color.White
                        )
                    )
                }
            }
        }
    }
}

