package com.eduardodenis.loteria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eduardodenis.loteria.component.LoItemType
import com.eduardodenis.loteria.component.LoNumberTextField
import com.eduardodenis.loteria.ui.theme.DarkGreen
import com.eduardodenis.loteria.ui.theme.LoteriaTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = AppRouter.HOME.route
                ) {
                    composable(AppRouter.HOME.route) {
                        HomeScreen {
                            navController.navigate(AppRouter.FORM.route)
                        }
                    }
                    composable(AppRouter.FORM.route) {
                        FormScreen()
                    }
                }

            }
        }
    }
}

enum class AppRouter(val route: String) {
    HOME("home"),
    FORM("form")

}


@Composable
fun HomeScreen(onClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = DarkGreen
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column {
                LotteryItem("Mega Sena", onClick = onClick)
            }
        }

    }
}


@Composable
fun LotteryItem(name: String, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .wrapContentSize()
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Black)
        ) {
            LoItemType(
                name,
                color = Color.White,
                image = R.drawable.logo_mega_sena
            )
            LoItemType(
                name,
                color = Color.White,
                image = R.drawable.logo_lotofacil
            )

        }
    }
}

@Composable
fun FormScreen() {
    val errorBets = stringResource(id = R.string.error_bets)
    val errorNumbers = stringResource(id = R.string.error_numbers)
    var qtdNumbers by remember { mutableStateOf("") }
    var qtdBets by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
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
                name = "Mega Sena",
                color = Color.Black,
                bgColor = Color.Transparent,
                image = R.drawable.logo_mega_sena
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
                    }
                    keyboardController?.hide()
                }
            ) {
                Text(stringResource(id = R.string.bets_generate))
            }

            Text(text = result)

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
fun GreetingPreview() {
    LoteriaTheme {
        HomeScreen {}
    }
}

@Preview(showBackground = true)
@Composable
fun FormScreenPreview() {
    LoteriaTheme {
        FormScreen()
    }
}