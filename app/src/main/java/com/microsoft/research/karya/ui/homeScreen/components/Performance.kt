package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.KaryaPreviews
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.compose.theme.sdp
import com.microsoft.research.karya.data.model.karya.modelsExtra.PerformanceSummary

@Composable
fun PerformanceRate(
    title: String,
    /** Rating between 0 and 1 */
    rate: Float
) {
    val n = (rate * 5).toInt()
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.sdp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        Row {
            repeat(5) {
                val color =
                    if (it <= n) MaterialTheme.colorScheme.onSurface.copy(0.5f) else MaterialTheme.colorScheme.surfaceVariant
                Icon(
                    painter = painterResource(R.drawable.ic_filled_star),
                    contentDescription = null,
                    tint = color
                )
            }
        }
    }
}

@Composable
fun PerformanceCard(
    performanceSummary: PerformanceSummary,
    onCLick: () -> Unit
) {
    HomeScreenCard(
        title = stringResource(id = R.string.performance_label),
        onCLick = onCLick
    ) {
        Column {
            PerformanceRate(title = "Recording", rate = performanceSummary.recordingAccuracy)
            PerformanceRate(
                title = "Transcription",
                rate = performanceSummary.transcriptionAccuracy
            )
            PerformanceRate(title = "Typing", rate = performanceSummary.typingAccuracy)
            PerformanceRate(
                title = "Image Annotation", rate = performanceSummary.imageAnnotationAccuracy
            )
        }
    }
}

@KaryaPreviews
@Composable
fun PerformanceCardPrev() {
    val performanceSummary = PerformanceSummary(
        recordingAccuracy = 0.7f,
        transcriptionAccuracy = 0.2f,
        typingAccuracy = 0.5f,
        imageAnnotationAccuracy = 0.9f
    )
    KaryaTheme {
        Surface {
            PerformanceCard(performanceSummary) {
            }
        }
    }
}
