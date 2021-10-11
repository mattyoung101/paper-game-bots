package com.hangman

import com.hangman.input.InputProvider
import com.hangman.strategies.DictionaryStrategy
import com.hangman.strategies.LetterFrequencyStrategy
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics

class Hangman(private val input: InputProvider) {
    private val dictionaryStrategy = DictionaryStrategy()
    private val letterFreqStrategy = LetterFrequencyStrategy()

    private val inWord = HashSet<Char>()
    private val notInWord = HashSet<Char>()

    private var totalGuesses = 0.0
    private var totalCorrect = 0.0

    private fun updateStats(){
        // also update global stats
        DIST_TO_SOL.addValue(totalGuesses)
        TOTAL_GUESSES += totalGuesses
    }

    tailrec fun run(state: GameState = input.getGameState()){
        println("Must be in word: ${inWord.joinToString(", ")}, " +
                "Not in word: ${notInWord.joinToString(", ")}")

        totalGuesses++

        if (state.percentage <= REF_DICT_THRESH){
            println("Using most common letter strategy")
            val choice = letterFreqStrategy.makeGuess(inWord, notInWord, state)

            choice as CharGuess // letterFreqStrat cannot return a StringGuess
            println("My guess: ${choice.guess}")

            return if (input.validateCharGuess(choice.guess)){
                println("Char guess was RIGHT")
                inWord.add(choice.guess)
                totalCorrect++
                run() // get new state as we got a letter right
            } else {
                println("Char guess was WRONG")
                notInWord.add(choice.guess)
                run(state) // no need to get new state
            }
        } else {
            println("Using dictionary strategy")
            val choice = dictionaryStrategy.makeGuess(inWord, notInWord, state)

            if (choice is CharGuess){
                println("My guess: ${choice.guess}")

                if (input.validateCharGuess(choice.guess)){
                    println("Char guess was RIGHT")
                    inWord.add(choice.guess)
                    totalCorrect++
                    run() // get new state as we got a letter right
                } else {
                    println("Char guess was WRONG")
                    notInWord.add(choice.guess)
                    run(state) // no need to get new state
                }
            } else if (choice is StringGuess){
                println("My guess: ${choice.guess}")

                if (input.validateStringGuess(choice.guess)){
                    // we were right, game ends
                    println("String guess was correct.")
                    totalCorrect++
                    updateStats()
                    println("Total guesses: $totalGuesses (word was: ${choice.guess})")
                    return
                } else {
                    // we were wrong, game ends
                    println("String guess was INCORRECT!")
                    TOTAL_WRONG_WORDS++
                    updateStats()
                    return
                }
            }
        }
    }

    companion object {
        /** threshold of the % of acquired letters until we start referring to the dictionary **/
        private const val REF_DICT_THRESH = 0.10f
        val DIST_TO_SOL = DescriptiveStatistics()
        var TOTAL_GUESSES = 0.0
        var TOTAL_WRONG_WORDS = 0.0
    }
}