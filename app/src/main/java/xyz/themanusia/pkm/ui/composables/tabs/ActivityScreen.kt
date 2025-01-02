package xyz.themanusia.pkm.ui.composables.tabs

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jaikeerthick.composable_graphs.composables.bar.BarGraph
import com.jaikeerthick.composable_graphs.composables.bar.model.BarData
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphColors
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphFillType
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphStyle
import com.jaikeerthick.composable_graphs.composables.bar.style.BarGraphVisibility
import com.jaikeerthick.composable_graphs.style.LabelPosition
import xyz.themanusia.pkm.R
import xyz.themanusia.pkm.model.BPM
import xyz.themanusia.pkm.ui.theme.PictonBlue
import xyz.themanusia.pkm.ui.theme.RadicalRed
import xyz.themanusia.pkm.utils.Utils

@Composable
fun ActivityScreen(data: List<BarData>, bpmSummary: List<BPM>) {
    val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    val chosen = remember {
        mutableIntStateOf(today - 1)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Column(modifier = Modifier.padding(31.dp)) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(id = R.string.select_day),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
            }
            BarGraph(
                data = data, modifier = Modifier.fillMaxWidth(),
                style = BarGraphStyle(
                    colors = BarGraphColors(
                        clickHighlightColor = RadicalRed,
                        fillType = BarGraphFillType.Solid(color = PictonBlue)
                    ),
                    yAxisLabelPosition = LabelPosition.LEFT,
                    visibility = BarGraphVisibility(
                        isYAxisLabelVisible = true,
                    )
                ),
                onBarClick = { bar ->
                    data.find { it.x == bar.x }?.let {
                        val index = data.indexOf(it)
                        chosen.intValue = index
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 41.dp, start = 31.dp, end = 31.dp, bottom = 31.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = Utils.getDay(chosen.intValue + 1),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Row(
                        modifier = Modifier.padding(bottom = 34.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "AVG",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 15.dp),
                        )
                        Text(
                            text = bpmSummary[chosen.intValue].avg.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                        Text(
                            text = "BPM",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                    Row(
                        modifier = Modifier.padding(bottom = 34.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "MIN",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 15.dp),
                        )
                        Text(
                            text = bpmSummary[chosen.intValue].min.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                        Text(
                            text = "BPM",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "MAX",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 15.dp),
                        )
                        Text(
                            text = bpmSummary[chosen.intValue].max.toString(),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(end = 8.dp),
                        )
                        Text(
                            text = "BPM",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ActivityPreview() {
    ActivityScreen(
        listOf(
            BarData("Mon", 10f),
            BarData("Tue", 20f),
            BarData("Wed", 30f),
            BarData("Thu", 40f),
            BarData("Fri", 50f),
            BarData("Sat", 60f),
            BarData("Sun", 70f),
        ),
        listOf(
            BPM(0, "", 0, 0, 0),
            BPM(0, "", 0, 0, 0),
            BPM(0, "", 0, 0, 0),
            BPM(0, "", 0, 0, 0),
            BPM(0, "", 0, 0, 0),
            BPM(0, "", 0, 0, 0),
            BPM(0, "", 0, 0, 0),
        )
    )
}