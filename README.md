# typo-detector

[![Build Status](https://travis-ci.org/antivanov/typo-detector.svg?branch=master)](https://travis-ci.org/antivanov/typo-detector)

Typo detector library in Scala.

Typical use case: certain words need to be found in a text, however they might be misspelled in
a variety of ways. Instead of trying to hardcode the possible misspellings of
such an input `typo-detector` might be used instead.

## Usage

### Add dependency

#### Maven

```
<dependency>
  <groupId>io.github.antivanov</groupId>
  <artifactId>typo-detector_2.13</artifactId>
  <version>0.2.0</version>
</dependency>
```

#### SBT

```scala
libraryDependencies += "io.github.antivanov" % "typo-detector_2.13" % "0.2.0"
```

for Scala 2.13.0

```scala
libraryDependencies += "io.github.antivanov" % "typo-detector_2.12" % "0.2.0"
```

for Scala 2.12.8

### Code examples

#### Searching for (maybe mistyped) words in a text

Enhancing the `String` class with `containsExactOrTypoOf` method

```scala
import io.github.antivanov.typo.detector.TypoDetector.TypoAwareString._

"Quick bron fox jumps over the lazy dog".containsExactOrTypoOf("brown fox")
```

Using API object `TypoDetector` if using implicits is not desirable

```scala
import io.github.antivanov.typo.detector.TypoDetector

TypoDetector.containsExactOrTypoOf("Quick bron fox jumps over the lazy dog", "brown fox")
```

#### Detecting if a string is a typo of another string

Enhancing the `String` class with `isTypoOf` method

```scala
import io.github.antivanov.typo.detector.TypoDetector.TypoAwareString._

val acknowlegmentText = "acknowlege"
val isAcknowledged = acknowlegmentText.isTypoOf("acknowledge")
```

Using API object `TypoDetector` if using implicits is not desirable

```scala
import io.github.antivanov.typo.detector.TypoDetector

val acknowlegmentText = "acknowlege"
val isAcknowledged = TypoDetector.isTypoOf(acknowlegmentText, "acknowledge")
```

#### Detecting if another string is equal to current string or is its typo

`equalsOrTypoOf` is very similar in usage to `isTypoOf`, but returns true if another
string is either a typo or is equal to the current string

#### Specifying how many wrong symbols is considered a typo

By default the number of wrongly typed symbols for a string to be considered a typo
is `2`, in the case of longer strings it might make sense to allow for more mistyped symbols
to detect typos, for example:

Enhancing the `String` class with `isTypoOf` method

```scala
import io.github.antivanov.typo.detector.TypoDetector.TypoAwareString._

val misspelledWord = "gementeradverkiezingen"
val isMisspelled = misspelledWord.isTypoOf("gemeenteraadsverkiezingen", maxMistypedSymbols = 5)
```

Using API object `TypoDetector` if using implicits is not desirable

```scala
import io.github.antivanov.typo.detector.TypoDetector

val misspelledWord = "gementeradverkiezingen"
val isMisspelled = TypoDetector.isTypoOf(misspelledWord, "gemeenteraadsverkiezingen", maxMistypedSymbols = 5)
```

#### Computing the edit distance between the strings

`editDistanceFrom` computes the [Levenshtein distance](https://en.wikipedia.org/wiki/Levenshtein_distance) between the strings 

Enhancing the `String` class with `editDistanceFrom` method

```scala
import io.github.antivanov.typo.detector.TypoDetector.TypoAwareString._

val acknowlegmentText = "acknowlege"
val distance = acknowlegmentText.editDistanceFrom("acknowledge")
```

Using API object `TypoDetector` if using implicits is not desirable

```scala
import io.github.antivanov.typo.detector.TypoDetector

val acknowlegmentText = "acknowlege"
val distance = TypoDetector.editDistanceFrom(acknowlegmentText, "acknowledge")
```

## How it works

Under the hood the library computes [the Levenshtein distance](https://en.wikipedia.org/wiki/Levenshtein_distance) between strings using 
[the Wagner-Fischer algorithm](https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm). If the distance is lower than a certain number, the two strings
are considered to be a typo.
