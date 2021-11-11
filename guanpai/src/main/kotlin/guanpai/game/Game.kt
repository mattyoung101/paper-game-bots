@file:Suppress("UnstableApiUsage")

package guanpai.game

import com.google.common.collect.EvictingQueue
import guanpai.MoveType
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
            for (player in players) {
                val move = getMoveForPlayer(player)
                if (move.type != MoveType.PASS) {
                    // not a pass, record move
                    player.played.add(move)
                    player.cards -= move.cards.size
                    passedPlayers.clear()
                } else {
                    // it was a pass, see if round should be over
                    passedPlayers.add(player)
                    if (passedPlayers.size == 2) {
                        // two players passed in a row, so whoever did not pass, wins
                        return players.first { it.playerId !in passedPlayers.map { a -> a.playerId } }
                    }
                }
            }
        }
    }

    /**
     * @return the list of cards the [player] should play, as defined by the implementation
     */
    abstract fun getMoveForPlayer(player: Player): Move

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

    override fun playGame(): Player {
        gprintln("\nPlayers:")
        for (i in 0 until 3) {
            gprintln("$i) ${players[i]}")
        }

        // keep playing rounds until a player has zero cards
        while (true) {
            val roundWinner = playRound()
            gprintln("***** End of round, won by ${roundWinner.playerId} *****\n")

            val minPlayer = players.minByOrNull { it.cards }!!
            if (minPlayer.cards == 0) {
                // a player has won
                gprintln("***** Player ${minPlayer.playerId} has won the game! *****")
                return minPlayer
            }
        }
    }

    override fun getMoveForPlayer(player: Player): Move {
        // TODO handle case if AI (need to return AI best move)
        print("Enter move for player ${player.playerId}: ")
        val cardStr = scanner.nextLine().uppercase().trim()

        if (cardStr.isEmpty()) {
            // pass
            return Move(listOf(), MoveType.PASS)
        }
        // not a pass
        // don't bother classifying the move, we just need it for card tracking in monte carlo anyway
        return Move(listOf(), MoveType.UNKNOWN)
    }
}

/**
 * Game in which all players autonomously decide their inputs, used in Monte Carlo simulation
 */
class AutomatedGame {
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
}