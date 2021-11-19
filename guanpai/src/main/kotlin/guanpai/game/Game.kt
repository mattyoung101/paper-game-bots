@file:Suppress("UnstableApiUsage")

package guanpai.game

import com.google.common.collect.EvictingQueue
import guanpai.DECK
import guanpai.MoveType
import guanpai.PlayerType
import guanpai.analysis.Analysis
import guanpai.strategy.MoveSelector
import java.util.*

abstract class Game(protected val players: MutableList<Player>, private val shouldPrint: Boolean) {
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
        var previousMove: Move? = null

        while (true) {
            // basically loop over all players repeatedly until everyone passes
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
     * Re-arrange the players list so that the [winner] is the first player
     * If OPP_1 wins then [AI OPP_2 OPP_1] becomes [OPP_1 AI OPP_2]
     */
    protected fun reArrangePlayers(winner: Player) {
        //println("Order before: ${players.map {it.playerId}}")

        // code courtesy of Ella <3
        val playersCopy = players.toList()
        val indexOfWinner = players.indexOfFirst { it.playerId == winner.playerId }
        players[0] = winner
        players[1] = playersCopy[(indexOfWinner + 1) % 3]
        players[2] = playersCopy[(indexOfWinner + 2) % 3]

        //println("Order after: ${players.map {it.playerId}}")
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