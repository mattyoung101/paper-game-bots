import guanpai.MoveType
import guanpai.analysis.NeighboursAnalyser
import guanpai.analysis.SameCardAnalyser
import guanpai.game.Move
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Test

class TestNeighbours {
    @Test
    fun testQQKKTricky() {
        val hand = "3 5 6 7 8 9 9 J J Q Q Q K A A 2".split(" ")
        val moves = NeighboursAnalyser().analyseHand(hand)

        assertTrue(moves.isNotEmpty())
        val wantedMove = Move(listOf("J", "J", "Q", "Q"), MoveType.NEIGHBOUR_LADDER_DOUBLE)
        assertTrue(wantedMove in moves)
    }

    @Test
    fun testQQQKKKPlusOne(){
        val hand = "3 3 4 6 6 7 7 9 10 J Q Q Q K K K".split(" ")
        val moves = NeighboursAnalyser().analyseHand(hand)

        println(moves.joinToString("\n"))

        val wantedMove = Move(listOf("Q", "Q", "Q", "K", "K", "K", "3", "3"), MoveType.NEIGHBOUR_LADDER_TRIPLE)
        assertTrue(wantedMove in moves)
    }

    @Test
    fun testBug1(){
        val hand = "3 4 K 8 Q J 10 7 5 3 J 7 5 K Q Q".split(" ")
        val moves = NeighboursAnalyser().analyseHand(hand)

        println(moves.joinToString("\n"))
        val unwantedMove = Move("J J Q Q K K 3 4".split(" "), MoveType.NEIGHBOUR_LADDER_TRIPLE)
        assertFalse(unwantedMove in moves)
    }
}