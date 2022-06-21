package guanpai.analysis

import guanpai.game.Move
import guanpai.MoveType

/**
 * Generates moves with a single card in them
 */
class SinglesAnalyser : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<Move> {
        return hand.map { Move(listOf(it), MoveType.SINGLE) }
    }

    override fun isMove(hand: Map<String, Int>): MoveType? {
        return if (hand.values.none { it != 1 }) MoveType.SINGLE else null
    }

}