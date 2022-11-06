package com.microsoft.research.karya.ui.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.BuildConfig
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.components.v1.KaryaAppBar
import com.microsoft.research.karya.compose.components.v1.KaryaCard
import com.microsoft.research.karya.compose.components.v1.KaryaProgressBar
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskInfo
import com.microsoft.research.karya.ui.dashboard.DashboardViewModel
import com.microsoft.research.karya.ui.dashboard.ERROR_LVL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    onSyncClicked: () -> Unit,
    onBackPressed: () -> Unit,
    languageCode: String,
    onLanguageClicked: () -> Unit,
    onTaskItemClicked: (TaskInfo) -> Unit,

) {

    val uiState by viewModel.dashboardUiState.collectAsState()

    val workFromCenterUser by viewModel.workFromCenterUser.collectAsState()
    val workerAccessCode by viewModel.workerAccessCode.collectAsState()
    val userInCenter by viewModel.userInCenter.collectAsState()

    val taskList by viewModel.taskList.collectAsState()

    val progress by viewModel.progress.collectAsState()
    val isProgressVisible by viewModel.isLoading.collectAsState()

    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            KaryaAppBar(
                title = stringResource(id = R.string.dashboard_title),
                versionCode = BuildConfig.VERSION_CODE.toString(),
                onBackPressed = onBackPressed,
                languageCode = languageCode, // TODO: Update
                onLanguageCodeClicked = onLanguageClicked,
                isNavigationEnable = true
            )
        }
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            HorizontalSpacer()

            /**
             * Work from center user
             */
            AnimatedVisibility(visible = workFromCenterUser) {
                // Show work from center
                if (userInCenter) {
                    // Show enter code
                    var code by remember { mutableStateOf("") }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedTextField(
                            value = code,
                            onValueChange = { code = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text(text = "Center Code") }
                        )
                        Button(onClick = { /** TODO */ }) {
                            Icon(
                                modifier = Modifier.padding(4.dp),
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                            )
                        }
                    }
                } else {
                    // Show revoke authorization
                    FilledTonalButton(onClick = { /** TODO */ }) {
                        Text(text = "Revoke Authorization")
                    }
                }
            }

            /**
             * Worker Access Code
             */
            Text(text = stringResource(R.string.access_code, workerAccessCode))

            /**
             * Sync with server
             */
            HorizontalSpacer()
            KaryaCard(Modifier.clickable { onSyncClicked() }) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.sync_prompt),
                        style = MaterialTheme.typography.titleLarge
                    )

                    AnimatedVisibility(
                        visible = isProgressVisible
                    ) {
                        KaryaProgressBar(
                            (progress / 100).toFloat(),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }

                    AnimatedVisibility(visible = error != null) {
                        // will be visible only if error is not null
                        error?.let {
                            Text(
                                modifier = Modifier.padding(top = 16.dp),
                                text = it.errorMessage,
                                style = MaterialTheme.typography.titleMedium,
                                color = if (it.errorLevel == ERROR_LVL.WARNING) Color.Yellow else MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }

            /**
             * Task List
             */
            HorizontalSpacer()
            LazyColumn {
                items(taskList) { taskInfo ->
                    TaskItemCard(taskInfo = taskInfo) {
                        onTaskItemClicked(taskInfo)
                    }
                    HorizontalSpacer()
                }
            }
        }
    }
}
