package guanpai.strategy

import guanpai.DECK
import guanpai.game.AutomatedGame
import guanpai.game.Move
import guanpai.game.Player
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object MonteCarloSimulation {

    /**
     * Runs a Monte-Carlo simulation for the given move. Returns E(X), the average number of remaining cards if we
     * play this move to the end of the game.
     * @param hand current hand
     * @param players all players including self
     * @param move move to consider
     */
    fun runSimulation(hand: List<String>, players: List<Player>, move: Move): Double {
        // execute sampling in parallel using executor service - this will use all CPU cores
        val executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
        val results = ConcurrentLinkedQueue<Int>()
        for (i in 0 until NUM_GAMES) {
            executor.execute {
                // TODO this should not be winning player .cards()
                results.add(AutomatedGame(move, players).playGame().cards)
            }
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