package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.microsoft.research.karya.R
import com.microsoft.research.karya.data.model.karya.modelsExtra.PerformanceSummary

@Composable
fun Performance(performanceSummary: PerformanceSummary, onCLick: () -> Unit) {
    HomeScreenDetailCard(title = "Performance", onCLick) {
        PerformanceRate(title = "Recording", rate = performanceSummary.recordingAccuracy)
        PerformanceRate(title = "Transcription", rate = performanceSummary.transcriptionAccuracy)
        PerformanceRate(title = "Typing", rate = performanceSummary.typingAccuracy)
        PerformanceRate(
            title = "Image Annotation", rate = performanceSummary.imageAnnotationAccuracy
        )
    }
}

@Composable
fun PerformanceRate(
    title: String,
    /** Rating between 0 and 1 */
    rate: Float
) {
    val n = (rate * 5).toInt()
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(text = title)
        Row {
            repeat(5) {
                val res = if (it <= n) R.drawable.ic_filled_star else R.drawable.ic_outline_star
                Icon(
                    painter = painterResource(res),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
