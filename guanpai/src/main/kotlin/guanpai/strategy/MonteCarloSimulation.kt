package guanpai.strategy

import guanpai.DECK
import guanpai.game.Player
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

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
     * Runs a Monte-Carlo simulation for the given move. Returns E(X), the average number of remaining cards if we
     * play this move to the end of the game.
     * @param hand current hand
     * @param players all players including self
     * @param move move to consider
     */
    fun runSimulation(hand: List<String>, players: List<Player>, move: List<String>): Double {
        // figure out what cards have not been played yet
        val allKnownCards = players.flatMap { it.played }.flatMap { it.cards }.toMutableList()
        allKnownCards.addAll(hand)
        val remainingCards = getRemainingCards(allKnownCards)

        // TODO change this to return average number of cards remaining

        // execute sampling in parallel using executor service - this will use all CPU cores
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val results = ConcurrentLinkedQueue<Int>()
        for (i in 0 until NUM_GAMES) {
            executor.execute { println("Running iteration $i") /* TODO simulate game and add to average */ }
        }
        executor.shutdown() // force all tasks to execute
        if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
            System.err.println("[Error] Monte Carlo simulation did not finish in time, forcing shutdown.")
            executor.shutdownNow()
        }

        return results.average()
    }

    /** Number of games to simulate for each call to runSimulation */
    private const val NUM_GAMES = 256
}