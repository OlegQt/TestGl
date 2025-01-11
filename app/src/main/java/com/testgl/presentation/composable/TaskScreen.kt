package com.testgl.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.testgl.presentation.theme.AppTheme

@Composable
fun SelectOptionScreen(
    modifier: Modifier = Modifier,
    task: String,
    answerList: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
) {
    var selectedValue by rememberSaveable { mutableStateOf("None") }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(task)
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                ListOfOptions(
                    optionList = answerList,
                    onNewSelection = { selectedValue = it }
                )
                AnimationWaiting(
                    Modifier
                        .fillMaxWidth()
                        .size(168.dp))
            }


            var textState by rememberSaveable { mutableStateOf("Txt") }
            OutlinedTextField(
                value = selectedValue,
                onValueChange = { textState = it },
                enabled = true,
                modifier = Modifier.background(Color.Transparent),
                shape = RoundedCornerShape(20),
                label = { Text("Answer") },
                trailingIcon = { Icon(Icons.Default.Call, null) }
            )
        }
    }
}

@Composable
fun ListOfOptions(
    modifier: Modifier = Modifier,
    optionList: List<String>,
    onNewSelection: (String) -> Unit
) {
    Column {
        var selectedItem by rememberSaveable { mutableStateOf("") }

        optionList.forEach { optionString ->
            Row {
                RadioButton(
                    selected = selectedItem == optionString,
                    onClick = {
                        selectedItem = optionString
                        onNewSelection(optionString)
                    }
                )
                Text(
                    text = optionString,
                    modifier = Modifier
                        .clickable(
                            enabled = true,
                            onClick = {
                                selectedItem = optionString
                                onNewSelection(optionString)
                            })
                        .align(Alignment.CenterVertically)
                )
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SelectOptionPreview() {
    AppTheme {
        SelectOptionScreen(
            modifier = Modifier.fillMaxHeight(),
            task = "Task",
            answerList = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        )
    }
}