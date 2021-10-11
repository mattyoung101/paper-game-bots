package com.hangman.input

import com.hangman.GameState
import com.hangman.Guess

interface InputProvider {
    fun getGameState(): GameState
    fun validateCharGuess(char: Char): Boolean
    fun validateStringGuess(string: String): Boolean
}