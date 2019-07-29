package fi.antivanov.typo.detector

import LevensteinDistance.computeDistance

object TypoDetector {

  object StringWithTypoDetection {

    class StringWithTypoDetection(str: String) {

      def isTypoOf(otherStr: String, maxMistypedSymbols: Int = 2): Boolean = {
        val distance = computeDistance(str, otherStr)
        distance > 0 && distance <= maxMistypedSymbols
      }

      def editDistanceFrom(otherStr: String) =
        computeDistance(str, otherStr)
    }

    implicit def stringToString(s: String) = new StringWithTypoDetection(s)
  }
}
