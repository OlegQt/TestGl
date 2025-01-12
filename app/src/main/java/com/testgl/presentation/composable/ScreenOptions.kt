package com.testgl.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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
        TextButton(
            modifier = Modifier.align(alignment = Alignment.Center),
            onClick = navigateNext,
            shape = RoundedCornerShape(40),
            border = BorderStroke(2.dp, color = Color.Yellow)
        ) {
            Text("NavigateNext")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ShowPreview() {
    ScreenOptions()
}