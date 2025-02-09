package com.testgl.presentation.screens

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.testgl.presentation.theme.AppTheme
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


@Composable
fun AboutScreen(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        val pagerState = rememberPagerState(pageCount = { 10 })
        HorizontalPager(pagerState) { page: Int ->

            val borderAnimator = rememberInfiniteTransition(label = "border")

            val angle by borderAnimator.animateFloat(
                targetValue = 6.28f,
                initialValue = 0.0f,
                animationSpec = infiniteRepeatable(
                    tween(durationMillis = 2000, easing = LinearEasing),
                    RepeatMode.Restart
                ),
                label = ""
            )


            var cardHalfSize by remember { mutableStateOf(Offset(0.0f, 0.0f)) }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        val hw = layoutCoordinates.size.width.toFloat() / 2
                        val hh = layoutCoordinates.size.height.toFloat() / 2
                        cardHalfSize = Offset(x = hw, y = hh)
                    },

                border = BorderStroke(
                    width = 2.dp,
                    brush = Brush.sweepGradient(
                        colors = listOf(Color.Yellow, Color.Magenta, Color.Yellow),
                        center = Offset(  // Конечная точка градиента после поворота
                            x = cardHalfSize.x + cos(angle) * cardHalfSize.x,  // Используем угол для расчета
                            y = cardHalfSize.y + sin(angle) * cardHalfSize.x   // Применяем синус для координаты Y
                        )
                    )
                )
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
                    text = "Text $page"
                )
            }
        }
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
                color = Color.Black,
                radius = 4.0f,
                center = it.times(multiple).plus(size.center)
            )
        }
    }
}
