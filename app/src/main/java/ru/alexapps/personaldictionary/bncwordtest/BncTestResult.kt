package ru.alexapps.personaldictionary.bncwordtest

import ru.alexapps.personaldictionary.data.models.BncWord
import ru.alexapps.personaldictionary.data.models.EnglishLevel
import java.util.*

class BncTestResult(val knownWords: Array<BncWord>, val unKnownWords: Array<BncWord>, val testDate: Date, val id: Long? = 0) {
    private val difficultySet: MutableSet<Int> = mutableSetOf()
    private val _categoryList: MutableList<DifficultyCategory> = mutableListOf()
    val categoryList: List<DifficultyCategory> = _categoryList

    init {
        knownWords.forEach { difficultySet.add(it.difficulty) }
        unKnownWords.forEach { difficultySet.add(it.difficulty) }
        difficultySet.forEach { difficulty ->
            run {
                val knownByDifficulty =
                    knownWords.filter { it.difficulty == difficulty }.toTypedArray()
                val unknownByDifficulty =
                    unKnownWords.filter { it.difficulty == difficulty }.toTypedArray()
                _categoryList.add(
                    DifficultyCategory(
                        difficulty,
                        knownByDifficulty,
                        unknownByDifficulty,
                        1000
                    )
                )
            }
        }
    }

    fun getExtrapolationKnownWords(): Int {
        var extrapolationKnownWords = 0
        categoryList.forEach { extrapolationKnownWords += it.extrapolationKnownWords }
        return extrapolationKnownWords
    }

    fun getEnglishLevel(): EnglishLevel {
        val knownWords = getExtrapolationKnownWords()
        return when {
            knownWords < 1200 -> EnglishLevel.A1
            knownWords < 2000 -> EnglishLevel.A2
            knownWords < 4000 -> EnglishLevel.B1
            knownWords < 7000 -> EnglishLevel.B2
            else -> EnglishLevel.C1
        }
    }

    class DifficultyCategory(
        val difficulty: Int,
        val knownWords: Array<BncWord>,
        val unKnownWords: Array<BncWord>,
        private val totalGroupSize: Int
    ) {
        val knownPart: Float
        val extrapolationKnownWords: Int

        init {
            knownPart = knownWords.size.toFloat() / (knownWords.size + unKnownWords.size).toFloat()
            extrapolationKnownWords = (totalGroupSize * knownPart).toInt()
        }
    }
}
