package guanpai.strategy

import guanpai.CARDS
import guanpai.game.Move
import guanpai.MoveType
import guanpai.PlayerType
import guanpai.game.Player

/**
 * Used in Monte-Carlo simulation for selecting player moves, and for selecting opening move
 */
class MoveSelector(private val shouldPrint: Boolean) {
    // early game: prioritise getting out biggest stack possible that is not a bomb, also no 2 allowed
    // mid game: try to get out big stacks of lower-valued cards, king is ok too, preferably no 2
    // late game: if an opponent is low on cards, spam out high cards, otherwise try to dump low value cards
    // to decide which phase we are in: number of cards opponent with lowest number has

    /**
     * Uses a heuristic to select a semi-OK move. Used in Monte-Carlo simulation.
     * @param player AI player to select move for
     * @param allMoves list of all possible moves that could be played by the current cards in the hand
     * @param toBeat move to beat, or null
     * @param allPlayers all players in the game, including the AI player
     * @returns the move, never null
     */
    fun selectMove(player: Player, allMoves: List<Move>, toBeat: Move?, allPlayers: List<Player>): Move {
        if (toBeat == null) {
            gprintln("Using opening move strategy")
            return selectOpeningMove(allMoves)
        }

        // find the opponent with the minimum number of cards (excluding AI)
        val opponentMin = allPlayers.filter { it.playerId != PlayerType.AI }.minByOrNull { it.cards }!!.cards
        return if (opponentMin <= LATE_GAME_CARDS) {
            // it's late game, block the opponent from moving
            // TODO don't block ourselves moving! if we have the advantage, play cheap cards!
            // TODO change this to be, if we are starting a round and its late game, play all our expensive cards
            gprintln("Using costliest move strategy (opponentMin = $opponentMin)")
            selectCostliestMove(allMoves, toBeat) ?: Move(listOf(), MoveType.PASS)
        } else {
            // it's early game, return the cheapest move that beats the last played move
            gprintln("Using cheapest move strategy")
            selectCheapestMove(allMoves, toBeat) ?: Move(listOf(), MoveType.PASS)
        }
    }

    /**
     * Returns the total card value of the move
     */
    private fun getMoveCardValue(move: Move): Double {
        return move.cards.sumOf { CARDS.indexOf(it) }.toDouble()
    }

    /**
     * Select the move with the highest number of cards that does not include ace or two (if possible)
     */
    private fun selectOpeningMove(possibleMoves: List<Move>): Move {
        // first, attempt to return a set without using ace or two
        return possibleMoves.filter { !("A" in it.cards || "2" in it.cards) }.maxByOrNull { it.cards.size }
            ?: // the only option is to return a set that uses an ace or two
            possibleMoves.maxByOrNull { it.cards.size }!!
    }

    private fun selectCheapestMove(allMoves: List<Move>, toBeat: Move): Move? {
        return allMoves.filter { MoveComparator.canBeat(it, toBeat) }.minByOrNull { getMoveCardValue(it) }
    }

    private fun selectCostliestMove(allMoves: List<Move>, toBeat: Move): Move? {
        return allMoves.filter { MoveComparator.canBeat(it, toBeat) }.maxByOrNull { getMoveCardValue(it) }
    }

    private fun gprintln(msg: String) {
        if (shouldPrint) println(msg)
    }

    private fun gprintln() { gprintln("") }

    companion object {
        /**
         * If an opponent has <= this many cards, it's late game
         */
        private const val LATE_GAME_CARDS = 4
    }
}