package xyz.themanusia.pkm.ui.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import xyz.themanusia.pkm.nav.NavItem
import xyz.themanusia.pkm.ui.theme.EbonyClay
import xyz.themanusia.pkm.ui.theme.PictonBlue
import xyz.themanusia.pkm.ui.theme.Silver

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navItems = listOf(NavItem.HeartRate, NavItem.Activity, NavItem.History)
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar(
        containerColor = EbonyClay,
        contentColor = PictonBlue,
    ) {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                colors = NavigationBarItemColors(
                    selectedIconColor = PictonBlue,
                    selectedTextColor = PictonBlue,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedIconColor = Silver,
                    unselectedTextColor = Silver,
                    disabledIconColor = Silver,
                    disabledTextColor = Silver
                ),
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(item.path) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}