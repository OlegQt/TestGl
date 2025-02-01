package com.testgl.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun FallingLetter(
    symbol: Char = Char(0),
    visibility: Boolean = true,
    rowHeight: Int = 0,
    charIndex: Int = 0
) {
    val duration = charIndex * 80

    AnimatedVisibility(
        visible = visibility,
        enter = slideInVertically(
            initialOffsetY = { -rowHeight },
            animationSpec = tween(durationMillis = 500 + duration)
        ),
        exit = slideOutVertically(
            targetOffsetY = { -rowHeight },
            animationSpec = tween(durationMillis = 1500 - duration)
        )

    ) {
        Text(
            text = symbol.toString(),
            fontSize = 34.sp
        )
    }
}