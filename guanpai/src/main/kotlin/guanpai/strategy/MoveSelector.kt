package guanpai.strategy

import guanpai.CARDS
import guanpai.game.Move
import guanpai.MoveType
import guanpai.game.Player

/**
 * Used in Monte-Carlo simulation for selecting player moves, and for selecting opening move
 */
object MoveSelector {
    // early game: prioritise getting out biggest stack possible that is not a bomb, also no 2 allowed
    // mid game: try to get out big stacks of lower-valued cards, king is ok too, preferably no 2
    // late game: if an opponent is low on cards, spam out high cards, otherwise try to dump low value cards
    // to decide which phase we are in: number of cards opponent with lowest number has

    /**
     * Uses a heuristic to select a semi-OK move. Used in Monte-Carlo simulation.
     */
    fun selectMove(player: Player, allMoves: List<Move>, toBeat: Move, allPlayers: List<Player>, isOpening: Boolean): Move {
        if (isOpening) {
            return selectOpeningMove(allMoves)
        }

        // find the opponent with the minimum number of cards
        val opponentMin = allPlayers.filter { it.playerId != player.playerId }.minByOrNull { it.cards }!!.cards
        return if (opponentMin <= LATE_GAME_CARDS) {
            // it's late game, block the opponent from moving
            // TODO don't block ourselves moving! if we have the advantage, play cheap cards!
            // TODO change this to be, if we are starting a round and its late game, play all our expensive cards
            selectCostliestMove(allMoves, toBeat) ?: Move(listOf(), MoveType.PASS)
        } else {
            // it's early game, return the cheapest move that beats the last played move
            selectCheapestMove(allMoves, toBeat) ?: Move(listOf(), MoveType.PASS)
        }
    }

    /**
     * Returns the total card value of the move
     */
    fun getMoveCardValue(move: Move): Double {
        return move.cards.sumOf { CARDS.indexOf(it) }.toDouble()
    }

    /**
     * Select the move with the most number of cards that does not include ace or two
     * @return the opening move
     */
    fun selectOpeningMove(possibleMoves: List<Move>): Move {
        return possibleMoves.filter { !("A" in it.cards || "2" in it.cards) }.maxByOrNull { it.cards.size }!!
    }

    private fun selectCheapestMove(allMoves: List<Move>, toBeat: Move): Move? {
        return allMoves.filter { MoveComparator.canBeat(it, toBeat) }.minByOrNull { getMoveCardValue(it) }
    }

    private fun selectCostliestMove(allMoves: List<Move>, toBeat: Move): Move? {
        return allMoves.filter { MoveComparator.canBeat(it, toBeat) }.maxByOrNull { getMoveCardValue(it) }
    }

    /**
     * If an opponent has <= this many cards, it's late game
     */
    private const val LATE_GAME_CARDS = 4
}