package com.microsoft.research.karya.compose.components.v1

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun KaryaCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(8.dp),
        color = Color.White,
        content = content
    )
}
