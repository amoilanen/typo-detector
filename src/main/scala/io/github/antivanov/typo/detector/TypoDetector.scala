package io.github.antivanov.typo.detector

import LevenshteinDistance.computeDistance

object TypoDetector {

  val DefaultMaxMistypedSymbols = 2

  private def stripPunctuation(str: String): String =
    str.replaceAll("[\\Q,;:.-'\\E]", "")

  private def getWords(str: String): Array[String] =
    stripPunctuation(str).split("\\s+")

  class StringWithTypoDetection(str: String) {

    def isTypoOf(otherStr: String, maxMistypedSymbols: Int = DefaultMaxMistypedSymbols): Boolean =
      TypoDetector.isTypoOf(str, otherStr, maxMistypedSymbols)

    def equalsOrTypoOf(
        otherStr: String,
        maxMistypedSymbols: Int = DefaultMaxMistypedSymbols
    ): Boolean =
      TypoDetector.equalsOrTypoOf(str, otherStr, maxMistypedSymbols)

    def containsExactOrTypoOf(
        strToFind: String,
        maxMistypedSymbols: Int = DefaultMaxMistypedSymbols
    ): Boolean =
      TypoDetector.containsExactOrTypoOf(str, strToFind, maxMistypedSymbols)

    def editDistanceFrom(otherStr: String): Int =
      TypoDetector.editDistanceFrom(str, otherStr)
  }

  object StringWithTypoDetection {

    implicit def stringToString(s: String) = new StringWithTypoDetection(s)
  }

  def isTypoOf(
      str: String,
      otherStr: String,
      maxMistypedSymbols: Int = DefaultMaxMistypedSymbols
  ): Boolean = {
    val distance = computeDistance(str, otherStr)
    distance > 0 && distance <= maxMistypedSymbols
  }

  def equalsOrTypoOf(
      str: String,
      otherStr: String,
      maxMistypedSymbols: Int = DefaultMaxMistypedSymbols
  ): Boolean =
    str.equals(otherStr) || isTypoOf(str, otherStr, maxMistypedSymbols)

  def containsExactOrTypoOf(
      str: String,
      strToFind: String,
      maxMistypedSymbols: Int = DefaultMaxMistypedSymbols
  ): Boolean = {
    val strWords       = getWords(str)
    val strToFindWords = getWords(strToFind)

    val possiblePositions = (0 to (strWords.length - strToFindWords.length))

    val positionOfStrToFind = possiblePositions.find { position: Int =>
      val strWordsAtPosition = strWords.slice(position, position + strToFindWords.length)
      strWordsAtPosition.zip(strToFindWords).forall {
        case (strWord, strToFindWord) =>
          new StringWithTypoDetection(strWord).equalsOrTypoOf(strToFindWord, maxMistypedSymbols)
      }
    }
    positionOfStrToFind.isDefined
  }

  def editDistanceFrom(str: String, otherStr: String): Int =
    computeDistance(str, otherStr)
}
