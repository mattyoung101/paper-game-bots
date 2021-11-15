package guanpai

import guanpai.analysis.Analysis
import guanpai.strategy.MoveSelector

fun main(args: Array<String>){
    val cards = DECK.toMutableList().shuffled().take(16).sortedWith(CARD_COMPARATOR)
    println("Hand: ${cards.joinToString(" ")}\n")

    val possibleMoves = Analysis.analyseAll(cards)
    println("Possible moves (${possibleMoves.size}):")
    for (move in possibleMoves){
        println(move)
    }
}