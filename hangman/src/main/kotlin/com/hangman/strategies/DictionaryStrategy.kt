package com.hangman.strategies

import com.hangman.*
import com.hangman.Utils.containsAll
import com.hangman.Utils.containsAny
import com.hangman.Utils.isWordPossible
import org.apache.commons.text.similarity.LevenshteinDistance
import kotlin.system.exitProcess

class DictionaryStrategy : Strategy {
    private val distance = LevenshteinDistance()

    override fun makeGuess(inWord: HashSet<Char>, notInWord: HashSet<Char>, state: GameState): Guess {
        val alreadyGuessed = state.gotLetters.toHashSet()

        val sorted = Dictionary.dict.filter {
                    it.length == state.allLetters.length
                    && !containsAny(it, notInWord)
                    && isWordPossible(it, state.allLetters)
                    && containsAll(it, inWord)
        }.sortedByDescending {
            distance.apply(state.allLetters, it)
        }

        println("Search found ${sorted.size} items")

        return when {
            sorted.isEmpty() -> {
                println("Either game is solved, or no matches have been found.")
                exitProcess(0)
            }
            sorted.size == 1 -> {
                println("Found potential solution: ${sorted[0]}")
                StringGuess(sorted[0])
            }
            else -> {
                println("Best 10 words found: ${sorted.take(10).joinToString(", ")}")

                for (word in sorted) {
                    val outLetters = word.filter {
                        it !in alreadyGuessed
                    }.toSortedSet().sortedBy {
                        LetterFrequencyStrategy.LETTERS.indexOf(it)
                    }

                    if (outLetters.isEmpty()){
                        continue
                    } else {
                        return CharGuess(outLetters[0])
                    }
                }

                System.err.println("!!! Encountered a bug! Cannot find letter to guess in ANY words!")
                StringGuess("CANNOT_FIND_SOLUTION")
            }
        }
    }
}