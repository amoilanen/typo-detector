package fi.antivanov.typo.detector

object TypoDetector {

  def computeLevensteinDistance(first: String, second: String): Int = {
    /*
     * Using the Wagner-Fischer algorithm, more efficient than the recursive definition
     * https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm
     */
    val firstLength = first.length
    val secondLength = second.length
    val distances = Array.ofDim[Int](firstLength + 1,secondLength + 1)

    distances(0)(0) = 0
    (1 to firstLength).foreach { rowIndex =>
      distances(rowIndex)(0) = rowIndex
    }
    (1 to secondLength).foreach { columnIndex =>
      distances(0)(columnIndex) = columnIndex
    }

    for {
      rowIndex <- (1 to firstLength)
      columnIndex <- (1 to secondLength)
    } yield {
      val substitutionCount = if (first(rowIndex - 1) == second(columnIndex - 1)) 0 else 1

      val deletionCost = distances(rowIndex - 1)(columnIndex) + 1
      val insertionCost = distances(rowIndex)(columnIndex - 1) + 1
      val substitutionCost = distances(rowIndex - 1)(columnIndex - 1) + substitutionCount

      distances(rowIndex)(columnIndex) = Seq(deletionCost, insertionCost, substitutionCost).min
    }

    distances(firstLength)(secondLength)
  }

}
