package com.testgl.presentation.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    Space(modifier = modifier)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent),
        contentAlignment = Alignment.Center

    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val sourceWord by rememberSaveable { mutableStateOf(uiState.value.currentScrambleWord) }

            // На основе сравнения с этой строкой будут загружаться анимации видимости букв
            var processedWord by remember { mutableStateOf(sourceWord) }

            var inputString by rememberSaveable { mutableStateOf("") }

            Row {
                sourceWord.toCharArray().forEachIndexed { index, symbol ->

                    //Анализируем видимость отдельных букв
                    var isVisible by mutableStateOf(sourceWord[index] == processedWord[index])

                    AnimatedVisibility(isVisible) {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 1.dp)
                                .clickable { isVisible = false },
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(
                                    alpha = 0.2f
                                )
                            )
                        )

                        {
                            Text(
                                modifier = Modifier.padding(all = 2.dp),
                                text = symbol.toString(),
                                fontSize = 36.sp,
                            )
                        }
                    }
                }
            }

            Box(modifier = Modifier.padding(top = 16.dp)) {
                OutlinedTextField(
                    value = inputString,
                    onValueChange = { newInput ->

                        val filterResult = filterInputText(
                            inputTxtVal = newInput,
                            sourceFilterTxt = sourceWord
                        )

                        inputString = filterResult.first
                        processedWord = filterResult.second
                    },
                    shape = RoundedCornerShape(percent = 50),
                    textStyle = TextStyle.Default.copy(fontSize = 28.sp)
                    //colors = OutlinedTextFieldDefaults.colors(                        unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary

                )
            }
        }
    }
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