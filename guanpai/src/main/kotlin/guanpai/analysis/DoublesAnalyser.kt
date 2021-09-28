package guanpai.analysis

/**
 * Generates doubles like (Q,Q)
 */
class DoublesAnalyser : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<List<String>> {
        val out = mutableListOf<List<String>>()
        for ((i, card) in hand.withIndex()){
            // remove the card we are currently considering
            val copy = hand.toMutableList()
            copy.removeAt(i)

            // now see if we have another card of this in the hand
            val other = copy.firstOrNull { it == card }
            if (other != null){
                out.add(mutableListOf(card, other))
            }
        }
        return out
    }
}