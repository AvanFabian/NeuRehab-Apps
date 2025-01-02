package xyz.themanusia.pkm.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import xyz.themanusia.pkm.model.BPM
import xyz.themanusia.pkm.nav.NavItem
import xyz.themanusia.pkm.ui.composables.tabs.ActivityScreen
import xyz.themanusia.pkm.ui.composables.tabs.HeartRateScreen
import xyz.themanusia.pkm.ui.composables.tabs.HistoryScreen

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun NavigationScreens(
    navController: NavHostController,
    bpm: Int,
    data: List<BarData>,
    bpmSummary: List<BPM>,
    bpmHistory: List<BPM>
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        NavHost(navController, startDestination = NavItem.HeartRate.path) {
            composable(NavItem.HeartRate.path) { HeartRateScreen(bpm) }
            composable(NavItem.Activity.path) { ActivityScreen(data, bpmSummary) }
            composable(NavItem.History.path) { HistoryScreen(bpmHistory) }
        }
    }
}