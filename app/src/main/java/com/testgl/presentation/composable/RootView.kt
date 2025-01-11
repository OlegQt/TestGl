package com.testgl.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
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
        ShowList()

        FloatingActionButton({}) { AnimationWaiting() }

        Spacer(Modifier.size(20.dp))

        FloatingActionButton({}) { AnimationGear() }
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
fun AnimationWaiting() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("dearSearchAnimation.json")
    )

    LottieAnimation(
        modifier = Modifier.size(48.dp, 48.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun AnimationGear() {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("gear_animation.json")
    )

    LottieAnimation(
        modifier = Modifier.size(48.dp, 48.dp),
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}
