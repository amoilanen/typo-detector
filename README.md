# typo-detector

[![Build Status](https://travis-ci.org/antivanov/typo-detector.svg?branch=master)](https://travis-ci.org/antivanov/typo-detector)

Typo detector library in Scala.

Typical use case: a certain input needs to be detected, however it might be misspelled in
a variety of ways. Instead of trying to hardcode the possible misspellings of
such an input `typo-detector` might be used instead.

## Usage

### Add dependency

#### Maven

```
<dependency>
  <groupId>io.github.antivanov</groupId>
  <artifactId>typo-detector</artifactId>
  <version>0.1.1</version>
</dependency>
```

#### SBT

```
libraryDependencies += "io.github.antivanov" % "typo-detector" % "0.1.1"
```

### Code examples

#### Basic usage

Enhancing the `String` class with `isTypoOf` method

```scala
import io.github.antivanov.typo.detector.TypoDetector.StringWithTypoDetection._

val acknowlegmentText = "acknowlege"
val isAcknowledged = acknowlegmentText.isTypoOf("acknowledge")
```

Using API object `TypoDetector` if using implicits is not desirable

```scala
import io.github.antivanov.typo.detector.TypoDetector

val acknowlegmentText = "acknowlege"
val isAcknowledged = TypoDetector.isTypoOf(acknowlegmentText, "acknowledge")
```

#### Specifying how many wrong symbols is considered a typo

By default the number of wrongly typed symbols for a string to be considered a typo
is `2`, in the case of longer strings it might make sense to allow for more mistyped symbols
to detect typos, for example:

Enhancing the `String` class with `isTypoOf` method

```scala
import io.github.antivanov.typo.detector.TypoDetector.StringWithTypoDetection._

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
import io.github.antivanov.typo.detector.TypoDetector.StringWithTypoDetection._

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