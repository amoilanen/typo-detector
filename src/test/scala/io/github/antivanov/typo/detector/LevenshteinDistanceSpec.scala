package io.github.antivanov.typo.detector

import org.scalatest.freespec.AnyFreeSpecLike
import org.scalatest.matchers.should
import LevenshteinDistance._

class LevenshteinDistanceSpec extends AnyFreeSpecLike with should.Matchers:
  "Levenshtein distance" - {
    "one symbol distance - substitution" in {
      computeDistance("cat", "sat") shouldEqual 1
    }

    /*
     * Symbol insertion is dual to symbol deletion:
     *
     * str1 = "ab", str2 = "abc", Levenshtein distance is 1
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
      computeDistance("Levenshtein distance", "Levenshtein dictance") shouldEqual 1
    }
  }
