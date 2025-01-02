package xyz.themanusia.pkm.ui.homepage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import xyz.themanusia.pkm.ui.theme.PKMTheme

@AndroidEntryPoint
class HomepageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            PKMTheme {
                Homepage()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    PKMTheme {
        Homepage()
    }
}