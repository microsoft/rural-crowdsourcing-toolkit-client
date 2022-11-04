package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.KaryaPreviews
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.compose.theme.sdp
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskStatus

@Composable
fun TaskSummary(
    taskStatus: TaskStatus,
    onCLick: () -> Unit
) {
    HomeScreenCard(
        title = stringResource(id = R.string.task_summary_label),
        onCLick = onCLick
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "${taskStatus.assignedMicrotasks} Available", color = colorScheme.error)
                HorizontalSpacer(8.sdp)
                Text(
                    text = "${taskStatus.completedMicrotasks} Completed",
                    color = colorScheme.primary
                )
                HorizontalSpacer(8.sdp)
                Text(text = "${taskStatus.verifiedMicrotasks} Verified", color = colorScheme.onSurface)
            }
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                Text(text = "${taskStatus.skippedMicrotasks} Skipped", color = colorScheme.error)
                HorizontalSpacer(8.sdp)
                Text(
                    text = "${taskStatus.submittedMicrotasks} Submitted",
                    color = colorScheme.primary
                )
                HorizontalSpacer(8.sdp)
                Text(text = "${taskStatus.expiredMicrotasks} Expired")
            }
        }
    }
}

@KaryaPreviews
@Composable
fun TaskSummaryPrev() {
    KaryaTheme {
        val sampleTaskStatus = TaskStatus(
            assignedMicrotasks = 10,
            completedMicrotasks = 10,
            skippedMicrotasks = 10,
            submittedMicrotasks = 10,
            verifiedMicrotasks = 32,
            expiredMicrotasks = 0
        )
        Surface {
            TaskSummary(taskStatus = sampleTaskStatus) {
            }
        }
    }
}
