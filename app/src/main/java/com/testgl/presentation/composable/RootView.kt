package com.testgl.presentation.composable

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.testgl.presentation.theme.AppTheme
import com.testgl.presentation.viewmodels.MainViewModel

// enum values that represent the screens in the app
enum class Screens(val title: String) {
    Options("Options_Screen"),
    Collection("Collection_Screen")
}

@Composable
fun RootView(
    viewModel: MainViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    Scaffold(topBar = {}, floatingActionButton = {}, bottomBar = {}) { innerPadding ->
        NavHost(
            navController = navController, // Instance of object to navigate between screens
            startDestination = Screens.Collection.title, // A string route defining the destination shown by default
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) }
        ) {
            composable(route = Screens.Options.title) {
                ScreenOptions(Modifier.fillMaxSize(), navigateNext = {
                    navController.navigate(
                        route = Screens.Collection.title
                    )
                })
            }
            composable(route = Screens.Collection.title) {
                ScreenCollection(Modifier.fillMaxSize(), navigateNext = {
                    navController.navigate(route = Screens.Options.title)
                })
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = false)
@Composable
fun ScreenPreview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        RootView()
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

/*
    Column(
        Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
    ) {
        SelectOptionScreen(
            task = "Task",
            onSelectionChanged = {},
            onCancelButtonClicked = {},
            onNextButtonClicked = {},
            answerList = listOf("A option", "B option", "C option")
        )
        //FloatingActionButton({}) { AnimationGear() }
    }
 */