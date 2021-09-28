package guanpai.analysis

import guanpai.CARDS
import guanpai.CARD_COMPARATOR

/**
 * Generates counting chains like (4,5,6,7,8). Note that this class returns every valid (i.e. n >= 4) count of cards
 * separately, so it would return a count of 4, then 5, then 6, etc
 */
class CountingAnalyser : HandAnalyser {
    /**
     * Returns true if we can count from [a] to [b] acceptably
     */
    private fun canCount(a: String, b: String): Boolean {
        return CARDS.indexOf(b) - CARDS.indexOf(a) == 1
    }

    private fun scoreCard(card: String): Int {
        return CARDS.indexOf(card)
    }

    private fun getNextCard(current: String, hand: List<String>): String? {
        return hand.firstOrNull { scoreCard(it) > scoreCard(current) }
    }

    override fun analyseHand(hand: List<String>): List<List<String>> {
        val out = mutableListOf<List<String>>()
        val sortedHand = hand.sortedWith(CARD_COMPARATOR)

        for ((i, card) in sortedHand.withIndex()){
            // current stack of cards counted
            val stack = mutableListOf<String>()
            var previousCard = card

            // try to count up from our current card to the end of the hand
            for (j in i + 1 until sortedHand.size){
                val nextCard = sortedHand[j]

                // check if we are allowed to count up to this next card
                // TODO need a new algorithm that loops forever and tries to find a card one above the current
                if (canCount(previousCard, nextCard)){
                    stack.add(nextCard)

                    if (stack.size >= MIN_SIZE && stack !in out){
                        // this in and of itself is a valid count, so add it to output as well
                        out.add(stack.toMutableList())
                    }
                    previousCard = nextCard
                } else {
                    // increment was too high, so our count is finished
                    if (stack.size >= MIN_SIZE && stack !in out) out.add(stack)
                    break
                }
            }
        }

        return out
    }

    companion object {
        /** Minimum size to play a ladder */
        private const val MIN_SIZE = 5
    }
}