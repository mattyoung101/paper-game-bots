package guanpai

/**
 * @param cards num cards remaining for this opponent
 * @param played list of cards this opponent played, where played[0] = first move
 */
data class Opponent(var cards: Int = 16, val played: List<List<String>> = listOf())