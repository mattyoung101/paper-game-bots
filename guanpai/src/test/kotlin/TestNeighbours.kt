import guanpai.MoveType
import guanpai.analysis.NeighboursAnalyser
import guanpai.game.Move
import junit.framework.Assert.assertTrue
import org.junit.Test

class TestNeighbours {
    @Test
    fun testQQKKTricky() {
        val hand = "3 5 6 7 8 9 9 J J Q Q Q K A A 2".split(" ")
        val moves = NeighboursAnalyser().analyseHand(hand)

        assertTrue(moves.isNotEmpty())
        val wantedMove = Move(listOf("J", "J", "Q", "Q"), MoveType.NEIGHBOUR_LADDER)
        assertTrue(wantedMove in moves)
    }
}