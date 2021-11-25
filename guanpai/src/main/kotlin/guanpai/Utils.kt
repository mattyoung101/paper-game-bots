package guanpai

import guanpai.game.Move

/** Some moves allow +1 of any card, so consider that here */
fun considerPlusOnes(out: MutableCollection<Move>, move: List<String>, hand: List<String>, moveType: MoveType) {
    // create a copy of the hand without the cards from the move
    val handWithoutMove = hand.toMutableList()
    for (card in move) {
        handWithoutMove.remove(card)
    }

    // loop over all the cards that were not in the move
    for (card in handWithoutMove){
        // copy the move and return a new one with this particular +1
        val newMove = move.toMutableList().apply { add(card) }
        out.add(Move(newMove, moveType))
    }
}

fun considerPlusOnes(out: MutableListIterator<Move>, move: Move, hand: List<String>, moveType: MoveType) {
    // create a copy of the hand without the cards from the move
    val handWithoutMove = hand.toMutableList()
    for (card in move.cards) {
        handWithoutMove.remove(card)
    }

    for (card in handWithoutMove){
        // copy the move and return a new one with this particular +1
        val newMove = move.cards.toMutableList().apply { add(card) }
        out.add(Move(newMove, moveType))
    }
}

fun considerPlusTwos(out: MutableListIterator<Move>, move: Move, hand: List<String>, moveType: MoveType) {
    // create a copy of the hand without the cards from the move
    val handWithoutMove = hand.toMutableList()
    for (card in move.cards) {
        handWithoutMove.remove(card)
    }

    // TODO also check do we actually want to unique moves?
    val uniqueMoves = mutableSetOf<Move>()

    // TODO fix duplicates: Q,Q,Q,K,K,K,2,3 is the same as Q,Q,Q,K,K,K,3,2
    for (card0 in handWithoutMove) {
        val handWithoutCard0 = handWithoutMove.toMutableList().apply { remove(card0) }

        for (card1 in handWithoutCard0) {
            // copy the move and return a new one with this particular +2
            val newMove = move.cards.toMutableList().apply {
                add(card0)
                add(card1)
            }
            uniqueMoves.add(Move(newMove, moveType))
        }
    }

    uniqueMoves.forEach { out.add(it) }
}