package guanpai.analysis

import guanpai.MoveType
import guanpai.countCardsToMap
import guanpai.game.Move

object Analysis {
    private val analysers: Array<HandAnalyser> = arrayOf(
        SinglesAnalyser(),
        SameCardAnalyser(),
        LadderAnalyser(),
        BombAnalyser(),
        NeighboursAnalyser()
    )

    /**
     * Applies all the registered [HandAnalyser]s to the [hand] (which may not be sorted)
     */
    fun analyseAll(hand: List<String>): List<Move> {
        val out = mutableListOf<Move>()
        for (analyser in analysers){
            out.addAll(analyser.analyseHand(hand))
        }
        return out
    }

    /**
     * Determines what move this set of cards is
     */
    fun determineMoveType(cards: List<String>): MoveType {
        val count = countCardsToMap(cards)
        for (analyser in analysers) {
            val type = analyser.isMove(count)
            if (type != null) {
                return type
            }
        }
        return MoveType.UNKNOWN
    }
}