package com.eduardodenis.loteria.compose.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eduardodenis.loteria.R
import com.eduardodenis.loteria.model.MainItem
import com.eduardodenis.loteria.ui.theme.BlueColor
import com.eduardodenis.loteria.ui.theme.DarkGreen
import com.eduardodenis.loteria.ui.theme.Green
import com.eduardodenis.loteria.ui.theme.LoteriaTheme
import com.eduardodenis.loteria.ui.theme.Purple40
import com.eduardodenis.loteria.ui.theme.component.LoItemType

@Composable
fun HomeScreen(onClick: (MainItem) -> Unit) {

    val mainItems = mutableListOf(
        MainItem(1, "Mega Sena", Green, R.drawable.logo_mega_sena),
        MainItem(2, "Quina", BlueColor, R.drawable.logo_quina),
        MainItem(3, "LotoFácil", Purple40, R.drawable.logo_lotofacil)
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = DarkGreen
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(30.dp),
                columns = GridCells.Fixed(3)
            ) {
                items(mainItems) {
                    LotteryItem(it) {
                        onClick(it)
                        Log.i("Clicou", "passou o id $it")
                    }
                }
            }
        }
    }
}

@Composable
fun LotteryItem(item: MainItem, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                onClick()
                Log.i("Clicou", "clicou no item")
            }
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Black)
        ) {
            LoItemType(
                name = item.name,
                color = Color.White,
                bgColor = item.color,
                icon = item.icon
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LoteriaTheme {
        HomeScreen {}
    }
}