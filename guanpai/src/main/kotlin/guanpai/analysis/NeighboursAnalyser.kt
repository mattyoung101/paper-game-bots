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

    /**
     * Generates a possible ladder with the given card groups (e.g. twos or threes) and adds to the output
     */
    private fun addLadder(groups: List<List<String>>, initial: List<String>, out: MutableList<List<String>>) {
        // ladders must be unique, we will still get correct output if there are duplicate cards in the hand
        // since we check them twice in the function that calls addLadder
        val ladders = mutableSetOf<List<List<String>>>()
        val stack = mutableListOf<List<String>>()
        stack.add(initial)
        var previousGroup = initial

        // use very similar logic to LadderAnalyser here
        while (true){
            // try and see if we can count to another card
            val nextGroup = getNextGroup(previousGroup, groups)
            if (nextGroup != null){
                // we can keep counting, update and continue
                stack.add(nextGroup)
                previousGroup = nextGroup

                if (stack.size >= MIN_SIZE){
                    // our stack is actually already a valid ladder, so add it as well
                    ladders.add(stack.toMutableList())
                }
            } else {
                // no luck, ladder has to stop here
                break
            }
        }

        // finished counting now, see if this is a valid count
        if (stack.size >= MIN_SIZE){
            ladders.add(stack)
        }

        // add to output (have to flatpack first)
        for (item in ladders) {
            out.add(item.flatten())
        }
    }

    override fun analyseHand(hand: List<String>): List<List<String>> {
        // internally we will actually use the SameCardAnalyser to grab a list of pairs/triples
        // performance: it may be possible to cache the output of SameCardAnalyser() instead of running it twice
        val pairsTriples = SameCardAnalyser().analyseHand(hand)
        val pairs = pairsTriples.filter { it.size == 2 }
        val triples = pairsTriples.filter { it.size == 3 }
        val out = mutableListOf<List<String>>()

        for (pair in pairs){
            addLadder(pairs, pair, out)
        }

        for (triple in triples){
            addLadder(triples, triple, out)
        }

        return out
    }

    companion object {
        /** Minimum number of groups to form a valid move (e.g. in this case `[[K, K], [Q,Q]]` */
        private const val MIN_SIZE = 2
    }
}