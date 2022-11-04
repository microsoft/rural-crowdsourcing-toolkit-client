package com.microsoft.research.karya.ui.homeScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.KaryaPreviews
import com.microsoft.research.karya.compose.theme.KaryaTheme
import com.microsoft.research.karya.compose.theme.sdp

@Composable
fun ProfileCard(
    name: String,
    phone: String,
    points: Int,
    navigateToProfile: () -> Unit,
) {
    Row(
        modifier = Modifier
            .shadow(2.dp)
            .background(Color.White)
            .clickable {
                navigateToProfile()
            }
            .padding(16.sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = name,
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Normal),
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = phone,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = points.toString(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.width(8.sdp))
            Text(
                text = stringResource(id = R.string.leaderboard_points),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@KaryaPreviews
@Composable
fun ProfileCardPrev() {
    KaryaTheme {
        Surface(Modifier.background(MaterialTheme.colorScheme.primaryContainer)) {
            ProfileCard(
                name = "Divyansh Kushwaha",
                phone = "9999999999",
                points = 30
            ) {}
        }
    }
}
