package com.eduardodenis.loteria

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.eduardodenis.loteria.compose.LoteriaApp
import com.eduardodenis.loteria.ui.theme.LoteriaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoteriaTheme {
                LoteriaApp()
            }
        }
    }
}