package fi.antivanov.typo.detector

object TypoDetector {

  def computeLevensteinDistance(first: String, second: String): Int = {
    val firstLength = first.length
    val secondLength = second.length

    if (firstLength == 0 || secondLength == 0) {
      Seq(firstLength, secondLength).max
    } else {
      val firstPrefix = first dropRight 1
      val firstLastCharacter = first takeRight 1
      val secondPrefix = second dropRight 1
      val secondLastCharacter = second takeRight 1
      val distanceIfFirstLastCharacterRemoved = computeLevensteinDistance(firstPrefix, second) + 1
      val distanceIfSecondLastCharacterIsRemoved = computeLevensteinDistance(first, secondPrefix) + 1
      val distanceIfLastCharactersAreSubstituted = (if (firstLastCharacter == secondLastCharacter) 0 else 1) + computeLevensteinDistance(firstPrefix, secondPrefix)

      List(distanceIfFirstLastCharacterRemoved, distanceIfSecondLastCharacterIsRemoved, distanceIfLastCharactersAreSubstituted).min
    }
  }

}
