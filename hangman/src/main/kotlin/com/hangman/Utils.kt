package com.hangman

object Utils {
    fun containsAny(str: String, charsToLookFor: HashSet<Char>): Boolean{
        return str.any { it in charsToLookFor }
    }

    // should be able to use .all() but I didn't trust it
    // the word must contain all of the letters from the sequence
    fun containsAll(str: String, charsToLookFor: HashSet<Char>): Boolean {
        return str.toList().containsAll(charsToLookFor)
    }

    /**
     * Check if a dictionary word matches the template we have
     * @param word the original word
     * @param state the state of the board currently
     */
    fun isWordPossible(word: String, state: String): Boolean {
        for ((i, char) in state.withIndex()){
            if (char == '_') continue
            if (!char.equals(word[i], true)){
                return false
            }
        }
        return true
    }
}