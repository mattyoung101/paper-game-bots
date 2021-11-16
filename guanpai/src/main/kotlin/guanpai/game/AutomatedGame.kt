package guanpai.game

import com.badlogic.gdx.math.RandomXS128
import guanpai.DECK
import guanpai.analysis.Analysis
import guanpai.strategy.MoveSelector
import java.util.*

/**
 * Game in which all players autonomously decide their inputs, used in Monte Carlo simulation
 * Note: players list is copied in, so the original list is not modified.
 * @param move move for AI to play first
 */
class AutomatedGame(private val move: Move, players: List<Player>) : Game(players.toMutableList(), false) {
    private val moveSelector = MoveSelector(false)
    private val prng = RandomXS128()

    override fun getMoveForPlayer(player: Player, previousMove: Move?, allPlayers: List<Player>): Move {
        // generate potential moves
        val possibleMoves = Analysis.analyseAll(player.hand)
        // in the automated game, all players are simulated
        return moveSelector.selectMove(player, possibleMoves, previousMove, allPlayers)
    }

    /**
     * Determines which cards have NOT been played yet, by removing them from a copy of the original deck
     */
    private fun findRemainingCards(playedCards: List<String>): LinkedList<String> {
        // create a copy of the deck so we don't destroy it
        val copy = DECK.toMutableList()
        for (card in playedCards) {
            copy.remove(card)
        }
        return LinkedList(copy)
    }

    override fun playGame(): Player {
        // for each player in the list, if their hand is empty, randomly deal out cards we know must exist in someone's
        // hand.
        // basically we record all the cards the players _have_ played, so we know what must _not_ be in their hand
        // and so we deal that out randomly as a guesstimate of what they are holding (only useful if we Monte-Carlo it
        // to account for probability). since we are assigning cards randomly we will never be correct, but in the long
        // run we will get a good estimate that accounts for many different cases.
        // performance: check if copy required here
        val allKnownCards = players.flatMap { it.played }.flatMap { it.cards }.toMutableList()
        val remainingCards = findRemainingCards(allKnownCards)
        remainingCards.shuffle(prng)

        for (player in players) {
            if (player.hand.isNotEmpty()) continue
            for (i in 0 until player.cards) {
                // grab the first card off the remaining cards list
                player.hand.add(remainingCards.pop())
            }
        }

        // we must now simulate the current round, so play the AI's move, then see who finishes the round

        // now that we've finished the current round, we may play the remaining rounds until the game is done as normal

        // TEMPORARY
        return players[0]
    }
}