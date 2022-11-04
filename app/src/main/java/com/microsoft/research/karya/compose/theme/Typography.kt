package com.microsoft.research.karya.compose.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

/**
 * It is okay for typography to define as a composable, we want typography to re-calculate when recomposition happens
 */
val karyaTypography @Composable get() = Typography(
    /**
     * Body typography
     */
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.ssp,
        lineHeight = 24.ssp,
        letterSpacing = 0.5.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.ssp,
        lineHeight = 20.ssp,
        letterSpacing = 0.5.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.ssp,
        lineHeight = 16.ssp,
        letterSpacing = 0.5.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    /**
     * Label typography
     */
    labelLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.ssp,
        lineHeight = 16.ssp,
        letterSpacing = 0.5.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 12.ssp,
        lineHeight = 16.ssp,
        letterSpacing = 0.5.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.ssp,
        lineHeight = 16.ssp,
        letterSpacing = 0.5.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    /**
     * Title typography
     */
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.ssp,
        lineHeight = 28.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.ssp,
        lineHeight = 24.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.ssp,
        lineHeight = 20.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    /**
     * Headline typography
     */
    headlineLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 32.ssp,
        lineHeight = 40.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 28.ssp,
        lineHeight = 36.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.ssp,
        lineHeight = 32.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    /**
     * Display typography
     */
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 57.ssp,
        lineHeight = 64.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 45.ssp,
        lineHeight = 52.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 36.ssp,
        lineHeight = 44.ssp,
        letterSpacing = 0.ssp,
        color = MaterialTheme.colorScheme.outline
    ),
)
