package com.hangman.strategies

import com.hangman.CharGuess
import com.hangman.GameState
import com.hangman.Guess

class LetterFrequencyStrategy : Strategy {
    private var guessIndex = 0

    override fun makeGuess(inWord: HashSet<Char>, notInWord: HashSet<Char>, state: GameState): Guess {
        return CharGuess(LETTERS[guessIndex++])
    }

    companion object {
        /**
         * Reference:
         * https://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language
         **/
        val LETTERS = "EARIOTNSLCUDPMHGBFYWKVXZJQ".toCharArray()
    }
}