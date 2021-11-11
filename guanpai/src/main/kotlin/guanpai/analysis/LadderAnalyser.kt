package guanpai.analysis

import guanpai.CARDS
import guanpai.CARD_COMPARATOR
import guanpai.game.Move
import guanpai.MoveType

/**
 * Generates counting chains like (4,5,6,7,8). Note that this class returns every valid (i.e. n >= 5) ladder of cards
 * separately, so it would return a count of 4, then 5, then 6, etc
 */
class LadderAnalyser : HandAnalyser {
    private fun scoreCard(card: String): Int {
        return CARDS.indexOf(card)
    }

    /** Returns the next card possible in a ladder (must be greater than current card, but only by one) */
    private fun getNextCard(current: String, hand: List<String>): String? {
        return hand.firstOrNull { scoreCard(it) - scoreCard(current) == 1 }
    }

    override fun analyseHand(hand: List<String>): List<Move> {
        // store output as set to enforce unique stacks of cards (this should definitely be a set, I checked)
        val out = mutableSetOf<Move>()
        // performance: may not actually need to sort hand? (with new algorithm)
        val sortedHand = hand.sortedWith(CARD_COMPARATOR)

        for (card in sortedHand){
            // no cards higher than two
            if (card == "2") continue

            // current stack of cards in this ladder
            val stack = mutableListOf<String>()
            stack.add(card)
            var previousCard = card
            while (true) {
                // try and see if we can count to another card
                val nextCard = getNextCard(previousCard, sortedHand)
                if (nextCard == "2" && TWO_BANNED) {
                    break
                }
                if (nextCard != null){
                    // we can keep counting, update and continue
                    stack.add(nextCard)
                    previousCard = nextCard

                    if (stack.size >= MIN_SIZE){
                        // our current stack is actually already a valid ladder, so add it as well
                        out.add(Move(stack.toMutableList(), MoveType.LADDER))
                    }
                } else {
                    // no luck, ladder has to stop here
                    break
                }
            }
            // finished counting now, see if this is a valid count
            if (stack.size >= MIN_SIZE) {
                out.add(Move(stack, MoveType.LADDER))
            }
        } // get next card

        return out.toList()
    }

    companion object {
        /** Minimum size to play a ladder */
        private const val MIN_SIZE = 5
        /** True if 2 is banned from being in the hand (default by rules, yes) */
        private const val TWO_BANNED = true
    }
}