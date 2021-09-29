package guanpai.analysis

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
    fun analyseAll(hand: List<String>): List<List<String>> {
        val out = mutableListOf<List<String>>()
        for (analyser in analysers){
            out.addAll(analyser.analyseHand(hand))
        }
        return out
    }
}