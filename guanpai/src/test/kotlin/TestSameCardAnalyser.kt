import guanpai.MoveType
import guanpai.analysis.Analysis
import guanpai.analysis.NeighboursAnalyser
import guanpai.analysis.SameCardAnalyser
import guanpai.game.Move
import org.junit.Assert.assertTrue
import org.junit.Test

class TestSameCardAnalyser {
    @Test
    fun testQQQTriple() {
        val hand = "3 3 4 6 6 7 7 9 10 J Q Q Q K K A".split(" ")
        val moves = SameCardAnalyser().analyseHand(hand)

        val wantedMove = Move(listOf("Q", "Q", "Q"), MoveType.TRIPLE)
        assertTrue(wantedMove in moves)
    }

    @Test
    fun testKKDouble() {
        val hand = "3 3 4 6 6 7 7 9 10 J Q Q Q K K A".split(" ")
        val moves = SameCardAnalyser().analyseHand(hand)

        val wantedMove = Move(listOf("K", "K"), MoveType.DOUBLE)
        assertTrue(wantedMove in moves)
    }

    @Test
    fun testQQQPlusOne(){
        val hand = "3 3 4 6 6 7 7 9 10 J Q Q Q K K A".split(" ")
        val moves = SameCardAnalyser().analyseHand(hand)

        val wantedMove = Move(listOf("Q", "Q", "Q", "3"), MoveType.TRIPLE)
        assertTrue(wantedMove in moves)
    }
}