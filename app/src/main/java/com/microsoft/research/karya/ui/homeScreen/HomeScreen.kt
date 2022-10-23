package com.microsoft.research.karya.ui.homeScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.ui.homeScreen.components.Earning
import com.microsoft.research.karya.ui.homeScreen.components.Performance
import com.microsoft.research.karya.ui.homeScreen.components.ProfileHeader
import com.microsoft.research.karya.ui.homeScreen.components.Summary

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel, navController: NavController) {

    val points by viewModel.points.collectAsState()
    val name by viewModel.name.collectAsState()
    val phone by viewModel.phoneNumber.collectAsState()

    val taskSummary by viewModel.taskSummary.collectAsState()
    val performance by viewModel.performanceSummary.collectAsState()
    val earningStatus by viewModel.earningStatus.collectAsState()

    /* val total = with(taskSummary) {
        assignedMicrotasks + completedMicrotasks + submittedMicrotasks + verifiedMicrotasks + skippedMicrotasks + expiredMicrotasks
    } */

    Column(
        Modifier
            .padding(16.dp, 0.dp)
            .verticalScroll(rememberScrollState())
    ) {

        /** Profile Header*/
        ProfileHeader(
            name = name,
            phone = phone,
            points = points,
            navigateToProfile = {
                val action = HomeScreenFragmentDirections.actionHomeScreenToProfile()
                navController.navigate(action)
            },
            navigateToLeaderboards = {
                val action = HomeScreenFragmentDirections.actionHomeScreenToLeaderboard()
                navController.navigate(action)
            }
        )
        HorizontalSpacer()

        /** Summary Card */
        Summary(taskSummary) {
            val action = HomeScreenFragmentDirections.actionHomeScreenToDashboard()
            navController.navigate(action)
        }
        HorizontalSpacer()

        /** Performance Card */
        Performance(performance) { /** No action */ }
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

@Preview
@Composable
fun DashboardPreview() {
    KaryaTheme {
    }
}
