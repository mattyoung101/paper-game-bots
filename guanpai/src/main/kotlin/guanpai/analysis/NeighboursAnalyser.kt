package guanpai.analysis

import guanpai.CARDS

/**
 * Generates neighbouring pairs or triples, such as (Q,Q,K,K) or (Q,Q,Q,K,K,K)
 * Also considers pair/triple ladders like: (Q,Q,K,K,A,A)
 */
class NeighboursAnalyser : HandAnalyser {
    /** Score a group of cards, assuming all cards in the group are of the same type */
    private fun scoreGroup(group: List<String>): Int {
        return CARDS.indexOf(group[0])
    }

    /**
    * Returns the next possible card group in a neighbouring group ladder thing
    * (must be greater than current card group, but only by one)
    */
    private fun getNextGroup(current: List<String>, groups: List<List<String>>): List<String>? {
        return groups.firstOrNull { scoreGroup(it) - scoreGroup(current) == 1 }
    }

    /** Generates a possible ladder with the given card groups of the given size */
    private fun getLadder(groups: List<List<String>>, initial: List<String>, size: Int){
        val stack = mutableListOf<List<String>>()
        stack.add(initial)
        var previousCard = initial

        // use very similar logic to LadderAnalyser
//        while (true){
//            // try and see if we can count to another card
//            val nextGroup = getNextGroup(previousCard, groups)
//            if (nextGroup != null){
//                // we can keep counting, update and continue
//                stack.add(nextCard)
//                previousCard = nextCard
//
//                if (stack.size >= LadderAnalyser.MIN_SIZE){
//                    // our stack is actually already a valid ladder, so add it as well
//                    out.add(stack.toMutableList())
//                }
//            } else {
//                // no luck, ladder has to stop here
//                break
//            }
//        }
//
//        // finished counting now, see if this is a valid count
//        if (stack.size >= LadderAnalyser.MIN_SIZE){
//            out.add(stack)
//        }
    }

    override fun analyseHand(hand: List<String>): List<List<String>> {
        // internally we will actually use the SameCardAnalyser to grab a list of pairs/triples
        // performance: it may be possible to cache the output of SameCardAnalyser() instead of running it twice
        val pairsTriples = SameCardAnalyser().analyseHand(hand)
        val pairs = pairsTriples.filter { it.size == 2 }
        val triples = pairsTriples.filter { it.size == 3 }

        for (pair in pairs){
            // try to get a ladder
        }

        for (triple in triples){
            // try to get a ladder
        }

        return listOf()
    }
}