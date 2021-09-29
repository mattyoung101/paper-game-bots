package guanpai.analysis

import guanpai.Move
import guanpai.MoveType

/**
 * Finds bombs: triple ace, or four of any card. Also, there is a potential +1 for any other card
 */
class BombAnalyser : HandAnalyser {
    /** For each bomb, we can also add +1 of any card, so consider that here */
    private fun considerPlusOnes(out: MutableList<Move>, move: List<String>, hand: List<String>){
        // create a copy of the hand with the first occurrence of each card in the bomb removed
        // performance: may be a more optimal method than copy and removeAll
        val handWithoutMove = hand.toMutableList()
        handWithoutMove.removeAll(move)

        for (card in handWithoutMove){
            // copy the move and return a new one with this particular +1
            val newMove = move.toMutableList().apply { add(card) }
            out.add(Move(newMove, MoveType.BOMB))
        }
    }

    override fun analyseHand(hand: List<String>): List<Move> {
        val out = mutableListOf<Move>()

        // kinda dumb to hardcode this, but whatever: search for aces (only require 3)
        val aces = hand.filter { it == "A" }
        if (aces.size == 3){
            out.add(Move(aces, MoveType.BOMB))
            considerPlusOnes(out, aces, hand)
        }

        // now check for 4 of any card
        val handNoAces = hand.filter { it != "A" }
        for (card in handNoAces){
            val matchingCards = handNoAces.filter { it == card }

            // it shouldn't be possible to have > 4, because there are only 4 suites
            if (matchingCards.size == 4){
                // have 4 of the same card, so bomb!
                out.add(Move(matchingCards, MoveType.BOMB))
                considerPlusOnes(out, matchingCards, handNoAces)
            }
        }

        return out
    }
}