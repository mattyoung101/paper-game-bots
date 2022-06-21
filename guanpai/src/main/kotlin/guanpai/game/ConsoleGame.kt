package guanpai.game

import guanpai.MoveType
import guanpai.PlayerType
import guanpai.analysis.Analysis
import guanpai.strategy.MoveSelector
import java.util.*

/**
 * Game which receives inputs from the console, used to query the real game being played in real life
 */
class ConsoleGame(players: MutableList<Player>) : Game(players, true) {
    private val scanner = Scanner(System.`in`)
    private val moveSelector = MoveSelector(true)

    override fun playGame(): Player {
        // keep playing rounds until a player has zero cards
        var counter = 1
        while (true) {
            gprintln("\n***** START OF ROUND ${counter++} *****\nPlayers:")
            for (i in 0 until 3) {
                gprintln("$i) ${players[i]}")
            }
            gprintln()
            // play the round (important code here)
            val roundWinner = playRound()
            gprintln("***** End of round, won by ${roundWinner.playerId} *****\n")
            // rearrange players so that the winner of the round plays first
            reArrangePlayers(roundWinner)

            val minPlayer = players.minByOrNull { it.cards }!!
            if (minPlayer.cards <= 0) {
                // a player has won
                gprintln("***** Player ${minPlayer.playerId} has won the game! *****")
                return minPlayer
            }
        }
    }

    override fun getMoveForPlayer(player: Player, previousMove: Move?, allPlayers: List<Player>): Move {
        if (player.playerId == PlayerType.AI) {
            // generate moves and display them
            val possibleMoves = Analysis.analyseAll(player.hand)
            /*gprintln("AI potential moves with hand (${possibleMoves.size}):")
            for ((i, move) in possibleMoves.withIndex()) {
                gprintln("$i) $move")
            }*/

            // select move (TODO in future use monte carlo simulation)
            val move = moveSelector.selectMove(player.playerId, possibleMoves, previousMove, allPlayers)
            if (move.cards.isEmpty()) {
                gprintln("Move for AI: PASS")
            } else {
                gprintln("Move for AI: ${move.cards.joinToString(" ")}")
            }
            return move
        } else {
            var cardStr: String
            // we have an issue with blank lines
            while (true) {
                print("Enter move for player ${player.playerId}: ")
                cardStr = scanner.nextLine().uppercase().trim()
                if (cardStr.isNotEmpty()) break
                println("Sorry, please try again.")
            }
            if (cardStr == "PASS" || cardStr == "P" || cardStr == "N") {
                // pass
                return Move(listOf(), MoveType.PASS)
            }
            // not a pass, return cards but don't bother classifying move
            return Move(cardStr.split(" "), MoveType.UNKNOWN)
        }
    }
}