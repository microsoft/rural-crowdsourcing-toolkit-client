package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.components.HorizontalSpacer
import com.microsoft.research.karya.data.model.karya.modelsExtra.TaskStatus

@Composable
fun Summary(taskStatus: TaskStatus, onCLick: () -> Unit) {
    HomeScreenDetailCard(title = "Task Summary", onCLick) {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                Text(text = "${taskStatus.assignedMicrotasks} Available", color = colorScheme.error)
                HorizontalSpacer(8.dp)
                Text(text = "${taskStatus.completedMicrotasks} Completed", color = colorScheme.primary)
                HorizontalSpacer(8.dp)
                Text(text = "${taskStatus.verifiedMicrotasks} Verified")
            }
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
                Text(text = "${taskStatus.skippedMicrotasks} Skipped", color = colorScheme.error)
                HorizontalSpacer(8.dp)
                Text(text = "${taskStatus.submittedMicrotasks} Submitted", color = colorScheme.primary)
                HorizontalSpacer(8.dp)
                Text(text = "${taskStatus.expiredMicrotasks} Expired")
            }
            Column(Modifier.weight(1f)) {
                Image(
                    modifier = Modifier.size(96.dp),
                    painter = painterResource(id = R.drawable.karya_logo),
                    contentDescription = null
                )
            }
        }
    }
}
