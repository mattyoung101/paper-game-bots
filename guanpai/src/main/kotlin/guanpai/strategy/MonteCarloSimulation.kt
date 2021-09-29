package guanpai.strategy

import guanpai.DECK
import guanpai.Player

object MonteCarloSimulation {
    /**
     * Determines which cards have NOT been played yet, by removing them from a copy of the deck
     */
    private fun getRemainingCards(playedCards: List<String>): List<String> {
        val copy = DECK.toMutableList()
        for (card in playedCards) {
            copy.remove(card)
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
    fun runSimulation(hand: List<String>, opponents: List<Player>, move: List<String>) {
        // figure out what cards have not been played yet
        val allOppositionPlayed = opponents.flatMap { it.played }.flatten()
        val allPlayedCards = mutableListOf<String>()
        allPlayedCards.addAll(hand)
        allPlayedCards.addAll(allOppositionPlayed)

        val remainingDeck = getRemainingCards(allPlayedCards)

        while (true) {
            // TODO before this, still need to play the remaining cards this turn
            // game loop
            // 1. deal out a new deck to each player
            // 2. each player plays best move from heuristic until everyone passes
            // 3. repeat 2 until a player has no cards left
        }
    }

    /** Number of games to simulate for each call to runSimulation */
    private const val NUM_GAMES = 512
}