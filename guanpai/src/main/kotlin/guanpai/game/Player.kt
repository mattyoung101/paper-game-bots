package guanpai.game

import guanpai.PlayerType

/**
 * Represents a player in the game
 * @param cards num cards remaining for this player
 * @param played list of cards played, where played[0] = first move
 * @param playerId which player this is
 * @param hand full hand, if known, only valid for PlayerType.AI
 */
data class Player(var cards: Int = 16, val played: MutableList<Move> = mutableListOf(), val playerId: PlayerType,
                  val hand: MutableList<String> = mutableListOf())