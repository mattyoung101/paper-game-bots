package guanpai.strategy

import guanpai.CARDS
import guanpai.Move
import guanpai.Player
import kotlin.math.max

object MoveEvaluator {
    /**
     * Returns the total card value of the move
     */
    private fun getMoveCardValue(move: Move): Double {
        // we consider cards 3-10 as worth the same amount (1.0) TODO is this a good idea?
        return move.cards.sumOf { max(1, CARDS.indexOf(it) - CARDS.indexOf("10")) }.toDouble()
    }

    /**
     * Evaluates the specified [move], returning a score (higher is more ideal to play). This is only a heuristic,
     * it is not a perfectly valid score of which to play.
     * @param turns number of turns we have played
     * @param opponents opponents info
     */
    fun evaluateMove(move: Move, turns: Int, opponents: List<Player>): Double {
        // what we want to do is: minimise value of cards, while maximising number of cards
        // this is an ill-defined problem: https://cs.stackexchange.com/a/52081
        // unless we use multi-objective optimisation, the best approach is to do f(x,y) = ... where x is num cards
        // and y is total value (so a 3D function)
        val value = getMoveCardValue(move)

        // early game: prioritise getting out biggest stack possible that is not a bomb, also no 2 allowed
        // mid game: try to get out big stacks of lower-valued cards, king is ok too, preferably no 2
        // late game: if an opponent is low on cards, spam out high cards, otherwise try to dump low value cards
        // to decide which phase we are in: number of cards opponent with lowest number has
//        when (opponents.minOf { it.cards }) {
//            in MID_GAME_CARDS..16 -> {
//                // early game
//            }
//            in LATE_GAME_CARDS until MID_GAME_CARDS -> {
//                // mid game
//            }
//            else -> {
//                // late game
//            }
//        }

        return value
    }

    /**
     * 16 <= cards <= MID_GAME_CARDS -> early game
     *
     * MID_GAME_CARDS < cards <= LATE_GAME_CARDS -> mid game
     *
     * LATE_GAME_CARDS < cards <= 0 -> late game
     */
    private const val MID_GAME_CARDS = 8
    private const val LATE_GAME_CARDS = 4
}