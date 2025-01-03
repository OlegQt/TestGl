package com.testgl.presentation.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.testgl.presentation.viewmodels.MainViewModel

@Composable
fun RootView(viewModelParam: MainViewModel? = null) {
    val top: @Composable () -> Unit = { ShowTopAppBar(viewModelParam) }
    val bottom: @Composable () -> Unit = { ShowNavBar(viewModelParam) }
    val floatBtn: @Composable () -> Unit = { ShowFloatBtn(viewModelParam) }

    Scaffold(
        topBar = top,
        bottomBar = bottom,
        floatingActionButton = floatBtn
    ) { innerPadding ->
        ShowList(viewModelParam,Modifier.fillMaxWidth().padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTopAppBar(viewModelParam: MainViewModel? = null) {
    Row(Modifier.fillMaxWidth()) {
        IconButton(
            { viewModelParam?.showMessageDlg("BACK") },
            Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "backToMain"
            )
        }
        TopAppBar(title = { Text("Compose app") }, Modifier.fillMaxWidth())
    }
}

@Composable
fun ShowNavBar(viewModelParam: MainViewModel? = null) {
    Text("Navigation")
}

@Composable
fun ShowFloatBtn(viewModelParam: MainViewModel? = null) {
    FloatingActionButton(
        onClick = { viewModelParam?.showMessageDlg("ADD") }) {
        Icon(imageVector = Icons.Default.AddCircle, contentDescription = "Add")
    }
}