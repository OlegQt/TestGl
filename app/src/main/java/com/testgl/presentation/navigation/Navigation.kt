package com.testgl.presentation.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.testgl.presentation.model.SoundType
import com.testgl.presentation.screens.GreetingScreen
import com.testgl.presentation.screens.abouttestscreen.AboutScreen
import com.testgl.presentation.screens.scramble.ScrambleGameScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    playSound: (SoundType) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Graph.ScrambleGameScreen,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        composable<Graph.GreetingScreen>(content = { GreetingScreen(modifier = modifier) })

        composable<Graph.AboutScreen>(content = { AboutScreen(modifier = modifier) })

        composable<Graph.ScrambleGameScreen> {
            ScrambleGameScreen(
                modifier = modifier,
                playSoundFun = playSound
            )
        }
    }
}