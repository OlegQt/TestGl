package com.testgl.presentation.screens

import android.content.res.Configuration
import android.media.AudioAttributes
import android.media.SoundPool
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.testgl.R
import com.testgl.presentation.model.SoundType
import com.testgl.presentation.screens.components.FallingLetter
import com.testgl.presentation.screens.components.Space
import com.testgl.presentation.theme.AppTheme
import com.testgl.presentation.viewmodels.ScrambleGameViewModel

@Composable
fun ScrambleGameScreen(
    modifier: Modifier = Modifier,
    viewModel: ScrambleGameViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsState()

    val soundPool = remember {
        SoundPool.Builder()
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            .setMaxStreams(10)
            .build()
    }

    val soundIdMap = mutableMapOf<SoundType, Int>()
    soundIdMap[SoundType.RisingLetter] =
        soundPool.load(LocalContext.current, R.raw.rising_letter_sond, 1)
    soundIdMap[SoundType.FallingLetter] =
        soundPool.load(LocalContext.current, R.raw.fall_letter_sound, 1)
    soundIdMap[SoundType.Bip] =
        soundPool.load(LocalContext.current, R.raw.bip_sound, 1)


    val playSoundFun: (SoundType) -> Unit = {
        soundIdMap[it]?.let { it1 -> soundPool.play(it1, 1f, 1f, 1, 0, 0f) }
    }


    DisposableEffect(Unit) {
        onDispose {
            soundPool.release()
        }
    }

    Space(modifier = modifier.fillMaxSize())
    Box(modifier = modifier, content = {
        GameCard(
            sourceWord = uiState.value.currentScrambleWord,
            playSound = playSoundFun
        )
    })
}


@Composable
fun GameCard(sourceWord: String, playSound: (SoundType) -> Unit = {}) {
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
            verticalArrangement = Arrangement.Center
        ) {
            UserInput(
                textLine = inputString,
                userInputFun = { txt ->
                    inputString = filterInputText(inputTxtVal = txt, sourceFilterTxt = sourceWord)
                }
            )
        }
    }
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

