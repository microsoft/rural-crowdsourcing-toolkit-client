package com.microsoft.research.karya.compose.components.v1

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color

/**
 * The Android's default progressbar with height = 2x and fillMaxWidth
 */
@Composable
fun KaryaProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    color: Color = ProgressIndicatorDefaults.linearColor,
    trackColor: Color = ProgressIndicatorDefaults.linearTrackColor,
) {
    LinearProgressIndicator(progress, modifier.fillMaxWidth().scale(scaleX = 1f, scaleY = 2f), color, trackColor)
}
