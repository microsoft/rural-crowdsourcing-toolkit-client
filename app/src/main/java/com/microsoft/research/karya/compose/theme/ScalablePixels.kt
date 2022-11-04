// Copyright (c) Microsoft Corporation.
// Licensed under the MIT license.

package com.microsoft.research.karya.compose.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.*

private val baseWidth = 360.dp

/**
 * Non Composable utilities
 */
@Suppress("unused")
fun Dp.toSdp(screenWidth: Dp, baseScreenWidth: Dp) =
    ((screenWidth.value / baseScreenWidth.value) * this.value).toInt().dp

fun TextUnit.toSsp(screenWidth: Dp, baseScreenWidth: Dp) =
    ((screenWidth.value / baseScreenWidth.value) * this.value).toInt().sp

/**
 * Composable utilities
 */
@Composable
private fun TextUnit.toSsp(baseScreenWidth: Dp = baseWidth): TextUnit {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return this.toSsp(screenWidth, baseScreenWidth)
}

val Int.ssp: TextUnit
    @Composable
    get() = this.sp.toSsp()

val Double.ssp: TextUnit
    @Composable
    get() = this.sp.toSsp()

/**
 * Composable utilities
 */
@Composable
private fun Dp.toSdp(baseScreenWidth: Dp = baseWidth): Dp {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    return this.toSdp(screenWidth, baseScreenWidth)
}

val Int.sdp: Dp
    @Composable
    get() = this.dp.toSdp()

val Double.sdp: Dp
    @Composable
    get() = this.dp.toSdp()
