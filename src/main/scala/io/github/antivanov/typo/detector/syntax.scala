package io.github.antivanov.typo.detector

import TypoDetector.*

/** Extension methods for a String to fit it with the utility methods for working with strings with typos.
  */
object syntax:

  implicit class TypoAwareStringSyntax(val str: String) extends AnyVal:

    /** Determines if str is a mistyped version of otherStr
      *
      * @param otherStr original String typed correctly
      * @param maxMistypedSymbols maximum number of mistyped symbols allowed for a string to be considered a typo
      *                             (number of symbol replacements, omitted or extra symbols)
      * @return true if str is a typo of otherStr, false if the strings are too different for str to be considered
      *         a typo, or str is the same as otherStr
      */
    def isTypoOf(otherStr: String, maxMistypedSymbols: Int = DefaultMaxMistypedSymbols): Boolean =
      TypoDetector.isTypoOf(str, otherStr, maxMistypedSymbols)

    /** Determines if str is equal to otherStr or is its typo
      *
      * @param otherStr original String typed correctly
      * @param maxMistypedSymbols maximum number of mistyped symbols allowed for a string to be considered a typo
      *                             (number of symbol replacements, omitted or extra symbols)
      * @return true if str is a typo of otherStr or is equal to otherStr, false if the strings are too different for str
      *         to be considered a typo
      */
    def equalsOrTypoOf(
        otherStr: String,
        maxMistypedSymbols: Int = DefaultMaxMistypedSymbols
    ): Boolean =
      TypoDetector.equalsOrTypoOf(str, otherStr, maxMistypedSymbols)

    /** Tries to find wordsToFind inside text. wordsToFind are being searched as exact words occurring in the text or as
      *  their typos with maximum mistyped maxMistypedSymbolsPerWord symbols.
      *
      *  For example:
      *  {{{
      *  "Quick bron fox jumps over the lazy dog".containsExactOrTypoOf("brown fox")
      *  }}}
      *  evaluates to {@code true}
      *
      * @param wordsToFind words to try to find in the text
      * @param maxMistypedSymbolsPerWord maximum number of mistyped symbols allowed per word when searching
      * @return {@code true} if the words (maybe including typos) can be found in the text
      */
    def containsExactOrTypoOf(
        wordsToFind: String,
        maxMistypedSymbolsPerWord: Int = DefaultMaxMistypedSymbols
    ): Boolean =
      TypoDetector.containsExactOrTypoOf(str, wordsToFind, maxMistypedSymbolsPerWord)

    /** Computes the "edit distance" from str to otherStr: number of symbol substitutions, insertions or deletions to
      * transform str into otherStr. Also known as "Levenshtein distance" {@link https://en.wikipedia.org/wiki/Levenshtein_distance}
      *
      * @param otherStr original String typed correctly
      * @return distance between str and otherStr as defined above
      */
    def editDistanceFrom(otherStr: String): Int =
      TypoDetector.editDistanceFrom(str, otherStr)
