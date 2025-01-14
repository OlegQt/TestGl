package com.testgl.presentation.composable

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

@Composable
fun ScreenOptions(
    modifier: Modifier = Modifier,
    navigateNext: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        OutlinedButton(
            modifier = Modifier.align(alignment = Alignment.Center),
            onClick = navigateNext,
            shape = RoundedCornerShape(20),
            //border = BorderStroke(2.dp, color = Color.Yellow)
        ) {
            Text("Navigate to collection")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ShowPreview() {
    ScreenOptions()
}