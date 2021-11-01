package guanpai

/**
 * Represents a player in the game
 * @param cards num cards remaining for this player
 * @param played list of cards played, where played[0] = first move
 * @param playerId which player this is
 */
data class Player(var cards: Int = 16, val played: MutableList<Move> = mutableListOf(), val playerId: PlayerType)