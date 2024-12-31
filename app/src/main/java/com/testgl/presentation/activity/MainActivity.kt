package com.testgl.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowDesign()
        }
    }
}

@Composable
fun ShowDesign() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
    ) {
        CustomCard(Icons.Default.Face, "txt1", "txt2", "Bottom")
    }
}

@Composable
fun CustomCard(icon: ImageVector, text1: String, text2: String, bottomText: String) {
    Card(
        onClick = { },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                tint = Color.Black,
                modifier = Modifier.size(48.dp)
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text1)
                Text(text2)
            }
        }
        Text(
            text = bottomText,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )
    }
}