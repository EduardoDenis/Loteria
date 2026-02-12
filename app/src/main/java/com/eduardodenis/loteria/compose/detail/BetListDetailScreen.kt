package com.eduardodenis.loteria.compose.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardodenis.loteria.App
import com.eduardodenis.loteria.R
import com.eduardodenis.loteria.data.Bet
import com.eduardodenis.loteria.ui.theme.LoteriaTheme
import com.eduardodenis.loteria.viewmodels.BetListDetailViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BetListDetailScreen(
    betViewModel: BetListDetailViewModel = viewModel(factory = BetListDetailViewModel.Factory)
) {

//    val isInPreview = LocalInspectionMode.current
    val bets = betViewModel.bets.collectAsState().value
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale("pt", "BR"))

//    val db = (LocalContext.current.applicationContext as App).db


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
            LazyColumn(
                modifier = Modifier.padding(contentPadding),
                verticalArrangement = Arrangement.spacedBy(10git .dp)
            ) {
                itemsIndexed(bets) { index, bet ->
                    Text(
                        stringResource(
                            id = R.string.list_response,
                            index,
                            sdf.format(bet.date),
                            bet.numbers
                        ), modifier = Modifier.padding(horizontal = 16.dp)
                    )

                }
            }
        }
    }

//    Thread {
//
//        val res = db.betDao().getNumberByType(type)
//        bets.clear()
//        bets.addAll(res)
//    }.start()
}

@Preview(showBackground = true)
@Composable
fun BetListDetailPreview() {
    LoteriaTheme {
        BetListDetailScreen()
    }
}
