package com.testgl.presentation.screens.scramble

sealed class EventType {
    data object PickNewWord : EventType()
    data object ShuffleWordAgain : EventType()
    data class ShowHint(val level: Int) : EventType()
    data object CheckAnswer : EventType()
    data class TypeIn(val newTextLine: String) : EventType()
    data object ScoreInc : EventType()
}