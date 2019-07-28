package fi.antivanov.typo.detector

import org.scalatest._
import TypoDetector._

class TypoDetectorSpec extends FreeSpec with Matchers {
  "Levenstein distance" - {
    "one symbol distance - substitution" in {
      computeLevensteinDistance("cat", "sat") shouldEqual 1
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
      computeLevensteinDistance("catt", "cat") shouldEqual 1
    }

    "two different strings with longer distance" in {
      computeLevensteinDistance("kitten", "sitting") shouldEqual 3
    }

    "work with longer strings" in {
      computeLevensteinDistance("Levenshtein distance", "Levenshtein dictance") shouldEqual 1
    }
  }
}
