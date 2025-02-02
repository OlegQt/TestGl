package com.testgl.presentation.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import com.testgl.presentation.model.SoundType

@Composable
fun FallingLetter(
    symbol: Char = Char(0),
    visibility: Boolean = true,
    rowHeight: Int = 0,
    charIndex: Int = 0,
    playSound: (SoundType) -> Unit = {}
) {
    val duration = charIndex * 80

    if (visibility) {
        playSound(SoundType.Bip)
    } else {
        playSound(SoundType.StrangeBeep)
    }

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