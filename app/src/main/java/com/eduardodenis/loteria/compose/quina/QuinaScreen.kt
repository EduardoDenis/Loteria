package com.eduardodenis.loteria.compose.quina

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eduardodenis.loteria.App
import com.eduardodenis.loteria.R
import com.eduardodenis.loteria.data.Bet
import com.eduardodenis.loteria.ui.theme.LoteriaTheme
import com.eduardodenis.loteria.ui.theme.Purple40
import com.eduardodenis.loteria.ui.theme.component.AutoTextDropDown
import com.eduardodenis.loteria.ui.theme.component.LoItemType
import com.eduardodenis.loteria.ui.theme.component.LoNumberTextField
import com.eduardodenis.loteria.viewmodels.BetViewModel
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuinaScreen(
    onBackCLick: () -> Unit,
    onMenuClick: (String) -> Unit,
    betViewModel: BetViewModel = viewModel(factory = BetViewModel.Factory)
) {

    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text("Apostar") },
                    navigationIcon = {
                        IconButton(onClick = onBackCLick) {
                            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "")
                        }
                    },
                    actions = {
                        IconButton(onClick = { onMenuClick("quina") }) {
                            Icon(imageVector = Icons.Filled.List, contentDescription = "")
                        }
                    }
                )
            }
        ) { contentPadding ->
            QuinaContentScreen(
                modifier = Modifier.padding(top = contentPadding.calculateTopPadding()),
                betViewModel = betViewModel
            ) { message ->
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        }
    }
}

@Composable
fun QuinaContentScreen(
    modifier: Modifier,
    betViewModel: BetViewModel,
    formError: (String) -> Unit,
) {

//    val isInPreview = LocalInspectionMode.current
//    // O acesso ao DB só acontece se NÃO estiver no Preview
//    val db = if (isInPreview) {
//        null // Retorna nulo no Preview
//    } else {
//        (LocalContext.current.applicationContext as App).db
//    }

    val errorBets = stringResource(id = R.string.error_bets)
    val errorNumbers = stringResource(id = R.string.error_numbers_quina)

    val result = betViewModel.result.observeAsState("").value
    val showAlertDialog = betViewModel.showAlert.observeAsState(false).value

    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()

    val rules = stringArrayResource(id = R.array.array_bet_rules)
    var selectedItem by remember { mutableStateOf(rules.first()) }

    val qtdNumbers = betViewModel.qtdNumbers
    val qtdBets = betViewModel.qtdBets
    val lotteryType = BetViewModel.LotteryType.QUINA


    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        LoItemType(
            name = "Quina",
            color = Purple40,
            icon = R.drawable.logo_quina
        )

        Text(
            text = stringResource(id = R.string.announcement_quina),
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .padding(start = 15.dp)
                .padding(end = 15.dp),
        )

        Text(
            text = stringResource(id = R.string.error_bets),
            fontStyle = FontStyle.Italic,
            modifier = Modifier
                .padding(start = 15.dp)
                .padding(end = 15.dp),
        )

        LoNumberTextField(
            value = qtdNumbers,
            label = R.string.quina_rule,
            placeholder = R.string.quantity
        ) {
            betViewModel.updateQtdNumbers(it)
        }

        LoNumberTextField(
            value = qtdBets,
            label = R.string.bets,
            placeholder = R.string.bets_quantity,
            imeAction = ImeAction.Done
        ) {
            betViewModel.updateQtdBets(it)
        }

        Column(modifier = Modifier.width(280.dp)) {
            AutoTextDropDown(
                label = stringResource(id = R.string.bet_rule),
                initial = selectedItem,
                list = rules.toList(),
                onItemChanged = {
                    selectedItem = it
                }
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            OutlinedButton(
                enabled = qtdNumbers.isNotEmpty() && qtdBets.isNotEmpty(),
                onClick = {
                    val bets = qtdBets.toInt()
                    val numbers = qtdNumbers.toInt()
                    if (bets < 1 || bets > 10) {
                        formError(errorBets)
                    } else if (numbers < 5 || numbers > 15) {
                        formError(errorNumbers)
                    } else {
                        val rule = rules.indexOf(selectedItem)
                        betViewModel.updateNumbers(rule, lotteryType)
                    }
                    keyboardController?.hide()
                }
            ) {
                Text(stringResource(id = R.string.bets_generate))
            }

            OutlinedButton(onClick = {
                betViewModel.deleteHistoricBet("quina")
            }) {
                Text(text = "Limpar Histórico")
            }
        }


        Text(text = result)
    }

    if (showAlertDialog) {
        AlertDialog(
            onDismissRequest = {},
            confirmButton = {
                TextButton(onClick = {
                    betViewModel.dismissAlert()
                }) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            }, dismissButton = {
                TextButton(onClick = {
//                    Thread {
//                        for (res in resultsToSave) {
//                            val bet = Bet(type = "quina", numbers = res)
//                            db?.betDao()?.insert(bet)
//                        }
//                    }.start()
                    betViewModel.saveBet("quina")
                }) {
                    Text(text = stringResource(id = R.string.save))
                }
            },
            title = {
                Text(text = stringResource(id = R.string.app_name))
            },
            text = {
                Text(text = stringResource(id = R.string.good_luck))
            }
        )
    }
}

//private fun numberGenerator(qtd: Int, rule: Int): String {
//    val numbers = mutableSetOf<Int>()
//
//    while (true) {
//        val n = Random.nextInt(80)
//
//        if (rule == 1) {
//            if (n % 2 == 0) {
//                continue
//            }
//        } else if (rule == 2) {
//            if (n % 2 != 0) {
//                continue
//            }
//        }
//        numbers.add(n + 1)
//        if (numbers.size == qtd) {
//            break
//        }
//    }
//
//    return numbers.joinToString(" - ")
//}
//
//private fun validadeInput(input: String): String {
//    val filteredChars = input.filter { chars ->
//        chars.isDigit()
//    }
//
//    return filteredChars
//}


@Preview(showBackground = true)
@Composable
fun QuinaScreenPreview() {
    LoteriaTheme(darkTheme = false) {
        QuinaScreen(
            onBackCLick = {},
            onMenuClick = {}
        )
    }
}