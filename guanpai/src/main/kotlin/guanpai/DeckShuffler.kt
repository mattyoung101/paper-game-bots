package guanpai

import java.util.*

fun main(args: Array<String>){
    val deckCopy = LinkedList(DECK.toList().shuffled())
    val players = listOf(PlayerType.AI, PlayerType.OPP_1, PlayerType.OPP_2).shuffled()

    for (player in players) {
        val cards = mutableListOf<String>()
        for (i in 1..16) {
            cards.add(deckCopy.pop())
        }
        cards.sortWith(CARD_COMPARATOR)
        println("Deck for player $player: ${cards.joinToString(" ")}")
    }

    //println("Cards remaining: $deckCopy")
}