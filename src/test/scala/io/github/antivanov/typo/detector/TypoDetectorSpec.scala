package io.github.antivanov.typo.detector

import org.scalatest._

class TypoDetectorSpec extends FreeSpec with Matchers {

  import TypoDetector.StringWithTypoDetection._

  "isTypoOf" - {

    "detects common typos" in {
      "annualy".isTypoOf("annually") shouldBe true
      "buisness".isTypoOf("business") shouldBe true
      "pronunciation".isTypoOf("pronounciation") shouldBe true
    }

    "differs by two symbols => typo" in {
      "Achilles".isTypoOf("Achilez") shouldBe true
    }

    "differs by one symbol => typo" in {
      "Achilles".isTypoOf("Achiles") shouldBe true
    }

    "identical => not a typo" in {
      "Achilles".isTypoOf("Achilles") shouldBe false
    }

    "differs by three symbols => not a typo, too many different symbols" in {
      "Achilles".isTypoOf("Atrides") shouldBe false
    }

    "allows to specify max number of different symbols" in {
      "Achiles".isTypoOf("Achilles", maxMistypedSymbols = 5) shouldBe true
    }
  }

  "equalsOrTypo" - {

    "recognize a typo" in {
      "Achilles".equalsOrTypoOf("Achillez") shouldBe true
    }

    "recognize exact same word" in {
      "Achilles".equalsOrTypoOf("Achilles") shouldBe true
    }

    "recognize different words, not a typo" in {
      "Achilles".equalsOrTypoOf("Hector") shouldBe false
    }
  }

  "containsExactOrTypoOf" - {

    /*
     * "The Iliad" Book I "The contention of Achilles and Agamemnon"
     * http://www.gutenberg.org/cache/epub/6130/pg6130.txt
     */
    val text =
      """
        |  Achilles' wrath, to Greece the direful spring
        |  Of woes unnumbered, heavenly goddess, sing!
        |  That wrath which hurled to Pluto's gloomy reign
        |  The souls of mighty chiefs untimely slain;
        |  Whose limbs unburied on the naked shore,
        |  Devouring dogs and hungry vultures tore.
        |  Since great Achilles and Atrides strove,
        |  Such was the sovereign doom, and such the will of Jove!
        |""".stripMargin

    "recognize exact string" in {
      "wrath".containsExactOrTypoOf("wrath") shouldBe true
    }

    "recognize exact contained string" in {
      text.containsExactOrTypoOf("wrath") shouldBe true
    }

    "recognize typo" in {
      "greet".containsExactOrTypoOf("great") shouldBe true
    }

    "recognize contained typo" in {
      text.replaceFirst("great", "greet").containsExactOrTypoOf("great") shouldBe true
    }

    "recognize typo if string consists of several words" in {
      text
        .replaceFirst("great", "greet")
        .replaceAll("Achilles", "Achilez")
        .containsExactOrTypoOf("great Achilles") shouldBe true
    }

    "return false if not contained" in {
      text.containsExactOrTypoOf("Priam") shouldBe false
    }
  }
}
