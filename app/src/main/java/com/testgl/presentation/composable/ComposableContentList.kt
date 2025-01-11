package com.testgl.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ShowList(modifier: Modifier = Modifier) {
    val listUsers = listOf("Marta", "Alex", "Pole")

    Column {
        listUsers.forEach {
            ShowUser(it)
        }
    }
}

@Composable
fun ShowUser(userName: String) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(userName, Modifier.align(Alignment.CenterVertically))
            IconButton({}) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ShowContentPreview() {
    ShowList(Modifier.fillMaxSize())
}