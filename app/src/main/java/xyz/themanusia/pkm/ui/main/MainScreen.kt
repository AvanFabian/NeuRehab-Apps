package xyz.themanusia.pkm.ui.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import xyz.themanusia.pkm.ui.composables.BottomNavigationBar
import xyz.themanusia.pkm.ui.composables.NavigationScreens
import xyz.themanusia.pkm.utils.Utils.Companion.rng

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun MainScreen(
    navController: NavHostController,
    uuid: String,
    mainViewModel: MainViewModel = hiltViewModel(
        creationCallback = { factory: MainViewModel.Factory ->
            factory.create(uuid)
        }
    )
) {
    val res = mainViewModel.res.value
    val res2 = mainViewModel.res2.value

    val bpm = remember { mutableIntStateOf(res.item?.item?.bpm ?: 0) }

    LaunchedEffect(Unit) {
        while (true) {
            bpm.intValue = (rng(70, 120))
            mainViewModel.insert(uuid, bpm.intValue)
            delay(2000)
        }
    }

    Scaffold(
        bottomBar = {
            BottomAppBar(containerColor = Color.Transparent) {
                BottomNavigationBar(navController = navController)
            }
        }) { innerPadding ->
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.padding(innerPadding)) {
                NavigationScreens(
                    navController = navController,
                    bpm = bpm.intValue,
                    data = res2.data,
                    bpmSummary = res2.bpmSummary,
                    bpmHistory = res2.bpmHistory,
                )
            }
        }
    }
}