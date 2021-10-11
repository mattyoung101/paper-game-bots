package com.hangman.input

import com.hangman.GameState
import java.util.*

class ConsoleInputProvider : InputProvider {
    private val scanner = Scanner(System.`in`)

    override fun getGameState(): GameState {
        print("Input state: ")
        val allLetters = scanner.nextLine().toUpperCase().replace(" ", "")
        val gotLetters = allLetters.replace("_", "")
        return GameState(gotLetters, allLetters)
    }

    override fun validateCharGuess(char: Char): Boolean {
        print("Was that letter in the word? (Y/N) ")
        return scanner.nextLine().toLowerCase() == "y"
    }

    override fun validateStringGuess(string: String): Boolean {
        print("Is the word: $string? (Y/N) ")
        return scanner.nextLine().toLowerCase() == "y"
    }
}