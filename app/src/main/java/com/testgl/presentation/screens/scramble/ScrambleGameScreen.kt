package com.testgl.presentation.screens.scramble

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testgl.presentation.model.SoundType
import com.testgl.presentation.screens.components.FallingLetter
import com.testgl.presentation.screens.components.Space
import com.testgl.presentation.theme.AppTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ScrambleGameScreen(
    modifier: Modifier = Modifier,
    viewModel: ScrambleGameViewModel = viewModel(),
    playSoundFun: (SoundType) -> Unit = {}
) {
    val uiState = viewModel.uiState.collectAsState()
    val onEvent: (EventType) -> Unit = { viewModel.onEvent(it) }

    Space(modifier = modifier.fillMaxSize())

    Box(
        modifier = modifier.clickable { playSoundFun(SoundType.WrongBeep) },
        content = {
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

    ScoreCard(score = score)

    Card(
        modifier = Modifier
            //.height(256.dp)
            .offset { IntOffset(0, scrambleWordYOffset) }
            .padding(top = 0.dp, start = 16.dp, end = 16.dp),
        //.clickable { event(EventType.ScoreInc) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
        )
    ) {
        Column(
            //modifier = Modifier.fillMaxSize(),
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

            ShowHintWord(hintString = hintWord)

            OperationButtons(
                hintWord = hintWord,
                clearInputTxt = { inputString = "" },
                event = event
            )
        }
    }

    FallingTextString(
        modifier = Modifier
            .offset { IntOffset(0, scrambleWordYOffset) }
            .fillMaxWidth()
            .padding(top = 16.dp),
        textLine = sourceWord,
        checkLettersVisibility(inputString, sourceWord),
        playSound = playSound,
        onLetterPush = {
            inputString = inputString.plus(it)
            event(EventType.CheckAnswer(inputString))
        }
    )
}

@Composable
fun ScoreCard(score: Int = 0) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
        val borderAnimator = rememberInfiniteTransition(label = "border")
        var cardHalfSize by remember { mutableStateOf(Offset(0.0f, 0.0f)) }
        var borderAnimationFlag by remember { mutableStateOf(false) }

        LaunchedEffect(score) {
            // Запуск анимации и отключение через 2 секунды
            borderAnimationFlag = true
            delay(2000)  // Задержка в 2 секунды
            borderAnimationFlag = false  // Сброс состояния для следующего клика
        }

        val angle by borderAnimator.animateFloat(
            targetValue = 6.28f,
            initialValue = 0.0f,
            animationSpec = infiniteRepeatable(
                tween(durationMillis = 2000, easing = LinearEasing),
                RepeatMode.Restart
            ),
            label = ""
        )

        val borderWith by animateDpAsState(
            targetValue = if (borderAnimationFlag) 2.dp else 0.dp, label = "borderWidthAnimation"
        )

        Card(
            modifier = Modifier
                .padding(all = 8.dp)
                .onGloballyPositioned { layoutCoordinates ->
                    val hw = layoutCoordinates.size.width.toFloat() / 2
                    val hh = layoutCoordinates.size.height.toFloat() / 2
                    cardHalfSize = Offset(x = hw, y = hh)
                },
            onClick = { borderAnimationFlag = true },
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(
                    alpha = 0.8f
                )
            ),
            border = BorderStroke(
                width = borderWith,
                brush = Brush.sweepGradient(
                    colors = if (borderAnimationFlag) listOf(
                        Color.Yellow,
                        Color.Magenta,
                        Color.Yellow
                    )
                    else listOf(Color.Transparent, Color.Transparent),
                    center = Offset(  // Конечная точка градиента после поворота
                        x = cardHalfSize.x + cos(angle) * cardHalfSize.x,  // Используем угол для расчета
                        y = cardHalfSize.y + sin(angle) * cardHalfSize.x   // Применяем синус для координаты Y
                    )
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
    clearInputTxt: () -> Unit = {},
    event: (EventType) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.Top,
    ) {
        OperationButton(
            label = "Shuffle",
            clickEvent = { event(EventType.ShuffleWordAgain) },
            icon = Icons.Default.Refresh
        )

        OperationButton(
            label = "Show hint",
            clickEvent = { event(EventType.ShowHint(level = 1)) },
            icon = Icons.Default.Info
        )

        OperationButton(
            label = "Pick new word",
            clickEvent = { event(EventType.PickNewWord) },
            icon = Icons.Default.AddCircle
        )

    }
}

@Composable
fun ShowHintWord(hintString: String) {
    Text(
        text = hintString
    )
}

@Composable
fun OperationButton(
    label: String,
    clickEvent: () -> Unit = {},
    icon: ImageVector = Icons.Default.Info
) {
    OutlinedButton(onClick = clickEvent) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .rotation(),
                imageVector = icon,
                contentDescription = null
            )
            Text(text = label)
        }

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
    playSound: (SoundType) -> Unit = {},
    onLetterPush: (Char) -> Unit = {}
) {
    var rowHeight by remember { mutableFloatStateOf(0f) }

    Row(
        modifier = modifier.onGloballyPositioned { layoutCoordinates ->
            rowHeight = layoutCoordinates.positionInRoot().y
        },
        horizontalArrangement = Arrangement.Center
    ) {
        textLine.forEachIndexed { charIndex, charSymbol ->
            val isVisible by mutableStateOf(visibilityList[charIndex])

            FallingLetter(
                symbol = charSymbol,
                visibility = isVisible,
                rowHeight = rowHeight.toInt(),
                playSound = playSound,
                onLetterClickListener = onLetterPush
            )
        }
    }
}

@Composable
fun UserInput(textLine: String, userInputFun: (String) -> Unit = {}) {
    OutlinedTextField(
        modifier = Modifier.padding(top = 64.dp),
        value = textLine,
        onValueChange = { newInput -> userInputFun(newInput) },
        shape = RoundedCornerShape(percent = 50),
        textStyle = TextStyle.Default.copy(fontSize = 28.sp),
        trailingIcon = {
            AnimatedVisibility(
                visible = textLine.isNotBlank(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = { userInputFun("") }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                }
            }

        }
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

