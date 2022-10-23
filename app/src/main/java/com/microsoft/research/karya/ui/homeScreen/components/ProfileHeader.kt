package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.components.HorizontalSpacer

@Composable
fun ProfileHeader(
    name: String,
    phone: String,
    points: Int,
    navigateToProfile: () -> Unit,
    navigateToLeaderboards: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        HorizontalSpacer(48.dp)
        Image(
            painter = painterResource(id = R.drawable.karya_logo),
            contentDescription = "profile picture",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        HorizontalSpacer()
        Text(text = name, style = MaterialTheme.typography.headlineMedium)
        Text(text = phone, style = MaterialTheme.typography.bodyLarge)
        Text(text = "$points points", style = MaterialTheme.typography.bodyLarge)
        HorizontalSpacer()
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = navigateToProfile) {
                Text(text = "View Profile")
            }
            OutlinedButton(onClick = navigateToLeaderboards) {
                Text(text = "Go to Leaderboard")
            }
        }
    }
}
