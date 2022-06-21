package guanpai.analysis

import guanpai.MoveType
import guanpai.game.Move

interface HandAnalyser {
    /**
     * Generates a list of possible moves of this type for the given hand.
     *
     * Note: assume [hand] is _not_ sorted!
     *
     * Also note: analysers are expected to return _all_ possible moves of their type, so the output may not
     * necessarily be unique. For example, in the hand [4,4,4,4] there are two possible pairs of fours.
     * @return list of possible moves that could be played
     */
    fun analyseHand(hand: List<String>): List<Move>

    /**
     * Determines if the move specified is this move
     * @param hand map of card to its count in the hand
     * @return the type of this move if it is a move in this class, otherwise null
     */
    fun isMove(hand: Map<String, Int>): MoveType?
}