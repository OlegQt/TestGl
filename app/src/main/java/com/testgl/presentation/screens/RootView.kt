package com.testgl.presentation.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.testgl.presentation.model.SoundType
import com.testgl.presentation.navigation.Graph
import com.testgl.presentation.navigation.Navigation
import com.testgl.presentation.theme.AppTheme

@Preview(showSystemUi = true, showBackground = false)
@Composable
fun ScreenPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        ShowScreenContent()
    }
}

@Composable
fun ShowScreenContent(playSound: (SoundType) -> Unit = {}) {
    val navigation = rememberNavController()

    var selectedScreen by rememberSaveable { mutableIntStateOf(0) }

    val navigateToScreen: () -> Unit = {
        when (selectedScreen) {
            0 -> {
                navigation.navigate(route = Graph.GreetingScreen)
            }

            1 -> {
                navigation.navigate(route = Graph.ScrambleGameScreen)
            }

            2 -> {
                navigation.navigate(
                    route = Graph.AboutScreen("Some info")
                )
            }
        }
    }

    val appBar: @Composable () -> Unit = { AppBar(selectedScreen) }

    val bottomBar: @Composable () -> Unit = {
        BottomBar(onClick = {
            selectedScreen = it
            navigateToScreen()
            playSound(SoundType.Bip)
        })
    }

    Scaffold(
        topBar = appBar,
        bottomBar = bottomBar,
        content = { inPad ->
            Navigation(
                navController = navigation,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(inPad),
                playSound = playSound
            )
        })

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    screen: Int,
    onNavIconClick: () -> Unit = {},
) {
    TopAppBar(
        actions = {
            //Text("TODO", Modifier.background(MaterialTheme.colorScheme.error))
        },
        title = {
            Text(
                text = Graph.ScreenNames.entries[screen].toString(),
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavIconClick) {
                Icon(imageVector = Icons.AutoMirrored.TwoTone.ArrowBack, contentDescription = null)
            }
        })
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit = {}
) {
    var selectedItem by remember { mutableIntStateOf(1) }

    NavigationBar {
        Graph.ScreenNames.entries.forEachIndexed { index, item ->

            val size = animateFloatAsState(
                if (selectedItem == index) 32.0F
                else 24.0F,
                label = "iconSizeAnimation"
            )

            val iconImage = when (index) {
                0 -> Icons.Filled.Person
                1 -> Icons.TwoTone.CheckCircle
                else -> Icons.Filled.Info
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = iconImage,
                        contentDescription = item.toString(),
                        modifier = Modifier.size(Dp(size.value)),
                    )
                },
                label = { Text(item.toString()) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onClick(index)
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun AnimationWaiting(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("dearSearchAnimation.json")
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}

@Composable
fun AnimationGear(modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.Asset("gear_animation.json")
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        iterations = LottieConstants.IterateForever
    )
}