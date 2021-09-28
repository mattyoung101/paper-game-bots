package guanpai

import guanpai.analysis.Analysis
import java.util.*

fun main(args: Array<String>){
    val scanner = Scanner(System.`in`)
    println("Guan Pai AI - Matt Young, 2021.")

    println("Total cards: ${DECK.size}: ${DECK.joinToString(" ")}")

    // prompt for initial hand
    print("Enter initial hand: ")
    //val cards = scanner.nextLine().split(" ")
    val cards = "4 4 5 5 6 7 8 9 10 K K Q Q A".split(" ").shuffled() // TODO just for testing
    val sortedCards = cards.sortedWith(CARD_COMPARATOR)

    println("Sorted hand: ${sortedCards.joinToString(" ")}")
    val possibleMoves = Analysis.analyseAll(cards)
    println("Possible moves: ${possibleMoves.joinToString(", ")}")
}