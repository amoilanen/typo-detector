package io.github.antivanov.typo.detector

import LevenshteinDistance.computeDistance

object TypoDetector {

  val DefaultMaxMistypedSymbols = 2

  class StringWithTypoDetection(str: String) {

    def isTypoOf(otherStr: String, maxMistypedSymbols: Int = DefaultMaxMistypedSymbols): Boolean = {
      val distance = computeDistance(str, otherStr)
      distance > 0 && distance <= maxMistypedSymbols
    }

    def editDistanceFrom(otherStr: String): Int =
      computeDistance(str, otherStr)
  }

  object StringWithTypoDetection {

    implicit def stringToString(s: String) = new StringWithTypoDetection(s)
  }

  def isTypoOf(str: String, otherStr: String, maxMistypedSymbols: Int = DefaultMaxMistypedSymbols): Boolean =
    new StringWithTypoDetection(str).isTypoOf(otherStr, maxMistypedSymbols)

  def editDistanceFrom(str: String, otherStr: String): Int =
    new StringWithTypoDetection(str).editDistanceFrom(otherStr)
}
