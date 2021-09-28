package guanpai.analysis

/**
 * Generates moves with a single card in them
 */
class SinglesAnalyser : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<List<String>> {
        val out = mutableListOf<List<String>>()
        for (card in hand){
            out.add(listOf(card))
        }
        return out
    }

}