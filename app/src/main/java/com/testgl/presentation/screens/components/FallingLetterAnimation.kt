package com.testgl.presentation.screens.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.sp
import com.testgl.presentation.model.SoundType

@Composable
fun FallingLetter(
    symbol: Char = Char(0),
    visibility: Boolean = true,
    rowHeight: Int = 0,
    playSound: (SoundType) -> Unit = {},
    onLetterClickListener: (Char) -> Unit = {}
) {
    if (visibility) {
        playSound(SoundType.Bip)
    } else {
        playSound(SoundType.StrangeBeep)
    }

    val topPadding by animateFloatAsState(
        targetValue = if (visibility) 0.0f else -1.0f,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    Text(
        modifier = Modifier
            .offset { IntOffset(0, (topPadding * rowHeight).toInt()) }
            .clickable { onLetterClickListener(symbol) },
        text = symbol.toString(),
        fontSize = 34.sp
    )
}
