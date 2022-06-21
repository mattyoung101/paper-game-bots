package guanpai.analysis

import guanpai.game.Move
import guanpai.MoveType
import guanpai.considerPlusOnes
import guanpai.countCardsToMap

/**
 * Generates pairs or triples of the same card, e.g. (Q,Q) or (Q,Q,Q)
 * @param shouldConsiderExtra if we should consider +1, +2, etc
 */
class SameCardAnalyser(private val shouldConsiderExtra: Boolean = true) : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<Move> {
        val out = mutableListOf<Move>()
        val occurrences = countCardsToMap(hand)

        @Suppress("KotlinConstantConditions") // seems to be bugged here
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
            }
        }

        // we no longer allow plus one on triples because that is an invalid move apparently
        // according to the experts
//        if (shouldConsiderExtra) {
//            val it = out.listIterator()
//            while (it.hasNext()) {
//                val move = it.next()
//                if (move.type != MoveType.TRIPLE) continue
//                considerPlusOnes(it, move, hand, move.type)
//            }
//        }

        // TODO consider plus two on triples - this is valid

        return out
    }

    private fun checkCount(targetCards: Int, hand: Map<String, Int>): Boolean {
        // in order to be a double/triple only exactly one element in the map can have 2 or 3 cards
        var haveUnique2or3 = false
        for ((_, count) in hand) {
            if (count == targetCards && !haveUnique2or3) {
                // first time seeing a card with 2 or 3, so mark that
                haveUnique2or3 = true
            } else if (count == targetCards) {
                // we've seen a card before that has 2 or 3, and seen it again, so this cannot be just a double/triple
                // (it's probably a ladder)
                return false
            }
        }
        return haveUnique2or3
    }

    override fun isMove(hand: Map<String, Int>): MoveType? {
        val have2 = checkCount(2, hand)
        val have3 = checkCount(3, hand)
        // shouldn't happen but just in case
        if (have2 && have3) {
            return null
        } else if (have2) {
            return MoveType.DOUBLE
        } else if (have3) {
            return MoveType.TRIPLE
        }
        return null
    }
}