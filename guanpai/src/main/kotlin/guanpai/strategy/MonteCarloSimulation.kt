package guanpai.strategy

import guanpai.DECK
import guanpai.Opponent

object MonteCarloSimulation {

    private fun getRemainingCards(playedCards: List<List<String>>): List<String> {
        val copy = DECK.toMutableList()
        for (play in playedCards) {
            for (card in play) {
                copy.remove(card)
            }
        }
        return copy
    }

    /**
     * Runs a Monte-Carlo simulation for the given move. Returns E(X), the expected probability of a win for us.
     * TODO do we want expected depth to loss?
     * @param hand current hand
     * @param opponents list of opponents
     * @param move move to consider
     */
    fun runSimulation(hand: List<String>, opponents: List<Opponent>, move: List<String>) {
        val oppositionPlayed = opponents.map { it.played }
        val remainingDeck = getRemainingCards(listOf(hand))
    }

    /** Number of games to simulate for each call to runSimulation */
    private const val NUM_GAMES = 512
}