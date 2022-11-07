package com.microsoft.research.karya.ui.dashboard.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.components.v1.KaryaCard
import com.microsoft.research.karya.compose.components.v1.KaryaProgressBar
import com.microsoft.research.karya.compose.components.v1.KaryaRatingBar
import com.microsoft.research.karya.compose.components.v1.TaskSummary
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskInfo
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskStatus

@Composable
fun TaskItemCard(taskInfo: TaskInfo, onClick: () -> Unit) {
    val status = taskInfo.taskStatus
    val verified = status.verifiedMicrotasks
    val submitted = status.submittedMicrotasks + verified
    val completed = status.completedMicrotasks + submitted
    val assigned = status.assignedMicrotasks
    val skipped = status.skippedMicrotasks
    val expired = status.expiredMicrotasks
    val progress = (completed.toFloat() / (completed + assigned).toFloat())
    val title = taskInfo.taskName
    val taskStatus = TaskStatus(assigned, completed, submitted, verified, skipped, expired)
    val report = taskInfo.reportSummary // speech data report, null for others

    KaryaCard {
        Column(
            modifier = Modifier
                .clickable(enabled = (assigned + skipped > 0), onClick = onClick)
                .padding(16.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
            HorizontalSpacer()
            KaryaProgressBar(progress = progress)
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            TaskSummary(taskStatus = taskStatus)
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            AnimatedVisibility(visible = report != null) {
                if (report != null) {
                    Column {
                        if (report.has("accuracy")) {
                            KaryaRatingBar(
                                title = stringResource(id = R.string.recording_accuracy),
                                rate = report.get("accuracy").asFloat,
                            )
                        }
                        if (report.has("volume")) {
                            KaryaRatingBar(
                                title = stringResource(id = R.string.recording_volume),
                                rate = report.get("volume").asFloat,
                            )
                        }
                        if (report.has("quality")) {
                            KaryaRatingBar(
                                title = stringResource(id = R.string.recording_quality),
                                rate = report.get("quality").asFloat
                            )
                        }
                    }
                }
            }
        }
    }
}
