package com.testgl.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.testgl.presentation.theme.AppTheme

@Composable
fun RootView() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        SelectOptionScreen(
            task = "Task",
            onSelectionChanged = {},
            onCancelButtonClicked = {},
            onNextButtonClicked = {},
            answerList = listOf("A option", "B option", "C option")
        )
        //FloatingActionButton({}) { AnimationGear() }
    }
}

@Preview(showSystemUi = true, showBackground = false)
@Composable
fun ScreenPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        RootView()
    }
}

@Composable
fun AnimationWaiting(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("dearSearchAnimation.json")
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun AnimationGear(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("gear_animation.json")
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}
