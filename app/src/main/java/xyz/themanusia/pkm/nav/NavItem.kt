package xyz.themanusia.pkm.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.SignalCellularAlt

sealed class NavItem {
    object HeartRate :
            Item(path = NavPath.HEART_RATE.toString(), title = NavTitle.HEART_RATE, Icons.Default.Favorite)

    object Activity :
            Item(path = NavPath.ACTIVITY.toString(), title = NavTitle.ACTIVITY, Icons.Default.SignalCellularAlt)

    object History :
            Item(path = NavPath.HISTORY.toString(), title = NavTitle.HISTORY, Icons.Default.History)
}