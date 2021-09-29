package guanpai

import guanpai.analysis.Analysis
import guanpai.strategy.MoveEvaluator
import java.util.*

fun main(args: Array<String>){
    val scanner = Scanner(System.`in`)
    println("Guan Pai AI - Matt Young, 2021")

    // prompt for initial hand
    //print("Enter initial hand: ")
    //val cards = scanner.nextLine().split(" ")

    // just for testing
    //val cards = "4 4 5 5 6 7 8 9 10 J J J Q Q Q K K K".split(" ")
    val cards = DECK.toMutableList().shuffled().take(16).sortedWith(CARD_COMPARATOR)
    println("Hand: ${cards.joinToString(" ")}\n")

    val possibleMoves = Analysis.analyseAll(cards)
    //println("Possible moves (${possibleMoves.size}):\n${possibleMoves.joinToString("\n")}")
    println("Possible moves (${possibleMoves.size}:")

    for (move in possibleMoves){
        println("$move - value: ${MoveEvaluator.evaluateMove(move, 0, listOf())}")
    }
}