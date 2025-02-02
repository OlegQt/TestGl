package com.testgl.presentation.screens.scramble

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testgl.presentation.model.SoundType
import com.testgl.presentation.screens.components.FallingLetter
import com.testgl.presentation.screens.components.Space
import com.testgl.presentation.theme.AppTheme

@Composable
fun ScrambleGameScreen(
    modifier: Modifier = Modifier,
    viewModel: ScrambleGameViewModel = viewModel(),
    playSoundFun: (SoundType) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState()
    val onEvent: (EventType) -> Unit = { viewModel.onEvent(it) }

    Space(modifier = modifier.fillMaxSize())

    Box(modifier = modifier.clickable {
        playSoundFun(SoundType.WrongBeep)
    }, content = {
        GameCard(
            sourceWord = uiState.value.currentScrambleWord,
            score = uiState.value.gameScore,
            hintWord = uiState.value.hint,
            playSound = playSoundFun,
            event = onEvent
        )
    })
}


@Composable
fun GameCard(
    sourceWord: String,
    score: Int = 0,
    hintWord: String = "",
    playSound: (SoundType) -> Unit = {},
    event: (EventType) -> Unit = {}
) {
    var inputString by rememberSaveable { mutableStateOf("") }
    val scrambleWordYOffset by remember { mutableIntStateOf(256) }

    FallingTextString(
        modifier = Modifier
            .offset { IntOffset(0, scrambleWordYOffset) }
            .fillMaxWidth()
            .padding(top = 16.dp),
        textLine = sourceWord,
        checkLettersVisibility(inputString, sourceWord),
        playSound = playSound
    )

    ScoreCard(score = score)

    Card(
        modifier = Modifier
            .height(256.dp)
            .offset { IntOffset(0, scrambleWordYOffset) }
            .padding(top = 0.dp, start = 16.dp, end = 16.dp)
            .clickable { playSound(SoundType.Bip) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.05f)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            UserInput(
                textLine = inputString,
                userInputFun = { txt ->
                    inputString = filterInputText(inputTxtVal = txt, sourceFilterTxt = sourceWord)
                    event(EventType.CheckAnswer(inputString))
                }
            )
            OperationButtons(hintWord = hintWord, event = event)
        }
    }
}

@Composable
fun ScoreCard(score: Int = 0) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
        Card(
            modifier = Modifier.padding(all = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(
                    alpha = 0.6f
                )
            )
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
                text = "Score $score"
            )
        }
    }
}

@Composable
fun OperationButtons(
    hintWord: String = "",
    playSound: (SoundType) -> Unit = {},
    event: (EventType) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { event(EventType.ShuffleWordAgain) }) {
            Icon(
                modifier = Modifier
                    .rotation()
                    .size(size = 48.dp),
                imageVector = Icons.Default.Refresh,
                contentDescription = null
            )
        }
        IconButton(onClick = { event(EventType.ShowHint(level = 1)) }) {
            Icon(
                modifier = Modifier
                    .rotation()
                    .size(size = 48.dp),
                imageVector = Icons.Outlined.Warning,
                contentDescription = null
            )
        }

        val hintTxt = if (hintWord.isNotEmpty()) {
            hintWord.replaceRange(
                1,
                hintWord.length - 1,
                StringBuilder().apply {
                    repeat(hintWord.length - 2) {
                        append("*")
                    }
                }
            )
        } else {
            hintWord
        }

        Text(
            modifier = Modifier.weight(0.5f),
            text = hintTxt,
            fontSize = 34.sp,
            textAlign = TextAlign.End
        )
    }
}

fun Modifier.rotation() = composed {
    val anim = rememberInfiniteTransition(label = "infiniteRotation")

    val angle by anim.animateFloat(
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        initialValue = 0.0f,
        targetValue = 1.0f,
        label = "angle"
    )
    Modifier.rotate(degrees = angle * 45)
}

@Composable
fun FallingTextString(
    modifier: Modifier = Modifier,
    textLine: String,
    visibilityList: List<Boolean>,
    playSound: (SoundType) -> Unit = {}
) {
    var animationHeight by remember { mutableFloatStateOf(0f) }

    Row(
        modifier = modifier.onGloballyPositioned { layoutCoordinates ->
            animationHeight = layoutCoordinates.positionInRoot().y
        },
        horizontalArrangement = Arrangement.Center
    ) {
        textLine.forEachIndexed { charIndex, charSymbol ->
            val isVisible by mutableStateOf(visibilityList[charIndex])

            FallingLetter(
                symbol = charSymbol,
                charIndex = charIndex,
                visibility = isVisible,
                rowHeight = animationHeight.toInt(),
                playSound = playSound
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
        textStyle = TextStyle.Default.copy(fontSize = 28.sp),
    )
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewScrambleGameUi() {
    AppTheme {
        ScrambleGameScreen()
    }
}

/**
 * This function filters characters from the input string `inputTxtVal` based on their presence
 * in the `sourceFilterTxt` string. The comparison is case-insensitive.
 *
 * @param inputTxtVal The string from which characters will be filtered.
 * @param sourceFilterTxt The string containing characters that can be used to filter `inputTxtVal`.
 * Characters in `sourceFilterTxt` will be consumed as they are matched in `inputTxtVal`.
 * @return A new string containing characters from `inputTxtVal` that are found in `sourceFilterTxt`,
 *         in the order they appear in `inputTxtVal`, with each character removed from `sourceFilterTxt`
 *         once used.
 */
fun filterInputText(inputTxtVal: String, sourceFilterTxt: String): String {
    val sourceTxt = sourceFilterTxt.lowercase().toMutableList()
    val finalStr: StringBuilder = StringBuilder()

    inputTxtVal.lowercase().toCharArray().forEach {
        if (sourceTxt.contains(it)) {
            finalStr.append(it)
            sourceTxt.remove(it)
        }
    }

    return finalStr.toString()
}

/**
 * Эта функция сравнивает исходное слово `sourceWord` с вводом пользователя `inputTxt`
 * Для всех букв в исходном слове, сопоставляется лист Boolean со значением true,
 * если буква введена пользователем и false, если такой буквы нет в поле ввода.
 * На основе данного листа можно установить видимость каждой буквы в исходном слове
 *
 * @param inputTxt Строка, в которой мы проверяем наличие символов из `sourceWord`.
 * @param sourceWord Строка, символы которой проверяются на наличие в `inputTxt`.
 * @return Список логических значений, длина которого совпадает с длиной `sourceWord`.
 *         Каждый элемент списка будет `true`, если символ не найден в `inputTxt`,
 *         и `false`, если найден.
 */
fun checkLettersVisibility(inputTxt: String, sourceWord: String): List<Boolean> {
    val lst = MutableList(sourceWord.length) { true }
    val sourceLetterList = inputTxt.lowercase().toMutableList()

    sourceWord.lowercase().forEachIndexed { charIndex, charSymbol ->
        val visibility = !sourceLetterList.contains(charSymbol)
        lst[charIndex] = visibility
        sourceLetterList.remove(charSymbol)
    }

    return lst
}

