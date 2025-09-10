package com.eduardodenis.loteria

import android.R.attr.onClick
import android.app.Activity
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.core.view.WindowCompat
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eduardodenis.loteria.component.LoItemType
import com.eduardodenis.loteria.component.LoNumberTextField
import com.eduardodenis.loteria.ui.theme.DarkGreen
import com.eduardodenis.loteria.ui.theme.LoteriaTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

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
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        var qtdNumbers by remember { mutableStateOf("") }
        var qtdBets by remember { mutableStateOf("") }
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
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
                    qtdNumbers = validadeInput(it)
                }
            }

            OutlinedButton(
                onClick = {

                }
            ) { Text(stringResource(id = R.string.bets_generate)) }


        }
    }
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