package guanpai

import guanpai.analysis.Analysis
import guanpai.strategy.MoveEvaluator
import java.util.*

fun main(args: Array<String>){
    val scanner = Scanner(System.`in`)
    println("Guan Pai AI - Matt Young, 2021")

    // prompt for initial hand
    print("Enter AI starting hand: ")
    val myCards = scanner.nextLine().split(" ")

    // player 0 = AI, player 1 = opponent 1, player 2 = opponent 2
    // note "3S" doesn't exist in our world, we ask the user to tell us who is playing first
    print("Please input player ordering [0 = AI; 1 = opp. 1, 2 = opp. 2]: ")
    val playerOrdering = scanner.nextLine().split(" ").map { it.toInt() }
    val opponents = listOf(
        Player(playerId = 1, order = playerOrdering.indexOf(1)),
        Player(playerId = 2, order = playerOrdering.indexOf(2))
    )
    val selfPlayer = Player(playerId = 0, order = playerOrdering.indexOf(0))
    println("Opponents: $opponents\nSelf: $selfPlayer")
}