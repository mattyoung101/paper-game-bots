package guanpai

import guanpai.analysis.Analysis
import guanpai.strategy.MoveEvaluator

fun main(args: Array<String>){
    val cards = DECK.toMutableList().shuffled().take(16).sortedWith(CARD_COMPARATOR)
    println("Hand: ${cards.joinToString(" ")}\n")

    val possibleMoves = Analysis.analyseAll(cards)
    println("Possible moves (${possibleMoves.size}):")
    for (move in possibleMoves){
        println("$move - value: ${MoveEvaluator.evaluateMove(move, 0, listOf())}")
    }
}