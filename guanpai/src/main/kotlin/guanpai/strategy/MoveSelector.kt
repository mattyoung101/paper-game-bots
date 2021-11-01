package guanpai.strategy

import guanpai.Move

/**
 * Used in Monte-Carlo simulation and for selecting opening move
 */
object MoveSelector {
    /**
     * Select the move with the most number of cards that does not include ace or two
     */
    fun selectOpeningMove(possibleMoves: List<Move>): Move {
        return possibleMoves.filter { !("A" in it.cards || "2" in it.cards) }.maxByOrNull { it.cards.size }!!
    }
}