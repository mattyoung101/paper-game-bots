package guanpai.analysis

import guanpai.Move
import guanpai.MoveType

/**
 * Generates moves with a single card in them
 */
class SinglesAnalyser : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<Move> {
        return hand.map { Move(listOf(it), MoveType.SINGLE) }
    }

}