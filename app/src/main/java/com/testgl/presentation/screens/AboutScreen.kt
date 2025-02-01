package com.testgl.presentation.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.testgl.presentation.theme.AppTheme
import kotlin.math.abs
import kotlin.math.sin
import kotlin.random.Random


@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush =
                Brush.linearGradient(colors = listOf(Color.Gray, Color.DarkGray))
            )
    ) {
        CardText("Greetings")
    }

}

@Composable
fun CardText(text: String) {

    var animationVisibility by rememberSaveable { mutableStateOf(false) }
    var rowHeight by rememberSaveable { mutableIntStateOf(0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(264.dp)
            .padding(all = 8.dp)
            .clickable { animationVisibility = !animationVisibility },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer.copy(
                alpha = 0.4f
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned {
                    rowHeight = it.size.height
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            text.forEachIndexed { index, it ->
                FallingLetter(
                    symbol = it,
                    visibility = animationVisibility,
                    rowHeight = rowHeight,
                    charIndex = index
                )
            }
        }
    }
}

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

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SomeScreenTestPreview(modifier: Modifier = Modifier) {
    AppTheme {
        AboutScreen()
    }
}

fun Modifier.space(): Modifier = composed {
    val transition = rememberInfiniteTransition(label = "space")
    // Создание списка частиц с использованием mutableStateListOf
    val particlesList = remember { mutableStateListOf<Offset>() }

    LaunchedEffect(Unit) {
        repeat(200) {
            particlesList.add(
                Offset(
                    x = Random.nextFloat(),
                    y = Random.nextFloat()
                )
            ) // исправлен неверный идентификатор "Y"
        }
    }

    val timeElapsed by transition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "repeatable"
    )

    val multiple = abs(sin(x = timeElapsed) * 2000)

    this.drawBehind {
        particlesList.forEach {
            drawCircle(
                color = Color.Black, radius = 4.0f, center = it.times(multiple).plus(size.center)
            )
        }
    }
}
