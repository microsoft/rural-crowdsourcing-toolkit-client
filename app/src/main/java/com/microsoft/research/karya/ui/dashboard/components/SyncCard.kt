package com.microsoft.research.karya.ui.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.KaryaPreviews
import com.microsoft.research.karya.compose.components.v1.KaryaCard
import com.microsoft.research.karya.compose.components.v1.KaryaProgressBar
import com.microsoft.research.karya.compose.theme.KaryaTheme

@Composable
fun SyncCard(
    modifier: Modifier = Modifier,
    isProgressVisible: Boolean = false,
    progress: Float = 0f,
    isError: Boolean = false,
    errorMessage: String = "",
    onClick: () -> Unit
) {
    KaryaCard(modifier.clickable { onClick() }) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.sync_prompt),
                style = MaterialTheme.typography.titleLarge
            )

            AnimatedVisibility(
                visible = isProgressVisible
            ) {
                KaryaProgressBar(progress, modifier = Modifier.padding(top = 16.dp))
            }

            AnimatedVisibility(visible = isError) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = errorMessage,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@KaryaPreviews
@Composable
fun SyncCardPrev() {
    KaryaTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            SyncCard(
                isError = true,
                isProgressVisible = true,
                progress = 0.5f,
                errorMessage = "This is an error message"
            ) {}
        }
    }
}

@KaryaPreviews
@Composable
fun SyncCardPrev1() {
    KaryaTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            SyncCard(
                isProgressVisible = true,
                progress = 0.5f,
            ) {}
        }
    }
}

@KaryaPreviews
@Composable
fun SyncCardPrev2() {
    KaryaTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            SyncCard(
                isError = true,
                errorMessage = "This is an error message"
            ) {}
        }
    }
}
