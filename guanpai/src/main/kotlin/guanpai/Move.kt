package guanpai

import guanpai.MoveType

/** Represents a potential move in the game */
data class Move(val cards: List<String>, val type: MoveType)
