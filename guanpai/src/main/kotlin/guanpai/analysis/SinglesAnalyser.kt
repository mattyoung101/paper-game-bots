package guanpai.analysis

/**
 * Generates moves with a single card in them
 */
class SinglesAnalyser : HandAnalyser {
    override fun analyseHand(hand: List<String>): List<List<String>> {
        return hand.map { listOf(it )}
    }

}