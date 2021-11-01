package guanpai.strategy

import guanpai.CARD_COMPARATOR
import guanpai.Move
import guanpai.MoveType

/**
 * Calculates if a move can beat another move
 */
object MoveComparator {
    /**
     * Returns true if [suggestion] can beat [target], otherwise false
     * @param suggestion move to check if can beat target
     * @param target move we want to beat
     */
    fun canBeat(suggestion: Move, target: Move): Boolean {
        // bomb can beat anything
        if (suggestion.type == MoveType.BOMB) return true
        // moves must be same number of cards
        if (suggestion.cards.size != target.cards.size) return false
        // first card must be higher value
        return CARD_COMPARATOR.compare(suggestion.cards[0], target.cards[0]) > 0
    }
}