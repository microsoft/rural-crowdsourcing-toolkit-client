package com.microsoft.research.karya.ui.homeScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.microsoft.research.karya.BuildConfig
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.components.v1.KaryaAppBar
import com.microsoft.research.karya.compose.theme.sdp
import com.microsoft.research.karya.ui.homeScreen.components.Earning
import com.microsoft.research.karya.ui.homeScreen.components.HomeScreenCard
import com.microsoft.research.karya.ui.homeScreen.components.PerformanceCard
import com.microsoft.research.karya.ui.homeScreen.components.ProfileCard
import com.microsoft.research.karya.ui.homeScreen.components.TaskSummaryCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    navController: NavController,
    languageCode: String,
    onLanguageClicked: () -> Unit
) {

    val points by viewModel.points.collectAsState()
    val name by viewModel.name.collectAsState()
    val phone by viewModel.phoneNumber.collectAsState()

    val taskSummary by viewModel.taskSummary.collectAsState()
    val performance by viewModel.performanceSummary.collectAsState()
    val earningStatus by viewModel.earningStatus.collectAsState()

    /* val total = with(taskSummary) {
        assignedMicrotasks + completedMicrotasks + submittedMicrotasks + verifiedMicrotasks + skippedMicrotasks + expiredMicrotasks
    } */

    Scaffold(
        topBar = {
            KaryaAppBar(
                title = stringResource(id = R.string.home_screen_title),
                versionCode = BuildConfig.VERSION_CODE.toString(),
                onBackPressed = { /* Ignore */ },
                languageCode = languageCode, // TODO: Update
                onLanguageCodeClicked = onLanguageClicked,
            )
        }
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(MaterialTheme.colorScheme.primaryContainer.copy(0.5f))
                .padding(16.dp, 0.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HorizontalSpacer(height = 32.sdp)
            /** Profile Header*/
            ProfileCard(
                name = name,
                phone = phone,
                points = points,
                navigateToProfile = {
                    val action = HomeScreenFragmentDirections.actionHomeScreenToProfile()
                    navController.navigate(action)
                },
            )
            HorizontalSpacer()

            /** Leaderboards */
            HomeScreenCard(
                title = stringResource(id = R.string.leaderboard_title),
                onCLick = {
                    val action = HomeScreenFragmentDirections.actionHomeScreenToLeaderboard()
                    navController.navigate(action)
                },
                content = { }
            )
            HorizontalSpacer()

            /** Summary Card */
            TaskSummaryCard(taskSummary) {
                val action = HomeScreenFragmentDirections.actionHomeScreenToDashboard()
                navController.navigate(action)
            }
            HorizontalSpacer()

            /** Performance Card */
            /** No action */
            PerformanceCard(performance) { /** No action */ }
            HorizontalSpacer()

            /** Earnings Card*/
            val context = LocalContext.current
            Earning(earningStatus) {
                viewModel.setEarningSummary()
                val workerBalance = viewModel.earningStatus.value.totalEarned
                // Navigate only if worker total earning is greater than 2 rs.
                if (workerBalance > 2.0f) {
                    viewModel.navigatePayment()
                } else {
                    Toast.makeText(context, "Please earn at least Rs 2", Toast.LENGTH_LONG).show()
                }
            }
            HorizontalSpacer()
        }
    }
}
