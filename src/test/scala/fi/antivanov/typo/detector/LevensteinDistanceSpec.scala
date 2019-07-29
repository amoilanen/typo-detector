package fi.antivanov.typo.detector

import org.scalatest._
import LevensteinDistance._

class LevensteinDistanceSpec extends FreeSpec with Matchers {
  "Levenstein distance" - {
    "one symbol distance - substitution" in {
      computeDistance("cat", "sat") shouldEqual 1
    }

    /*
     * Symbol insertion is dual to symbol deletion:
     *
     * str1 = "ab", str2 = "abc", Levenstein distance is 1
     *
     * str1 requires one symbol addition "c"
     * or
     * str2 requires one symbol deletion "c" (same symbol)
     */
    "one symbol distance - symbol deletion" in {
      computeDistance("catt", "cat") shouldEqual 1
    }

    "two different strings with longer distance" in {
      computeDistance("kitten", "sitting") shouldEqual 3
    }

    "work with longer strings" in {
      computeDistance("Levenstein distance", "Levenstein dictance") shouldEqual 1
    }
  }
}
