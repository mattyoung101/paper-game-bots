package guanpai.analysis

interface HandAnalyser {
    /**
     * Generates a list of possible moves of this type for the given hand.
     * Note: assume [hand] is _not_ sorted!
     * @return list of possible moves that could be played
     */
    fun analyseHand(hand: List<String>): List<List<String>>
}