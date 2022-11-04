package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.microsoft.research.karya.compose.KaryaPreviews
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.data.model.karya.modelsExtra.EarningStatus

@Composable
fun Earning(earningStatus: EarningStatus, onCLick: () -> Unit) {
    HomeScreenCard("Earnings", onCLick) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Rs ${earningStatus.weekEarned}")
                Text(text = "Last Week")
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Rs ${earningStatus.totalEarned}")
                Text(text = "Total earned")
            }
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Rs ${earningStatus.totalPaid}")
                Text(text = "Total Paid")
            }
        }
    }
}

@KaryaPreviews
@Composable
fun EarningPrev() {
    KaryaTheme {
        Surface {
            Earning(
                earningStatus = EarningStatus(
                    weekEarned = 29.43f,
                    totalEarned = 133.49f,
                    totalPaid = 133.49f
                )
            ) {}
        }
    }
}
