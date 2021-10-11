package com.hangman.strategies

import com.hangman.GameState
import com.hangman.Guess

interface Strategy {
    fun makeGuess(inWord: HashSet<Char>, notInWord: HashSet<Char>, state: GameState): Guess
}