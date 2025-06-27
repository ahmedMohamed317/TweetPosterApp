package com.example.core.util

import android.icu.text.BreakIterator

fun countGraphemes(text: String): Int {
    val iterator = BreakIterator.getCharacterInstance()
    iterator.setText(text)
    var count = 0
    while (true) {
        val end = iterator.next()
        if (end == BreakIterator.DONE) break
        count++
    }
    return count
}
