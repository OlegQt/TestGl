package com.testgl.presentation.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.testgl.presentation.navigation.Graph

@Composable
fun GreetingScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        repeat(10) {
            Card(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(all = 4.dp)
            )
            {
                Row {
                    Text("Item num $it")
                    Spacer(Modifier.weight(1.0f))
                    IconButton(onClick = {
                        navHostController.navigate(Graph.AboutScreen("$it"))
                    }) {
                        Icon(imageVector = Icons.TwoTone.AddCircle, contentDescription = null)
                    }
                }
            }
        }
    }
}