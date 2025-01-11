package com.testgl.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RootView() {
    Column {
        Text("Hello")
    }
}

@Preview(showSystemUi = true)
@Composable
fun ScreenPreview() {
    RootView()
}

