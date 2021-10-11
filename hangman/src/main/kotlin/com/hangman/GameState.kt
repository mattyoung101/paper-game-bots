package com.hangman

data class GameState(val gotLetters: String, val allLetters: String){
    val percentage = gotLetters.length.toFloat() / allLetters.length.toFloat()
}