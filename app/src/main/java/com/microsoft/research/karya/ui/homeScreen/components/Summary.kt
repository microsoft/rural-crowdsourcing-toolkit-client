package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.KaryaPreviews
import com.microsoft.research.karya.compose.components.v1.TaskSummary
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskStatus

@Composable
fun TaskSummaryCard(
    taskStatus: TaskStatus,
    onCLick: () -> Unit
) {
    HomeScreenCard(
        title = stringResource(id = R.string.task_summary_label),
        onCLick = onCLick
    ) {
        TaskSummary(taskStatus = taskStatus)
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
            TaskSummaryCard(taskStatus = sampleTaskStatus) {
            }
        }
    }
}
