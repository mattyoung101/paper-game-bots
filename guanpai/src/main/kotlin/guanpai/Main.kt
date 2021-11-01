package guanpai

import guanpai.analysis.Analysis
import guanpai.strategy.MoveComparator
import guanpai.strategy.MoveSelector
import java.util.*
import kotlin.system.exitProcess

fun main(args: Array<String>){
    val scanner = Scanner(System.`in`)
    println("Guan Pai AI - Matt Young, 2021 (autonomous mode)")

    // prompt for initial hand
    print("Enter AI starting hand: ")
    val myCards = scanner.nextLine().split(" ").toMutableList()
    if (myCards.size != 16) {
        println("Error: Invalid number of cards (you entered: ${myCards.size})")
        exitProcess(1)
    }

    // note "3S" doesn't exist in our world, we ask the user to tell us who is playing first
    print("Please input player ordering, using spaces [AI, OPP_1, OPP_2]: ")
    val players = scanner.nextLine().uppercase().split(" ").map { Player(playerId = PlayerType.valueOf(it)) }
    val aiIndex = players.indexOfFirst { it.playerId == PlayerType.AI }

    // game loop
    while (true) {
        // print player info
        println("\nPlayers:")
        for (i in 0 until 3) {
            println("$i) ${players[i]}")
        }

        // round
        for (player in players) {
            println()
            if (player.playerId == PlayerType.AI) {
                // generate move
                var possibleMoves = Analysis.analyseAll(myCards)
                // if we're not the first player, filter for moves that can beat the last player's moves
                if (aiIndex != 0) {
                    val previousMove = players[aiIndex - 1].played.last()
                    possibleMoves = possibleMoves.filter { MoveComparator.canBeat(it, previousMove) }
                }
                // display possible moves
                println("Possible moves (${possibleMoves.size}):")
                for ((i, move) in possibleMoves.withIndex()) {
                    println("$i) $move")
                }
                // pick a move
                val move: Move = if (player.played.isEmpty()) {
                    // first move
                    MoveSelector.selectOpeningMove(possibleMoves)
                } else {
                    Move(listOf(), MoveType.PASS)
                }
                println("***** AI's move: $move *****")
                // record it and remove cards from virtual hand
                player.played.add(move)
                for (card in move.cards) {
                    myCards.remove(card)
                }
            } else {
                // record move
                print("Enter move for player ${player.playerId}: ")
                val cards = scanner.nextLine().uppercase().split(" ")

                if (cards.isEmpty()) {
                    player.played.add(Move(listOf(), MoveType.PASS))
                } else {
                    // don't bother classifying the move, we just need it for card tracking in monte carlo anyway
                    player.played.add(Move(cards, MoveType.UNKNOWN))
                }
            }
        }
        // TODO not end of round until everyone passes
        println("End of round.")
    }
}