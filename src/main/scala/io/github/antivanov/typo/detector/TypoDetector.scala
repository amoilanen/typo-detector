package io.github.antivanov.typo.detector

import LevensteinDistance.computeDistance

object TypoDetector {

  class StringWithTypoDetection(str: String) {

    def isTypoOf(otherStr: String, maxMistypedSymbols: Int = 2): Boolean = {
      val distance = computeDistance(str, otherStr)
      distance > 0 && distance <= maxMistypedSymbols
    }

    def editDistanceFrom(otherStr: String): Int =
      computeDistance(str, otherStr)
  }

  object StringWithTypoDetection {

    implicit def stringToString(s: String) = new StringWithTypoDetection(s)
  }

  def isTypoOf(str: String, otherStr: String): Boolean =
    new StringWithTypoDetection(str).isTypoOf(otherStr)

  def editDistanceFrom(str: String, otherStr: String): Int =
    new StringWithTypoDetection(str).editDistanceFrom(otherStr)
}
