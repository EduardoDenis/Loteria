package com.eduardodenis.loteria.compose.quina

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eduardodenis.loteria.R
import com.eduardodenis.loteria.ui.theme.Black
import com.eduardodenis.loteria.ui.theme.LoteriaTheme
import com.eduardodenis.loteria.ui.theme.Purple40
import com.eduardodenis.loteria.ui.theme.PurpleGrey40
import com.eduardodenis.loteria.ui.theme.component.LoItemType
import com.eduardodenis.loteria.ui.theme.component.LoNumberTextField
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun QuinaScreen() {
    val errorBets = stringResource(id = R.string.error_bets)
    val errorNumbers = stringResource(id = R.string.error_numbers)

    var qtdNumbers by remember { mutableStateOf("") }
    var qtdBets by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var showAlertDialog by remember { mutableStateOf(false) }

    val snackbarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->


        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            LoItemType(
                name = "Quina",
                color = Purple40,
                bgColor = Black,
                icon = R.drawable.logo_quina
            )

            Text(
                text = stringResource(id = R.string.announcement),
                fontStyle = FontStyle.Italic,
                modifier = Modifier
                    .padding(start = 15.dp)
                    .padding(end = 15.dp),
            )

            LoNumberTextField(
                value = qtdNumbers,
                label = R.string.mega_rule,
                placeholder = R.string.quantity
            ) {
                if (it.length < 3) {
                    qtdNumbers = validadeInput(it)
                }
            }

            LoNumberTextField(
                value = qtdBets,
                label = R.string.bets,
                placeholder = R.string.bets_quantity,
                imeAction = ImeAction.Done
            ) {
                if (it.length < 3) {
                    qtdBets = validadeInput(it)
                }
            }

            OutlinedButton(
                enabled = qtdNumbers.isNotEmpty() && qtdBets.isNotEmpty(),
                onClick = {
                    val bets = qtdBets.toInt()
                    val numbers = qtdNumbers.toInt()
                    if (bets < 1 || bets > 10) {
                        scope.launch {
                            snackbarHostState.showSnackbar(errorBets)
                        }
                    } else if (numbers < 6 || numbers > 15) {
                        scope.launch {
                            snackbarHostState.showSnackbar(errorNumbers)
                        }
                    } else {
                        result = ""
                        for (i in 1..bets) {
                            result += "[$i]  "
                            result += numberGenerator(numbers)
                            result += "\n\n"
                        }
                        showAlertDialog = true
                    }
                    keyboardController?.hide()
                }
            ) {
                Text(stringResource(id = R.string.bets_generate))
            }

            Text(text = result)

        }
        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    TextButton(onClick = { showAlertDialog = false }) {
                        Text(text = stringResource(id = android.R.string.ok))
                    }
                }, dismissButton = {
                    TextButton(onClick = { showAlertDialog = false }) {
                        Text(text = stringResource(id = android.R.string.cancel))
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
}

private fun numberGenerator(qtd: Int): String {
    val numbers = mutableSetOf<Int>()

    while (true) {
        val n = Random.nextInt(60)
        numbers.add(n + 1)
        if (numbers.size == qtd) {
            break
        }
    }

    return numbers.joinToString(" - ")
}

private fun validadeInput(input: String): String {
    val filteredChars = input.filter { chars ->
        chars.isDigit()
    }

    return filteredChars
}

@Preview(showBackground = true)
@Composable
fun QuinaScreenPreview() {
    LoteriaTheme(darkTheme = false) {
        QuinaScreen()
    }
}