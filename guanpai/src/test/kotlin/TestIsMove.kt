import guanpai.MoveType
import guanpai.analysis.*
import guanpai.countCardsToMap
import org.junit.Assert.*
import org.junit.Test

class TestIsMove {
    @Test
    fun testIsSingle() {
        val single = countCardsToMap(listOf("Q"))
        val notSingle = countCardsToMap(listOf("Q", "Q"))

        assertEquals(MoveType.SINGLE, SinglesAnalyser().isMove(single))
        assertNull(SinglesAnalyser().isMove(notSingle))
    }

    @Test
    fun testIsBomb() {
        val bombAces = countCardsToMap("A A A".split(" "))
        val bombFourCards = countCardsToMap("4 4 4 4".split(" "))
        val bombFourCardsPlusOne = countCardsToMap("4 4 4 4 5".split(" "))
        val notBomb = countCardsToMap("3 3 3 4 4 4".split(" "))

        assertEquals(MoveType.BOMB, BombAnalyser().isMove(bombAces))
        assertEquals(MoveType.BOMB, BombAnalyser().isMove(bombFourCards))
        assertEquals(MoveType.BOMB, BombAnalyser().isMove(bombFourCardsPlusOne))
        assertNull(BombAnalyser().isMove(notBomb))
    }

    @Test
    fun testDouble() {
        val double = countCardsToMap("Q Q".split(" "))
        val notDouble = countCardsToMap("Q Q K K".split(" "))

        assertEquals(MoveType.DOUBLE, SameCardAnalyser().isMove(double))
        assertNull(SameCardAnalyser().isMove(notDouble))
    }

    @Test
    fun testTriple() {
        val triple = countCardsToMap("Q Q Q".split(" "))
        val triplePlusOne = countCardsToMap("Q Q Q 3".split(" "))
        val notTriple = countCardsToMap("Q Q Q K K K".split(" "))

        assertEquals(MoveType.TRIPLE, SameCardAnalyser().isMove(triple))
        assertEquals(MoveType.TRIPLE, SameCardAnalyser().isMove(triplePlusOne))
        assertNull(SameCardAnalyser().isMove(notTriple))
    }

    @Test
    fun funTestLadder() {
        val ladder = countCardsToMap("3 4 5 6 7 8 9 10 J Q K".split(" "))
        val notLadder = countCardsToMap("3 4 5".split(" "))
        val notLadder2 = countCardsToMap("3 3 4 4".split(" "))

        assertEquals(MoveType.LADDER, LadderAnalyser().isMove(ladder))
        assertNull(LadderAnalyser().isMove(notLadder))
        assertNull(LadderAnalyser().isMove(notLadder2))
    }

    @Test
    fun testDetermineMoveType() {
        val triple = "Q Q Q 4".split(" ")
        assertEquals(MoveType.TRIPLE, Analysis.determineMoveType(triple))
    }
}