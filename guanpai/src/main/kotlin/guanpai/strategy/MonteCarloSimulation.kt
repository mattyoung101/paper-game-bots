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

    private fun simulateRound() {

    }

    private fun simulateGame() {
    }

    /**
     * Runs a Monte-Carlo simulation for the given move. Returns E(X), the expected number of full turns until loss.
     * @param hand current hand
     * @param players all players including self
     * @param move move to consider
     */
    fun runSimulation(hand: List<String>, players: List<Player>, move: List<String>) {
        // figure out what cards have not been played yet
        val allKnownCards = players.flatMap { it.played }.flatMap { it.cards }.toMutableList()
        allKnownCards.addAll(hand)
        val remainingCards = getRemainingCards(allKnownCards)

        // TODO before this, still need to play the remaining cards this turn
        // game loop
        // 1. deal out a new deck to each player
        // 2. each player plays best move from heuristic until everyone passes
        // 3. repeat 2 until a player has no cards left

        // TODO multi thread this!
        for (i in 0 until NUM_GAMES) {
            // simulate game
        }
    }

    /** Number of games to simulate for each call to runSimulation */
    private const val NUM_GAMES = 256
}