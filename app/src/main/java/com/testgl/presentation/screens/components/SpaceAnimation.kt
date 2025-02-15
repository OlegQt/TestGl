package com.testgl.presentation.screens.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testgl.presentation.theme.AppTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Space(
    modifier: Modifier = Modifier,
    takeFpsInfo: (String) -> Unit = {},
    particleQuantity: Int = 100
) {

    data class WhiteParticle(
        val position: Offset = Offset(0.5f, 0.5f),
        val direction: Offset = Offset(0.0f, 0.0f)
    )

    fun Offset.toActualSize(width: Float, height: Float): Offset {
        return Offset(
            x = this.x * width,
            y = this.y * height
        )
    }

    var particlesCounter by remember { mutableIntStateOf(0) }
    val particleList = remember { mutableStateListOf<WhiteParticle>() }
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    val darkBlueGradient = Brush.horizontalGradient(
        colors = listOf(
            Color(0xFF0D0C1D),  // Очень темный синий (начало градиента)
            Color(0xFF1D1C2B)   // Темный фиолетово-синий (конец градиента)
        ),
        startX = 0f,  // Начало градиента слева
        endX = Float.POSITIVE_INFINITY  // Конец градиента справа
    )

    LaunchedEffect(particleQuantity) {
        // Инициализация частиц при смене числа
        repeat(particleQuantity - particleList.size) {
            val speedVector = Offset(
                x = (Random.nextFloat() - 0.5f) / 100f,
                y = (Random.nextFloat() - 0.5f) / 100f
            )
            particleList.add(WhiteParticle(direction = speedVector))
        }
    }

    LaunchedEffect(particleList) {
        while (true) {
            if (particleList.size > 0)
                particleList.forEachIndexed { index, whiteParticle ->
                    particleList[index] = whiteParticle.copy(
                        position = whiteParticle.position.plus(whiteParticle.direction)
                    )
                    if (particleList[index].position.minus(Offset(0.5f, 0.5f))
                            .getDistance() > 0.8f
                    ) {
                        particleList[index] = whiteParticle.copy(position = Offset(0.5f, 0.5f))
                    }
                }
            delay(16)
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .background(darkBlueGradient)
        .blur(radius = 6.dp)
        .clickable { }
        .drawBehind {
            particleList.forEach {
                drawCircle(
                    color = onSurfaceColor,
                    radius = (Offset(0.5f, 0.5f).minus(it.position)).getDistance() * 100,
                    center = it.position.toActualSize(size.width, size.height)
                )
            }
            particlesCounter = particleList.size
        },
        content = {
        }
    )
}


@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SpacePreview() {
    AppTheme {
        Space(modifier = Modifier.fillMaxSize())
    }
}
