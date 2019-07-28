package fi.antivanov.typo.detector

import org.scalatest._
import TypoDetector._

class TypoDetectorSpec extends FreeSpec with Matchers {
  "Levenstein distance" - {
    "two different strings" in {
      computeLevensteinDistance("kitten", "sitting") shouldEqual 3
    }
  }
}
