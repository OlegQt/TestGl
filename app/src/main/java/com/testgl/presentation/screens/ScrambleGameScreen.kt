package com.testgl.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testgl.presentation.theme.AppTheme
import com.testgl.presentation.viewmodels.ScrambleGameViewModel

@Composable
fun ScrambleGameScreen(
    modifier: Modifier = Modifier,
    viewModel: ScrambleGameViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    Space(modifier = modifier.fillMaxSize())

    GameCard(
        modifier = Modifier.fillMaxWidth(),
        sourceWord = uiState.value.currentScrambleWord
    )
}


@Composable
fun GameCard(modifier: Modifier = Modifier, sourceWord: String) {
    var inputString by rememberSaveable { mutableStateOf("") }
    var processedWord by remember { mutableStateOf(sourceWord) }
    var fallingHeight by remember { mutableIntStateOf(0) }

    Card(
        modifier = modifier
            .padding(top = 128.dp, start = 16.dp, end = 16.dp)
            .onGloballyPositioned { coordinates ->
                fallingHeight = coordinates.size.height
            }
            .height(256.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(
                alpha = 0.05f
            )
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(modifier = Modifier.padding(bottom = 24.dp)) {
                sourceWord.forEachIndexed { charIndex, charSymbol ->
                    //Анализируем видимость отдельных букв
                    val isVisible by mutableStateOf(sourceWord[charIndex] == processedWord[charIndex])

                    FallingLetter(
                        symbol = charSymbol,
                        charIndex = charIndex,
                        visibility = isVisible,
                        rowHeight = fallingHeight
                    )
                }
            }

            UserInput(
                textLine = inputString,
                userInputFun = { txt ->
                    val filterResult =
                        filterInputText(inputTxtVal = txt, sourceFilterTxt = sourceWord)
                    processedWord = filterResult.second
                    inputString = filterResult.first
                }
            )
        }
    }
}

@Composable
fun UserInput(textLine: String, userInputFun: (String) -> Unit = {}) {
    OutlinedTextField(
        value = textLine,
        onValueChange = { newInput -> userInputFun(newInput) },
        shape = RoundedCornerShape(percent = 50),
        textStyle = TextStyle.Default.copy(fontSize = 28.sp)
    )
}


fun filterInputText(inputTxtVal: String, sourceFilterTxt: String): Pair<String, String> {
    var sourceTxt = sourceFilterTxt
    val finalStr: StringBuilder = StringBuilder()

    inputTxtVal.toCharArray().forEach {
        if (sourceTxt.contains(it)) {
            finalStr.append(it)
            sourceTxt = sourceTxt.replaceFirst(oldChar = it, newChar = Char(126), ignoreCase = true)
        }
    }

    //Log.d("LOG"," source = $sourceTxt \n input = $inputTxtVal \n final = $finalStr")

    return Pair(first = String(finalStr), second = sourceTxt)
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewScrambleGameUi() {
    AppTheme {
        ScrambleGameScreen()
    }
}