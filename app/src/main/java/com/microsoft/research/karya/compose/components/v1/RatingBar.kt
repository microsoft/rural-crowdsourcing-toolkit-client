package com.microsoft.research.karya.compose.components.v1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.theme.sdp

/**
 * @param title Rating parameter
 * @param rate Rating between 0 and 1
 */
@Composable
fun KaryaRatingBar(
    title: String,
    rate: Float,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween
) {
    val n = (rate * 5).toInt()
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.sdp),
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = MaterialTheme.typography.headlineSmall)
        HorizontalSpacer()
        Row {
            repeat(5) {
                val color =
                    if (it <= n) MaterialTheme.colorScheme.onSurface.copy(0.5f) else MaterialTheme.colorScheme.surfaceVariant
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = color
                )
            }
        }
    }
}
