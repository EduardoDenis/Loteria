package com.eduardodenis.loteria

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.eduardodenis.loteria.ui.theme.LoteriaTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaTheme {

                // Obter a cor do fundo do tema e o View da janela
                val view = LocalView.current
                val surfaceColor = MaterialTheme.colorScheme.surface

                // Usar o SideEffect para ajustar as cores da barra de status
                SideEffect {
                    val isLightBackground = surfaceColor.luminance() > 0.5f
                    val window = (view.context as Activity).window
                    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                        isLightBackground
                    window.statusBarColor = Color.Transparent.toArgb()

                }

                HomeScreen()
            }

        }
    }
}

@Composable
fun HomeScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.DarkGray
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            LotteryItem("Mega Sena")
        }

    }
}


@Composable
fun LotteryItem(name: String) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .wrapContentSize()
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Green)
        ) {
            Image(
                painter = painterResource(id = R.drawable.trevo),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )
            Text(
                text = "Mega Sena",
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoteriaTheme {
        HomeScreen()
    }
}