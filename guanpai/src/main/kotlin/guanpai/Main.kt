package guanpai

import guanpai.analysis.Analysis
import guanpai.game.ConsoleGame
import guanpai.game.Move
import guanpai.game.Player
import guanpai.strategy.MonteCarloSimulation
import guanpai.strategy.MoveComparator
import guanpai.strategy.MoveSelector
import java.util.*
import kotlin.system.exitProcess

private const val TEST_MODE = false

fun main(args: Array<String>){
    val scanner = Scanner(System.`in`)
    println("Guan Pai AI - Matt Young, 2021 (autonomous mode)")

    // prompt for initial hand
    print("Enter AI starting hand: ")
    val myCards: List<String>
    if (TEST_MODE) {
        myCards = "Q A 10 J 5 4 2 7 4 5 Q 10 3 8 6 A".split(" ")
    } else {
        myCards = scanner.nextLine().uppercase().split(" ").sortedWith(CARD_COMPARATOR).toMutableList()
        if (myCards.size != 16) {
            System.err.println("Error: Invalid number of cards (you entered: ${myCards.size})")
            exitProcess(1)
        }
    }

    val players: List<Player>
    val aiIndex: Int
    if (TEST_MODE) {
        // fixed order of AI, OPP_2, OPP_1
        players = listOf(Player(playerId = PlayerType.AI), Player(playerId = PlayerType.OPP_2),
            Player(playerId = PlayerType.OPP_1))
        players[0].hand.addAll(myCards)
        aiIndex = 0
    } else {
        // note "3S" doesn't exist in our world, we ask the user to tell us who is playing first
        // TODO make it so we can just type in "AI", "1", "2"
        print("Please input player ordering, using spaces [AI, OPP_1, OPP_2]: ")
        players = scanner.nextLine().uppercase().split(" ").map { Player(playerId = PlayerType.valueOf(it)) }
        aiIndex = players.indexOfFirst { it.playerId == PlayerType.AI }
        players[aiIndex].hand.addAll(myCards)
    }

    // print possible moves
    val possibleMoves = Analysis.analyseAll(players[aiIndex].hand)
    println("Possible moves:")
    for ((i, move) in possibleMoves.withIndex()) {
        println("$i) $move")
    }

    val game = ConsoleGame(players.toMutableList())
    game.playGame()
    println("Thank you for playing with Guan Pai AI. Goodbye!")
}