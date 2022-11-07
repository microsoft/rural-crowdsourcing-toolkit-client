package com.microsoft.research.karya.ui.leaderboard.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.components.VerticalSpacer
import com.microsoft.research.karya.compose.components.v1.KaryaCard
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.data.model.karya.LeaderboardRecord

@Composable
fun LeaderboardItem(leaderboardRecord: LeaderboardRecord) {
    KaryaCard {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = leaderboardRecord.rank.toString())
            VerticalSpacer()
            Text(
                text = leaderboardRecord.name,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primary
            )
            Text(text = "${leaderboardRecord.xp} points")
        }
    }
}

@Preview
@Composable
fun LeaderboardItemPrev() {
    KaryaTheme {
        Surface {
            LazyColumn {
                items(11) {
                    LeaderboardItem(
                        leaderboardRecord = LeaderboardRecord(
                            id = "21",
                            rank = it,
                            xp = 2342,
                            name = "Soumya"
                        )
                    )
                    HorizontalSpacer()
                }
            }
        }
    }
}
