import guanpai.game.Move
import guanpai.MoveType
import guanpai.strategy.MoveComparator
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import org.junit.Test

class TestMoveComparator {
    @Test
    fun testSimple() {
        val target = Move(listOf("Q"), MoveType.SINGLE)
        val suggestion = Move(listOf("K"), MoveType.SINGLE)

        assertTrue(MoveComparator.canBeat(suggestion, target))
    }

    @Test
    fun testSlightlyMoreComplicated() {
        val target = Move(listOf("J", "J", "Q", "Q"), MoveType.NEIGHBOUR_LADDER)
        val suggestion = Move(listOf("Q", "Q", "K", "K"), MoveType.NEIGHBOUR_LADDER)

        assertTrue(MoveComparator.canBeat(suggestion, target))
    }

    @Test
    fun testCanNotBeat() {
        val target = Move(listOf("4", "5", "6", "7", "8"), MoveType.LADDER)
        val suggestion = Move(listOf("3", "4", "5", "6", "7", "8"), MoveType.LADDER)
        val suggestion2 = Move(listOf("5", "6", "7", "8"), MoveType.LADDER)
        val suggestion3 = Move(listOf("A"), MoveType.SINGLE)

        assertFalse(MoveComparator.canBeat(suggestion, target))
        assertFalse(MoveComparator.canBeat(suggestion2, target))
        assertFalse(MoveComparator.canBeat(suggestion3, target))
    }

    @Test
    fun testBomb() {
        val target = Move(listOf("J", "J", "Q", "Q"), MoveType.NEIGHBOUR_LADDER)
        val suggestion = Move(listOf("A", "A", "A"), MoveType.BOMB)
        val suggestion2 = Move(listOf("K", "K", "K", "K"), MoveType.BOMB)

        assertTrue(MoveComparator.canBeat(suggestion, target))
        assertTrue(MoveComparator.canBeat(suggestion2, target))
    }

    @Test
    fun testBug() {
        val a = Move(listOf("10", "10"), MoveType.DOUBLE)
        val b = Move(listOf("K", "K"), MoveType.DOUBLE)
        assertFalse(MoveComparator.canBeat(a, b))
    }
}