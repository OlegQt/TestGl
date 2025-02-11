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


    init {
        pickNewWord()
    }

    private fun getAllWords(): List<String> {
        return listOf(
            "Adventure",
            "Bicycle",
            "Computer",
            "Guitar",
            "Elephant",
            "Butterfly",
            "Cucumber",
            "Library",
            "Jungle",
            "Sunshine",
            "Banana",
            "Chocolate",
            "Pineapple",
            "Mountain",
            "Rainbow",
            "Ocean",
            "University",
            "President",
            "Restaurant",
            "Wilderness",
            "Technology",
            "Galaxy",
            "Notebook",
            "Telescope",
            "Football",
            "Footballer",
            "Lighthouse",
            "Volcano",
            "Hospital",
            "Waterfall",
            "Wormhole",
            "Happiness",
            "Soccer",
            "Laughter",
            "Holiday",
            "Journey",
            "Pyramid",
            "Eclipse",
            "Meteor",
            "Astronaut",
            "Professor",
            "Сопротивление",      // Resistance
            "Оккультный",         // Occult
            "Невозможность",      // Impossibility
            "Экспоненциальный",   // Exponential
            "Противоречие",       // Contradiction
            "Математика",         // Mathematics
            "Независимость",      // Independence
            "Трансцендентальный", // Transcendental
            "Антропология",       // Anthropology
            "Феноменология",      // Phenomenology
            "Суперпозиция",       // Superposition
            "Конфиденциальность", // Confidentiality
            "Метаморфозы",        // Metamorphosis
            "Квантовый",          // Quantum
            "Невозможный",        // Impossible
            "Теорема",            // Theorem
            "Многомерный",        // Multidimensional
            "Парадигма",          // Paradigm
            "Генерация",          // Generation
            "Нейропсихология",     // Neuropsychology
            "Психоанализ",        // Psychoanalysis
            "Интерпретация",      // Interpretation
            "Генетика",           // Genetics
            "Эволюция",           // Evolution
            "Гипотеза",           // Hypothesis
            "Трансформация",      // Transformation
            "Когнитивный",        // Cognitive
            "Детерминизм",        // Determinism
            "Методология",        // Methodology
            "Семиотика",          // Semiotics
            "Эмпирический",       // Empirical
            "Философия",          // Philosophy
            "Социология",         // Sociology
            "Нарратив",           // Narrative
            "Гармония",           // Harmony
            "Антиутопия",         // Dystopia
            "Онтология",          // Ontology
            "Прогнозирование",    // Forecasting
            "Симуляция",          // Simulation
            "Эксперимент",        // Experiment
            "Абстракция",         // Abstraction
            "Архетип",            // Archetype
            "Синергия",           // Synergy
            "Прогрессивный",      // Progressive
            "Реализм",            // Realism
            "Синтаксис",          // Syntax
            "Теоретический"       // Theoretical
        )
    }


    private fun pickNewWord() {
        currentWord = getAllWords().random().uppercase()

        _uiState.update {
            it.copy(
                currentScrambleWord = String(currentWord.toCharArray().apply { shuffle() }),
                hint = ""
            )
        }
    }

    private fun reshuffle() {
        _uiState.update {
            it.copy(currentScrambleWord = String(currentWord.toCharArray().apply { shuffle() }))
        }
    }

    private fun checkAnswer(userWord: String) {
        if (userWord.equals(other = currentWord, ignoreCase = true)) {
            squore++
            _uiState.update { it.copy(gameScore = squore, hint = "") }
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
            is EventType.CheckAnswer -> checkAnswer(event.answer)
            EventType.ScoreInc -> this.testScoreAnimation()
        }
    }
}