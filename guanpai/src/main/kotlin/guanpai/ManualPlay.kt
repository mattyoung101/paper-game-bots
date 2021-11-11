package guanpai

import guanpai.analysis.Analysis
import guanpai.strategy.MoveSelector
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>){
    val scanner = Scanner(System.`in`)
    println("Guan Pai AI - Matt Young, 2021 (maunal mode)")

    // prompt for initial hand
    print("Enter AI starting hand: ")
    val myCards = scanner.nextLine().uppercase().split(" ").toMutableList()
    if (myCards.size != 16) {
        println("Error: Invalid number of cards (you entered: ${myCards.size})")
        exitProcess(1)
    }

    while (true) {
        // generate possible moves
        println("\nRemaining cards: ${myCards.sortedWith(CARD_COMPARATOR).joinToString(" ")}")
        val possibleMoves = Analysis.analyseAll(myCards).associateWith { MoveSelector.getMoveCardValue(it) }
        val sortedMoves = possibleMoves.keys.sortedByDescending { possibleMoves[it]!! }
        println("Possible moves (${possibleMoves.size}):")

        // display them
        for ((i, move) in sortedMoves.withIndex()) {
            println("$i) $move - value: ${possibleMoves[move]}")
        }

        // remove user's selection
        print("Enter move selection: ")
        val move = scanner.nextLine().toInt()
        val moveCards = sortedMoves[move].cards
        // do not use removeAll!
        for (card in moveCards) {
            myCards.remove(card)
        }

        if (myCards.isEmpty()) {
            println("Game is over!")
            break
        }
    }
}