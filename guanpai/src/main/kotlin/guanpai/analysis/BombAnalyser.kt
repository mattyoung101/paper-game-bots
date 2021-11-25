package guanpai.analysis

import guanpai.game.Move
import guanpai.MoveType
import guanpai.considerPlusOnes

/**
 * Finds bombs: triple ace, or four of any card. Also, there is a potential +1 for any other card
 * FIXME this is bugged, see: 3 4 6 7 8 8 9 9 9 9 10 J J Q K K
 */
class BombAnalyser : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<Move> {
        val out = mutableListOf<Move>()

        // kinda dumb to hardcode this, but whatever: search for aces (only require 3)
        val aces = hand.filter { it == "A" }
        if (aces.size == 3){
            out.add(Move(aces, MoveType.BOMB))
            considerPlusOnes(out, aces, hand, MoveType.BOMB)
        }

        // now check for 4 of any card
        val handNoAces = hand.filter { it != "A" }
        for (card in handNoAces){
            val matchingCards = handNoAces.filter { it == card }

            // it shouldn't be possible to have > 4, because there are only 4 suites
            if (matchingCards.size == 4){
                // have 4 of the same card, so bomb!
                out.add(Move(matchingCards, MoveType.BOMB))
                considerPlusOnes(out, matchingCards, handNoAces, MoveType.BOMB)
            }
        }

        return out
    }
}