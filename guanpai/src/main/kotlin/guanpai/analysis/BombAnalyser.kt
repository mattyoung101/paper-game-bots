package guanpai.analysis

/**
 * Finds bombs: triple ace, or four of any card. Also potential +1 of any other card
 */
class BombAnalyser : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<List<String>> {
        val out = mutableListOf<List<String>>()

        // kinda dumb to hardcode this, but whatever
        val aces = hand.filter { it == "A" }
        if (aces.size == 3){
            out.add(listOf("A", "A", "A"))
        }

        // now check for 4 of any card
        val handNoAces = hand.filter { it != "A " }
        for (card in handNoAces){
            val matchingCards = handNoAces.filter { it == card }

            // it shouldn't be possible to have > 4, because there are only 4 suites
            if (matchingCards.size == 4){
                // we have a bomb!
                out.add(matchingCards)

                // also consider our plus ones here
                val handWithoutMove = handNoAces.toMutableList()
                // create a copy of the hand with the first occurrence of each card in the bomb removed
                handWithoutMove.removeAll(matchingCards)
                for (extra in handWithoutMove){
                    val newMove = matchingCards.toMutableList()
                    newMove.add(extra)
                    out.add(newMove)
                }
            }
        }

        return out
    }
}