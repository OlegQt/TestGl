package com.testgl.presentation.screens.abouttestscreen

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testgl.presentation.theme.AppTheme
import kotlin.math.abs
import kotlin.math.sin
import kotlin.random.Random

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SomeScreenTestPreview(modifier: Modifier = Modifier) {
    AppTheme {
        AboutScreen()
    }
}

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    viewModel: AboutViewModel = viewModel()
) {

    Box(modifier = modifier.fillMaxSize()) {
        ScreenContent(
            uiState = viewModel.uiState.collectAsState().value,
            onEvent = viewModel::onEvent
        )
    }


}

@Composable
fun ScreenContent(
    uiState: AboutScreenUiState = AboutScreenUiState(),
    onEvent: (AboutEventType) -> Unit = {}
) {
    TextField(
        value = uiState.txtInput,
        onValueChange = { onEvent(AboutEventType.InputTxt(it)) },
        label = { Text(uiState.txtInput.length.toString()) }
    )
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
