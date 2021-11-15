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

private const val TEST_MODE = true

fun main(args: Array<String>){
    val scanner = Scanner(System.`in`)
    println("Guan Pai AI - Matt Young, 2021 (autonomous mode)")

    // prompt for initial hand
    print("Enter AI starting hand: ")
    val myCards: List<String>
    if (TEST_MODE) {
        myCards = "3 3 5 6 6 7 7 8 8 9 10 J Q K A A".split(" ")
    } else {
        myCards = scanner.nextLine().uppercase().split(" ").toMutableList()
        if (myCards.size != 16) {
            System.err.println("Error: Invalid number of cards (you entered: ${myCards.size})")
            exitProcess(1)
        }
    }

    val players: List<Player>
    val aiIndex: Int
    if (TEST_MODE) {
        players = listOf(Player(playerId = PlayerType.OPP_1), Player(playerId = PlayerType.AI),
            Player(playerId = PlayerType.OPP_2))
        players[1].hand.addAll(myCards)
    } else {
        // note "3S" doesn't exist in our world, we ask the user to tell us who is playing first
        print("Please input player ordering, using spaces [AI, OPP_1, OPP_2]: ")
        players = scanner.nextLine().uppercase().split(" ").map { Player(playerId = PlayerType.valueOf(it)) }
        aiIndex = players.indexOfFirst { it.playerId == PlayerType.AI }
        players[aiIndex].hand.addAll(myCards)
    }

    val game = ConsoleGame(players)
    game.playGame()
    println("Thank you for playing with Guan Pai AI. Goodbye!")
}