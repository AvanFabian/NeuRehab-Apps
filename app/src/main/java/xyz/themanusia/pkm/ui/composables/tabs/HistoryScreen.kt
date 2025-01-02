package xyz.themanusia.pkm.ui.composables.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.themanusia.pkm.model.BPM
import xyz.themanusia.pkm.ui.theme.Silver
import xyz.themanusia.pkm.utils.Utils

@Composable
fun HistoryScreen(bpms: List<BPM>) {

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 31.dp, start = 31.dp, end = 31.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "History", fontWeight = FontWeight.Medium, fontSize = 30.sp)
        }
        HistoryList(bpms)
    }
}

@Composable
fun HistoryList(data: List<BPM>) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 50.dp)
            .fillMaxWidth(),
    ) {
        items(data) {
            val epoch = Utils.dateToEpoch(it.time)
            val day = Utils.getDay(Utils.getDayByEpoch(epoch))
            val date = Utils.getDateByEpoch2(epoch)

            Column {
                Box(
                    modifier = Modifier
                        .background(color = Silver)
                        .fillMaxWidth()
                        .height(2.dp)
                )
                Column(
                    modifier = Modifier.padding(
                        start = 30.dp, top = 10.dp, bottom = 10.dp
                    )
                ) {
                    Text(text = day, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    ProvideTextStyle(
                        TextStyle(
                            fontWeight = FontWeight.Medium, fontSize = 24.sp
                        )
                    ) {
                            Text(
                                text = date,
                                modifier = Modifier.padding(end = 5.dp)
                            )
                    }
                }
            }
        }
    }

}