@file:Suppress("UnstableApiUsage")

package guanpai.game

import com.google.common.collect.EvictingQueue
import guanpai.MoveType
import guanpai.PlayerType
import guanpai.analysis.Analysis
import guanpai.strategy.MonteCarloSimulation
import guanpai.strategy.MoveSelector
import java.util.*

abstract class Game(protected val players: List<Player>, private val shouldPrint: Boolean) {
    /** Last two players in a row to pass, used to determine if round is over. Cleared if a player does not pass. */
    private val passedPlayers = EvictingQueue.create<Player>(2)

    protected fun gprintln(msg: String) {
        if (shouldPrint) println(msg)
    }

    protected fun gprintln() { gprintln("") }

    /**
     * Plays one round of Guan Pai
     * @return the player who won the round
     */
    protected fun playRound(): Player {
        passedPlayers.clear()

        while (true) {
            // basically loop over all players repeatedly until everyone passes
            var previousMove: Move? = null
            for (player in players) {
                // get move
                val move = getMoveForPlayer(player, previousMove, players)
                previousMove = move

                if (move.cards.isNotEmpty()) {
                    // not a pass, record move
                    player.played.add(move)
                    player.cards -= move.cards.size
                    // remove from known hand (only really applies to AI)
                    for (card in move.cards) {
                        player.hand.remove(card)
                    }
                    // reset passed players count
                    passedPlayers.clear()
                } else {
                    // it was a pass, see if round should be over
                    passedPlayers.add(player)
                    if (passedPlayers.size == 2) {
                        // two players passed in a row, so whoever did not pass, wins
                        return players.first { it.playerId !in passedPlayers.map { a -> a.playerId } }
                    }
                }

                // check if this player won the round
                if (player.cards <= 0) {
                    return player
                }
            }
        }
    }

    /**
     * @param previousMove the previous move played in this round, or null if first move
     * @param allPlayers list of all players in the game
     * @return the list of cards the [player] should play, as defined by the implementation
     */
    abstract fun getMoveForPlayer(player: Player, previousMove: Move? = null, allPlayers: List<Player>): Move

    /**
     * Each implementation has a slightly different game mechanic, so it's left abstract.
     * @return player who won the game
     */
    abstract fun playGame(): Player
}

/**
 * Game which receives inputs from the console, used to query the real game being played in real life
 */
class ConsoleGame(players: List<Player>) : Game(players, true) {
    private val scanner = Scanner(System.`in`)
    private val moveSelector = MoveSelector(true)

    override fun playGame(): Player {
        // keep playing rounds until a player has zero cards
        while (true) {
            gprintln("Players:")
            for (i in 0 until 3) {
                gprintln("$i) ${players[i]}")
            }
            gprintln()
            // play the round (important code here)
            val roundWinner = playRound()
            gprintln("***** End of round, won by ${roundWinner.playerId} *****\n")

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
            gprintln("AI potential moves with hand (${possibleMoves.size}):")
            for ((i, move) in possibleMoves.withIndex()) {
                gprintln("$i) $move")
            }

            // select move (TODO in future use monte carlo simulation)
            val move = moveSelector.selectMove(player, possibleMoves, previousMove, allPlayers)
            if (move.cards.isEmpty()) {
                gprintln("Move for AI: PASS *****")
            } else {
                gprintln("Move for AI: ${move.cards.joinToString(" ")}")
            }
            return move
        } else {
            print("Enter move for player ${player.playerId}: ")
            val cardStr = scanner.nextLine().uppercase().trim()
            if (cardStr.isEmpty()) {
                // pass
                return Move(listOf(), MoveType.PASS)
            }
            // not a pass, return cards but don't bother classifying move
            return Move(cardStr.split(" "), MoveType.UNKNOWN)
        }
    }
}

/**
 * Game in which all players autonomously decide their inputs, used in Monte Carlo simulation
 * Note: players list is copied in, so the original list is not modified.
 */
/*class AutomatedGame(players: List<Player>) : Game(players.toList(), false) {
    private val moveSelector = MoveSelector(false)

//    fun simulateGame() {
//        // TODO before this, still need to play the remaining cards this turn
//        // game loop
//        // 1. deal out a new deck to each player
//        // 2. each player plays best move from heuristic until everyone passes
//        // 3. repeat 2 until a player has no cards left
//
//        while (true) {
//            playRound()
//        }
//    }

    override fun getMoveForPlayer(player: Player, previousMove: Move?, allPlayers: List<Player>): Move {
        // generate potential moves
        val possibleMoves = Analysis.analyseAll(player.hand)
        // in the automated game, all players are simulated
        return moveSelector.selectMove(player, possibleMoves, previousMove, allPlayers)
    }

    override fun playGame(): Player {
        // deal out remaining cards, noting that the players list is already cloned so we are safe to modify it
    }
}*/