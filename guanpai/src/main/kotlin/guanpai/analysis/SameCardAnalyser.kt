package guanpai.analysis

import guanpai.game.Move
import guanpai.MoveType
import guanpai.considerPlusOnes

/**
 * Generates pairs or triples of the same card, e.g. (Q,Q) or (Q,Q,Q)
 * @param shouldConsiderExtra if we should consider +1, +2, etc
 */
class SameCardAnalyser(private val shouldConsiderExtra: Boolean = true) : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<Move> {
        val out = mutableListOf<Move>()

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
                    out.add(Move(listOf(card, card), MoveType.DOUBLE))
                }
            } else if (count % 3 == 0){
                // generate triples
                val groups = count / 3
                for (i in 0 until groups){
                    out.add(Move(listOf(card, card, card), MoveType.TRIPLE))
                }
                // TODO consider plus twos
            }
        }

        // consider plus ones on triples
        if (shouldConsiderExtra) {
            val it = out.listIterator()
            while (it.hasNext()) {
                val move = it.next()
                if (move.type != MoveType.TRIPLE) continue
                considerPlusOnes(it, move, hand, move.type)
            }
        }

        // TODO consider plus two on triples

        return out
    }
}