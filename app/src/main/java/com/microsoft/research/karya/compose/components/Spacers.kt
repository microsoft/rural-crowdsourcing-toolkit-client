package com.microsoft.research.karya.compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.microsoft.research.karya.compose.theme.sdp

/**
 * Vertical spacer with default width = 16.dp
 */
@Composable
fun VerticalSpacer(width: Dp = 16.sdp) = Spacer(modifier = Modifier.width(width))

/**
 * Horizontal spacer with default height = 16.dp
 */
@Composable
fun HorizontalSpacer(height: Dp = 16.sdp) = Spacer(modifier = Modifier.height(height))
