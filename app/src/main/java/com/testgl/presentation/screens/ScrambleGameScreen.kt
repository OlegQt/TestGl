package com.testgl.presentation.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testgl.presentation.viewmodels.ScrambleGameViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun ScrambleGameScreen(
    modifier: Modifier = Modifier,
    viewModel: ScrambleGameViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center

    ) {
        Column {
            Text(
                //modifier = Modifier.align(Alignment.Center),
                text = uiState.value.currentScrambleWord,
                textAlign = TextAlign.Center,
                fontSize = 32.sp
            )

            IconButton(
                onClick = { viewModel.pickNewWord() },
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Black)

            ) {
                Icon(imageVector = Icons.Filled.AddCircle,contentDescription = null)
            }

            OutlinedTextField(
                value = "Text",
                shape = RoundedCornerShape(percent = 10),
                colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = Color.LightGray),
                onValueChange = {}
            )
            Row(modifier = Modifier.padding(top = 24.dp)) {


                val word = uiState.value.currentScrambleWord
                word.toCharArray().forEach {
                    val visibility by rememberSaveable { mutableStateOf(true) }

                    Text(it.toString(), fontSize = 32.sp)
                }
            }
        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewScrambleGameUi(){
    ScrambleGameScreen()
}