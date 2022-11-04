package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.theme.sdp

@Composable
fun HomeScreenCard(
    title: String,
    onCLick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    Column(
        Modifier
            .fillMaxWidth()
            .shadow(2.dp)
            .background(Color.White)
            .clickable { onCLick() }
            .padding(16.sdp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineSmall
        )
        HorizontalSpacer()
        content()
    }
}
