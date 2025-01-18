package com.testgl.presentation.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.twotone.Build
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    var screenTitle by remember { mutableStateOf("") }

    val navigateBack: () -> Unit = { navController.popBackStack() }

    val navigateHome: () -> Unit = {
        navController.popBackStack()
        navController.navigate(route = "task")
    }

    Scaffold(
        topBar = {
            AppBar(
                titleStr = screenTitle,
                onNavIconClick = navigateBack,
                onNavHomeClick = navigateHome
            )
        },
        floatingActionButton = {},
        bottomBar = {
            BottomBar(
                onClick = {
                    viewModel.showMessageDlg(it.toString())
                })
        }
    ) { innerPadding ->
        NavHost(
            navController = navController, // Instance of object to navigate between screens
            startDestination = Screens.Collection.title, // A string route defining the destination shown by default
            modifier = Modifier.padding(innerPadding),
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            composable(route = Screens.Options.title) {
                screenTitle = Screens.Options.title  // Change App Bar Title

                ScreenOptions(Modifier.fillMaxSize(), navigateNext = {
                    navController.navigate(
                        route = Screens.Collection.title
                    )
                })
            }

            composable(route = Screens.Collection.title) {
                screenTitle = Screens.Collection.title // Change App Bar Title

                ScreenCollection(Modifier.fillMaxSize(), navigateNext = {
                    navController.navigate(route = Screens.Options.title)
                })
            }

            composable(route = "task") {
                SelectOptionScreen(
                    task = "New task",
                    answerList = listOf("New Option", "Choose", "Variant", "None of this right")
                )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    titleStr: String = "Back",
    onNavIconClick: () -> Unit = {},
    onNavHomeClick: () -> Unit = {}
) {
    TopAppBar(
        actions = {
            IconButton(onClick = onNavHomeClick)
            { Icon(imageVector = Icons.TwoTone.Build, null) }
        },
        title = {
            Text(text = titleStr, modifier = Modifier.padding(start = 16.dp))
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
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        "Songs",
        "Artists",
        "Playlists"
    )
    NavigationBar {
        items.forEachIndexed { index, item ->
            val size = animateFloatAsState(
                if (selectedItem == index) 32.0F
                else 24.0F,
                label = "iconSizeAnimation"
            )

            NavigationBarItem(
                icon = {
                    BadgedBox(
                        badge = {
                            Badge(
                                content = { Text(index.toString()) },
                                containerColor = MaterialTheme.colorScheme.onError
                            )
                        },
                        content = {
                            Icon(
                                Icons.Filled.Favorite,
                                contentDescription = item,
                                modifier = Modifier.size(Dp(size.value)),
                            )
                        })
                },
                label = { Text(item.plus(" $index")) },
                selected = selectedItem == index,
                onClick = { selectedItem = index },
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