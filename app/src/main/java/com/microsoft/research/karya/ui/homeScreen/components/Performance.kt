package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.KaryaPreviews
import com.microsoft.research.karya.compose.components.v1.KaryaRatingBar
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.data.model.karya.modelsExtra.PerformanceSummary

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
            KaryaRatingBar(title = "Recording", rate = performanceSummary.recordingAccuracy)
            KaryaRatingBar(
                title = "Transcription",
                rate = performanceSummary.transcriptionAccuracy
            )
            KaryaRatingBar(title = "Typing", rate = performanceSummary.typingAccuracy)
            KaryaRatingBar(
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
