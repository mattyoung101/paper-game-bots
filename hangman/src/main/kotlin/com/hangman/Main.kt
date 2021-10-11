package com.hangman

import com.hangman.input.AutomatedInputProvider
import com.hangman.input.ConsoleInputProvider

object Main {
    @JvmStatic
    fun main(args: Array<String>){
        val totalWords = 512
        for (i in 0 until totalWords) {
            Hangman(AutomatedInputProvider()).run()
            println("")
        }

        println("========== FINISHED $totalWords TRIALS ==========")
        print("""Min dist to solution: ${Hangman.DIST_TO_SOL.min}
            |Mean dist to solution: ${Hangman.DIST_TO_SOL.mean}
            |Max dist to solution: ${Hangman.DIST_TO_SOL.max}
            |Standard deviation: ${Hangman.DIST_TO_SOL.standardDeviation}
            |Full distribution:
            |${Hangman.DIST_TO_SOL}
            |
            |Wrong words: ${Hangman.TOTAL_WRONG_WORDS} (${Hangman.TOTAL_WRONG_WORDS / totalWords.toDouble() * 100.0}%)
            |Total guesses: ${Hangman.TOTAL_GUESSES}
        """.trimMargin())

        // uncomment if you want to play the game yourself:
//         Hangman(ConsoleInputProvider()).run()
    }
}