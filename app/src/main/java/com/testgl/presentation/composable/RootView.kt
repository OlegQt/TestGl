package com.testgl.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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

@Composable
fun RootView() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(all = 16.dp), verticalArrangement = Arrangement.Bottom) {
        FloatingActionButton({}) {
            AnimationWaiting()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ScreenPreview() {
    RootView()
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
