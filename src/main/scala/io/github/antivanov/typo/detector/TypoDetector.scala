package io.github.antivanov.typo.detector

import LevenshteinDistance.computeDistance

/** Includes useful methods to search in text and test whether a string is a typo of another string */
object TypoDetector:

  /** Default maximum number of mistyped symbols allowed for a string to be considered a typo
    *  (number of symbol replacements, omitted or extra symbols)
    */
  val DefaultMaxMistypedSymbols = 2

  private def stripPunctuation(str: String): String =
    str.replaceAll("[\\Q,;:.-'\\E]", "")

  private def getWords(str: String): Array[String] =
    stripPunctuation(str).split("\\s+")

  /** Wrapper for a String to fit it with the utility methods for working with strings with typos.
    *
    * @param str original string to be wrapped
    */
  class TypoAwareString(str: String):

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

  object TypoAwareString:

    implicit def stringToString(s: String): TypoAwareString =
      new TypoAwareString(s)

  /** Determines if str is a mistyped version of otherStr
    *
    * @param str String to test for being a typo
    * @param otherStr original String typed correctly
    * @param maxMistypedSymbols maximum number of mistyped symbols allowed for a string to be considered a typo
    *                             (number of symbol replacements, omitted or extra symbols)
    * @return true if str is a typo of otherStr, false if the strings are too different for str to be considered
    *         a typo, or str is the same as otherStr
    */
  def isTypoOf(
      str: String,
      otherStr: String,
      maxMistypedSymbols: Int = DefaultMaxMistypedSymbols
  ): Boolean =
    val distance = computeDistance(str, otherStr)
    distance > 0 && distance <= maxMistypedSymbols

  /** Determines if str is equal to otherStr or is its typo
    *
    * @param str String to test for equality or being a typo
    * @param otherStr original String typed correctly
    * @param maxMistypedSymbols maximum number of mistyped symbols allowed for a string to be considered a typo
    *                             (number of symbol replacements, omitted or extra symbols)
    * @return true if str is a typo of otherStr or is equal to otherStr, false if the strings are too different for str
    *         to be considered a typo
    */
  def equalsOrTypoOf(
      str: String,
      otherStr: String,
      maxMistypedSymbols: Int = DefaultMaxMistypedSymbols
  ): Boolean =
    str.equals(otherStr) || isTypoOf(str, otherStr, maxMistypedSymbols)

  /** Tries to find wordsToFind inside text. wordsToFind are being searched as exact words occurring in the text or as
    *  their typos with maximum mistyped maxMistypedSymbolsPerWord symbols.
    *
    *  For example:
    *  {{{
    *  containsExactOrTypoOf("Quick bron fox jumps over the lazy dog", "brown fox")
    *  }}}
    *  evaluates to {@code true}
    *
    * @param text text to try to find the words in, might include typos
    * @param wordsToFind words to try to find in the text
    * @param maxMistypedSymbolsPerWord maximum number of mistyped symbols allowed per word when searching
    * @return {@code true} if the words (maybe including typos) can be found in the text
    */
  def containsExactOrTypoOf(
      text: String,
      wordsToFind: String,
      maxMistypedSymbolsPerWord: Int = DefaultMaxMistypedSymbols
  ): Boolean =
    val textWords      = getWords(text)
    val strToFindWords = getWords(wordsToFind)

    val possiblePositions = (0 to (textWords.length - strToFindWords.length))

    val positionOfStrToFind = possiblePositions.find { (position: Int) =>
      val textWordsAtPosition = textWords.slice(position, position + strToFindWords.length)
      textWordsAtPosition.zip(strToFindWords).forall {
        case (textWord, strToFindWord) =>
          new TypoAwareString(textWord).equalsOrTypoOf(strToFindWord, maxMistypedSymbolsPerWord)
      }
    }
    positionOfStrToFind.isDefined

  /** Computes the "edit distance" from str to otherStr: number of symbol substitutions, insertions or deletions to
    * transform str into otherStr. Also known as "Levenshtein distance" {@link https://en.wikipedia.org/wiki/Levenshtein_distance}
    *
    * @param str String to test for equality or being a typo
    * @param otherStr original String typed correctly
    * @return distance between str and otherStr as defined above
    */
  def editDistanceFrom(str: String, otherStr: String): Int =
    computeDistance(str, otherStr)
