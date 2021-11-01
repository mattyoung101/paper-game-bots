package guanpai

/**
 * List of card types in the deck ranked lowest to highest.
 * (note that we don't consider "3S" the starting card here, it's considered separately)
 */
val CARDS = arrayOf("3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A", "2")

/**
 * Compare cards in ascending order
 */
val CARD_COMPARATOR = Comparator<String> { a, b ->
    val aIndex = CARDS.indexOf(a)
    val bIndex = CARDS.indexOf(b)

    if (aIndex < bIndex){
        -1
    } else if (aIndex == bIndex){
        0
    } else {
        1
    }
}

// cards removed from deck: one ace, three twos (= 4 cards) - also no jokers
private val suite = arrayOf("3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")

/**
 * Full Guan Pai deck
 */
val DECK = arrayOf(*suite, *suite, *suite, *suite, "A", "A", "A", "2")

/**
 * Category of move
 */
enum class MoveType {
    SINGLE,
    DOUBLE,
    TRIPLE,
    LADDER,
    // e.g. (Q,Q,K,K)
    NEIGHBOUR_LADDER,
    BOMB,
    PASS,
    UNKNOWN
}

enum class PlayerType {
    AI,
    OPP_1,
    OPP_2
}