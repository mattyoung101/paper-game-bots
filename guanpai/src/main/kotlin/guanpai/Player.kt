package guanpai

/**
 * Represents a player in the game
 * @param cards num cards remaining for this player
 * @param played list of cards played, where played[0] = first move
 * @param playerId ID of which player this is (0 = AI, 1 = opp. 1, 2 = opp. 2)
 * @param order index at which this opponent should play (0 = first player, 2 = last player)
 */
data class Player(var cards: Int = 16, val played: List<List<String>> = listOf(), val playerId: Int, val order: Int)