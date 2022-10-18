package com.microsoft.research.karya.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.theme.KaryaTheme

@OptIn(ExperimentalMaterial3Api::class) // it is in release candidate, so will be stable very soon
@Composable
fun KaryaAppBar(
    title: String,
    versionCode: String,
    onBackPressed: () -> Unit,
    languageCode: String, // TODO: use an enum or sealed class for language codes
    onLanguageCodeClicked: (String) -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        actions = {
            IconButton(onClick = { }) { Text(text = versionCode) }
            IconButton(onClick = { onLanguageCodeClicked(languageCode) }) { Text(text = languageCode) }
            Image(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.karya_logo),
                contentDescription = stringResource(R.string.karya_logo)
            )
        }
    )
}

@Preview
@Composable
fun KaryaAppBarPreview() {
    KaryaTheme {
        KaryaAppBar(
            title = "Task List",
            versionCode = "23",
            onBackPressed = { },
            languageCode = "EN",
            onLanguageCodeClicked = { }
        )
    }
}
