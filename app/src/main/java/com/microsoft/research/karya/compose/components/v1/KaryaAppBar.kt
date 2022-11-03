package com.microsoft.research.karya.compose.components.v1

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.microsoft.research.karya.R
import com.microsoft.research.karya.compose.components.VerticalSpacer
import com.microsoft.research.karya.compose.theme.KaryaTheme

@OptIn(ExperimentalMaterial3Api::class) // it is in release candidate, so will be stable very soon
@Composable
fun KaryaAppBar(
    title: String,
    versionCode: String,
    onBackPressed: () -> Unit,
    languageCode: String, // TODO: use an enum or sealed class for language codes
    onLanguageCodeClicked: (String) -> Unit,
    isNavigationEnable: Boolean = false,
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(id = R.drawable.karya_logo),
                    contentDescription = stringResource(R.string.karya_logo)
                )
                VerticalSpacer()
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        navigationIcon = {
            AnimatedVisibility(visible = isNavigationEnable) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { onLanguageCodeClicked(languageCode) }) {
                Text(text = languageCode, color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
            }
            IconButton(onClick = { }) { Text(text = versionCode) }
        }
    )
}

@Preview
@Composable
fun KaryaAppBarPreview() {
    KaryaTheme {
        Surface {
            KaryaAppBar(
                title = "Task List",
                versionCode = "23",
                onBackPressed = { },
                languageCode = "EN",
                onLanguageCodeClicked = { }
            )
        }
    }
}

@Preview
@Composable
fun KaryaAppBarPreview1() {
    KaryaTheme {
        Surface {
            KaryaAppBar(
                title = "Task List",
                versionCode = "23",
                onBackPressed = { },
                languageCode = "EN",
                onLanguageCodeClicked = { },
                isNavigationEnable = true
            )
        }
    }
}
