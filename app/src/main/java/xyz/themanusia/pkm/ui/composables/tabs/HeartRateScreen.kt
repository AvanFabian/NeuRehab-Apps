package xyz.themanusia.pkm.ui.composables.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.themanusia.pkm.ui.theme.PictonBlue
import xyz.themanusia.pkm.ui.theme.RadicalRed
import xyz.themanusia.pkm.ui.theme.RollingStone

@Composable
fun HeartRateScreen(bpm: Int) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier.padding(31.dp)
            ) {
                Column(modifier = Modifier.padding(bottom = 25.dp)) {
                    Text(text = "Patient", fontSize = 30.sp)
                    Text(text = "Heart Rate", fontSize = 30.sp)
                }
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .size(280.dp)
                            .clip(CircleShape)
                            .border(width = 2.dp, color = PictonBlue, shape = CircleShape)
                            .padding(38.dp),
                        contentAlignment = Alignment.TopCenter,

                        ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = "BPM",
                                tint = RadicalRed,
                                modifier = Modifier.size(50.dp)
                            )
                            Text(
                                text = "$bpm",
                                fontSize = 60.sp,
                            )
                            Text(
                                text = "BPM", fontSize = 32.sp, color = RollingStone
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScreenPreview() {
    HeartRateScreen(69)
}