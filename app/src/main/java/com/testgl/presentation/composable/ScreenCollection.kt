package com.testgl.presentation.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.testgl.presentation.theme.AppTheme

@Composable
fun ScreenCollection(modifier: Modifier = Modifier, navigateNext: () -> Unit = {}) {
    AnimatedVisibility(
        visible = true,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutHorizontally(targetOffsetX = { -it })
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            OutlinedButton(
                modifier = Modifier.align(alignment = Alignment.Center),
                onClick = navigateNext,
                shape = RoundedCornerShape(20),
                //border = BorderStroke(2.dp, color = Color.Yellow)
            ) {
                Text("Navigate to options")
            }
        }
    }

}

@Preview
@Composable
fun ShowScreenPreview() {
    AppTheme {
        ScreenCollection { }
    }
}