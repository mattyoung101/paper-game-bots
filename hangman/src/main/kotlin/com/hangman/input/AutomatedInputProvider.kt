package com.hangman.input

import com.hangman.Dictionary
import com.hangman.GameState

class AutomatedInputProvider : InputProvider {
    private val selectedWord = Dictionary.dict.random().toUpperCase()
    private val currentString = "_".repeat(selectedWord.length).toMutableList()

    init {
        println("Automated input provider, selected word: $selectedWord")
    }

    override fun getGameState(): GameState {
        val allLetters = currentString.joinToString("")
        val gotLetters = allLetters.replace("_", "")
        return GameState(gotLetters, allLetters)
    }

    override fun validateCharGuess(char: Char): Boolean {
        val ch = char.toUpperCase()
        return if (ch in selectedWord){
            // put in the letters into the current string
            for (i in currentString.indices){
                if (selectedWord[i] == ch){
                    currentString[i] = ch
                }
            }
            println("Current state is now: ${currentString.joinToString("")}")
            true
        } else {
            false
        }
    }

    override fun validateStringGuess(string: String): Boolean {
        return string.equals(selectedWord, true)
    }
}