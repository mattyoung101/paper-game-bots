package com.hangman

interface Guess
/** Guesses a single letter **/
data class CharGuess(val guess: Char) : Guess
/** Guesses the entire string **/
data class StringGuess(val guess: String): Guess