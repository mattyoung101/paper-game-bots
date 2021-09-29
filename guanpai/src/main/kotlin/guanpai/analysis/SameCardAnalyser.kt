package guanpai.analysis

/**
 * Generates pairs or triples of the same card, e.g. (Q,Q) or (Q,Q,Q)
 */
class SameCardAnalyser : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<List<String>> {
        val out = mutableListOf<List<String>>()

        // generate a map of the number of times each card occurs in the hand
        // performance: may be a more optimal method for this
        val occurrences = hashMapOf<String, Int>()
        val uniqueHand = hand.toSet()
        for (card in uniqueHand){
            occurrences[card] = hand.count { it == card }
        }

        for ((card, count) in occurrences){
            if (count % 2 == 0){
                // generate pairs
                // we have a two (we may have more than exactly two cards, but it's divisible by two, so we can divide
                // it up into groups and add those to output)
                val groups = count / 2
                for (i in 0 until groups){
                    // size of group is hardcoded, that's ok
                    out.add(listOf(card, card))
                }
            } else if (count % 3 == 0){
                // generate triples
                val groups = count / 3
                for (i in 0 until groups){
                    out.add(listOf(card, card, card))
                }
                // TODO need to consider +1 or +2 (do we?)
            }
        }

//        for ((i, card) in hand.withIndex()){
//            // copy list and remove the card we are currently considering
//            // speed improvement: it may be possible to remove this copy, and instead exclude it by index below
//            val copy = hand.toMutableList()
//            copy.removeAt(i)
//
//            // now see if we have another card of this type in the hand
//            val other = copy.firstOrNull { it == card }
//            if (other != null){
//                out.add(mutableListOf(card, other))
//            }
//        }
        return out.toList()
    }
}