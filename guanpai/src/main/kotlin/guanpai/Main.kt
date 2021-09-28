package guanpai

import guanpai.analysis.Analysis
import java.util.*

fun main(args: Array<String>){
    val scanner = Scanner(System.`in`)
    println("Guan Pai AI - Matt Young, 2021")

    // prompt for initial hand
    //print("Enter initial hand: ")
    //val cards = scanner.nextLine().split(" ")

    // just for testing
    //val cards = "4 4 4 4 6 7 8 9 10 J K Q Q A A A".split(" ")
    val cards = DECK.toMutableList().shuffled().take(16).sortedWith(CARD_COMPARATOR)
    println("Hand: ${cards.joinToString(" ")}\n")

    val possibleMoves = Analysis.analyseAll(cards)
    println("Possible moves (${possibleMoves.size}):\n${possibleMoves.joinToString("\n")}")
}