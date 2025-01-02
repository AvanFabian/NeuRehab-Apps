package xyz.themanusia.pkm.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import xyz.themanusia.pkm.ui.theme.PKMTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT))

        val data = intent.getStringExtra("user") ?: ""

        setContent {
            MainView(data)
        }
    }
}

@Composable
fun MainView(uuid: String) {
    PKMTheme {
        val navController = rememberNavController()
        Surface(modifier = Modifier.fillMaxSize()) {
            MainScreen(navController = navController, uuid = uuid)
        }
    }
}