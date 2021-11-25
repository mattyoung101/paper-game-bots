package guanpai.strategy

import guanpai.CARD_COMPARATOR
import guanpai.game.Move
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
        // moves must be same number of cards (implies same move as well)
        if (suggestion.cards.size != target.cards.size) return false
        // first card must be higher value
        return CARD_COMPARATOR.compare(suggestion.cards[0], target.cards[0]) > 0
    }
    // TODO fix triple + 1
    // example: Ella plays [8, 8, 8, 7], AI attempts [K, K, A, A]
}