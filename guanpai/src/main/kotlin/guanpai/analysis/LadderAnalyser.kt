package guanpai.analysis

import guanpai.CARDS
import guanpai.CARD_COMPARATOR

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

    override fun analyseHand(hand: List<String>): List<List<String>> {
        // store output as set to enforce unique stacks of cards
        // TODO check if should be unique
        val out = mutableSetOf<List<String>>()
        // TODO may not actually need to sort hand? (with new algorithm)
        val sortedHand = hand.sortedWith(CARD_COMPARATOR)

        for (card in sortedHand){
            // current stack of cards in this ladder
            val stack = mutableListOf<String>()
            stack.add(card)
            var previousCard = card

            while (true){
                // try and see if we can count to another card
                val nextCard = getNextCard(previousCard, sortedHand)
                if (nextCard != null){
                    // we can keep counting, update and continue
                    stack.add(nextCard)
                    previousCard = nextCard

                    if (stack.size >= MIN_SIZE){
                        // our stack is actually already a valid ladder, so add it as well
                        out.add(stack.toMutableList())
                    }
                } else {
                    // no luck, ladder has to stop here
                    break
                }
            }

            // finished counting now, see if this is a valid count
            if (stack.size >= MIN_SIZE){
                out.add(stack)
            }
        }

        return out.toList()
    }

    companion object {
        /** Minimum size to play a ladder */
        private const val MIN_SIZE = 5
    }
}