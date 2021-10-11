package com.hangman

import java.nio.file.Files
import java.nio.file.Paths

object Dictionary {
    val dict = Files.readAllLines(Paths.get("words_alpha.txt")).toHashSet().map {
        it.toUpperCase()
    }

    init {
        println("Dictionary contains ${dict.size} items.")
    }
}