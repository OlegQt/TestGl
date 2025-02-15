package com.testgl.presentation.screens.scramble

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScrambleGameViewModel : ViewModel() {
    private var currentWord: String = ""
    private var squore = 0

    private val _uiState = MutableStateFlow(ScrambleUiState())
    val uiState = _uiState.asStateFlow()

    private val _userTextInput = MutableStateFlow("")
    val userInputText = _userTextInput.asStateFlow()

    init {
        pickNewWord()
    }

    private fun getAllWords(): List<String> {
        return listOf(
            "Реализм", "Синтаксис", "Теоретический", "Математика", "Философия",
            "Логика", "Технология", "Архитектура", "Эксперимент", "Абстракция",
            "Глобализация", "Декомпозиция", "Моделирование", "Квантовый", "Инновация",
            "Гармония", "Парадигма", "Культура", "Диалектика", "Генерация",
            "Психология", "Образование", "Методология", "Оптимизация", "Эмпирика",
            "Прогнозирование", "Экспертиза", "Концепция", "Коммуникация", "Социология",
            "Интерпретация", "Систематика", "Модуль", "Аналитика", "Артефакт",
            "Реализация", "Интерфейс", "Программирование", "Контекст", "Сложность",
            "Конструкт", "Трансформация", "Функция", "Субъект", "Объект",
            "Метафора", "Активизация", "Когнитивный", "Аргументация", "Интуиция",
            "Анализ", "Параметр", "Генеративный", "Процесс", "Формализация",
            "Наблюдение", "Дискретный", "Обобщение", "Идентификация", "Абстрактный",
            "Реакция", "Модернизация", "Логарифм", "Интеграция", "Адаптация",
            "Рефлексия", "Гармоничный", "Ресурс", "Прогресс", "Цикл",
            "Феномен", "Трансляция", "Асимметрия", "Перцепция", "Определение",
            "Параметризация", "Моделист", "Теория", "Форма", "Перевод",
            "Локализация", "Тенденция", "Теоретик", "Реформа", "Классификация",
            "Генератор", "Норма", "Протокол", "Задача", "Эволюция",
            "Аналогия", "Экспонента", "Контроллер", "Фазис", "Техника",
            "Функционал", "Эмпирический", "Когнитивизм", "Механизм", "Резонанс",
            "Гибкость", "Параллель", "Реактивность", "Множество", "Дистрибуция"
        )
    }


    private fun pickNewWord() {
        currentWord = getAllWords().random().uppercase()

        _uiState.update {
            it.copy(
                currentScrambleWord = String(currentWord.toCharArray().apply { shuffle() }),
                hint = "",
                isTaskCompleted = false
            )
        }

        _userTextInput.update { "" }

    }

    private fun reshuffle() {
        _uiState.update {
            it.copy(currentScrambleWord = String(currentWord.toCharArray().apply { shuffle() }))
        }
    }

    private fun checkAnswer() {
        if (_userTextInput.value.equals(other = currentWord, ignoreCase = true)) {
            squore++
            _uiState.update {
                it.copy(
                    currentScrambleWord = currentWord,
                    gameScore = squore,
                    hint = "",
                    isTaskCompleted = true
                )
            }
            _userTextInput.update { "" }
        }
    }

    private fun testScoreAnimation() {
        squore++
        _uiState.update { it.copy(gameScore = squore) }
    }

    private fun showHint(level: Int) {
        _uiState.update {
            it.copy(
                hint = currentWord.replaceRange(
                    startIndex = 1,
                    endIndex = currentWord.length - 1,
                    replacement = StringBuilder().apply {
                        repeat(currentWord.length - 2) {
                            append("*")
                        }.toString()
                    }
                )
            )
        }
    }

    fun onEvent(event: EventType) {
        when (event) {
            EventType.PickNewWord -> pickNewWord()
            is EventType.ShowHint -> showHint(level = event.level)
            EventType.ShuffleWordAgain -> reshuffle()
            is EventType.CheckAnswer -> checkAnswer()
            EventType.ScoreInc -> this.testScoreAnimation()
            is EventType.TypeIn -> _userTextInput.update { event.newTextLine }
        }
    }
}