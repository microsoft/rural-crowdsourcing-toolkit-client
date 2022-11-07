package com.microsoft.research.karya.ui.leaderboard.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.BuildConfig
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.components.v1.KaryaAppBar
import com.microsoft.research.karya.ui.leaderboard.LeaderboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    viewModel: LeaderboardViewModel,
    languageCode: String,
    onNavigateBack: () -> Unit,
    onLanguageClicked: () -> Unit
) {
    // TODO: add a Loading UI
    val leaderboardList by viewModel.leaderboardList.collectAsState()
    Scaffold(
        topBar = {
            KaryaAppBar(
                title = stringResource(id = R.string.leaderboard_title),
                versionCode = BuildConfig.VERSION_CODE.toString(),
                onBackPressed = onNavigateBack,
                languageCode = languageCode, // TODO: Update
                onLanguageCodeClicked = onLanguageClicked,
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (leaderboardList.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.leaderboard_empty),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .border(
                            BorderStroke(2.dp, Color.Black),
                            RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp)
                )
            } else {
                LazyColumn(Modifier.padding(horizontal = 16.dp)) {
                    items(leaderboardList) { item ->
                        LeaderboardItem(leaderboardRecord = item)
                        HorizontalSpacer()
                    }
                }
            }
        }
    }
}
