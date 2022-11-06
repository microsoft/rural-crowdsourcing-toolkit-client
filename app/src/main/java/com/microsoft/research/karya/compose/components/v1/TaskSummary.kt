package com.microsoft.research.karya.compose.components.v1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.compose.theme.sdp
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskStatus

@Composable
fun TaskSummary(taskStatus: TaskStatus) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "${taskStatus.assignedMicrotasks} Available", color = MaterialTheme.colorScheme.error)
            HorizontalSpacer(8.sdp)
            Text(
                text = "${taskStatus.completedMicrotasks} Completed",
                color = MaterialTheme.colorScheme.primary
            )
            HorizontalSpacer(8.sdp)
            Text(text = "${taskStatus.verifiedMicrotasks} Verified", color = MaterialTheme.colorScheme.onSurface)
        }
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "${taskStatus.skippedMicrotasks} Skipped", color = MaterialTheme.colorScheme.error)
            HorizontalSpacer(8.sdp)
            Text(
                text = "${taskStatus.submittedMicrotasks} Submitted",
                color = MaterialTheme.colorScheme.primary
            )
            HorizontalSpacer(8.sdp)
            Text(text = "${taskStatus.expiredMicrotasks} Expired")
        }
    }
}
