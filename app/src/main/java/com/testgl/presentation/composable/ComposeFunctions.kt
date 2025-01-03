package com.testgl.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.testgl.presentation.viewmodels.MainViewModel

@Composable
fun ShowDesign(viewModelParam: MainViewModel?, modifier: Modifier) {
    Column(modifier.padding(horizontal = 12.dp)) {
        CustomCard(viewModelParam, modifier)
        CustomCard(viewModelParam, modifier)
        CustomCard(viewModelParam, modifier)
    }
}

@Composable
fun CustomCard(viewModelParam: MainViewModel? = null, modifier: Modifier) {
    val txt = viewModelParam?.textData?.collectAsState()

    Card(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Icon",
                tint = Color.Black,
                modifier = Modifier.size(48.dp)
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text("text1")
                Text("text2")
            }
        }
        Text(
            text = txt?.value.toString(),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}


@Composable
fun RootView(viewModelParam: MainViewModel? = null) {
    val top: @Composable () -> Unit = { ShowTopAppBar() }
    val bottom: @Composable () -> Unit = { ShowNavBar() }
    val center: @Composable () -> Unit = {}

    Scaffold(
        topBar = top,
        bottomBar = bottom,
    ) { innerPadding ->
        ShowDesign(
            viewModelParam,
            Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowTopAppBar(viewModelParam: MainViewModel? = null) {
    Row(Modifier.fillMaxWidth()) {
        IconButton({}, Modifier.align(Alignment.CenterVertically)) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "backToMain"
            )
        }
        TopAppBar(title = { Text("Compose app") }, Modifier.fillMaxWidth())
    }
}

@Composable
fun ShowNavBar() {
    Text("Navigation")

}