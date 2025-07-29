package com.eduardodenis.loteria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eduardodenis.loteria.ui.theme.Green
import com.eduardodenis.loteria.ui.theme.LoteriaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaTheme {
                HomeScreen()

            }
        }
    }
}

@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        LotteryItem("Mega Sena")

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
                .background(Green)
        ) {
            Image(
                painter = painterResource(id = R.drawable.trevo),
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .padding(10.dp)
            )
            Text(
                text = "Mega Sena",
                color = Color.White,
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