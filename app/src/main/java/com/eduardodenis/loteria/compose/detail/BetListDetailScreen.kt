package com.eduardodenis.loteria.compose.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eduardodenis.loteria.App
import com.eduardodenis.loteria.R
import com.eduardodenis.loteria.data.Bet
import com.eduardodenis.loteria.ui.theme.LoteriaTheme
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BetListDetailScreen(type: String) {

//    val isInPreview = LocalInspectionMode.current
    val bets = remember { mutableStateListOf<Bet>() }
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))

    val db = (LocalContext.current.applicationContext as App).db


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
            LazyColumn {
                itemsIndexed(bets) { index, bet ->
                    Text(
                        stringResource(
                            id = R.string.list_response,
                            index,
                            sdf.format(bet.date),
                            bet.numbers
                        ), modifier = Modifier.padding(top = contentPadding.calculateTopPadding())
                    )

                }
            }
        }
    }

    Thread {

        val res = db.betDao().getNumberByType(type)
        bets.clear()
        bets.addAll(res)
    }.start()
}

@Preview(showBackground = true)
@Composable
fun BetListDetailPreview() {
    LoteriaTheme {
        BetListDetailScreen(type = "megasena")
    }
}
