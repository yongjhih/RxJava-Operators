# Rxjava Operators

[![JitPack](https://img.shields.io/github/tag/yongjhih/RxJava-Operators.svg?label=JitPack)](https://jitpack.io/#yongjhih/RxJava-Operators)
<!--[![Build Status](https://travis-ci.org/yongjhih/RxJava-Operators.svg)](https://travis-ci.org/yongjhih/RxJava-Operators)-->

## Usage

* OperatorFlatList
* OperatorFrequency
* OperatorGroupByGroup
* OperatorReverse
* OperatorShuffle
* OperatorToReversedList
* OperatorToShuffledList

### OperatorToReversedList/OperatorReverse

Before:

```java
Observable.range(1, 10).toList().doOnNext(list -> Collections.reverse(list))
    .subscribe(System.out::println);
// [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
```

After:

```java
Observable.range(1, 10).toList().lift(new OperatorToReversedList())
    .subscribe(System.out::println);
// [10, 9, 8, 7, 6, 5, 4, 3, 2, 1]
```

or

```java
Observable.range(1, 10).lift(new OperatorToReverse())
    .subscribe(System.out::println);
// 10
// 9
// 8
// 7
// 6
// 5
// 4
// 3
// 2
// 1
```

## OperatorFrequency

Before:

```java
Observable.range(1, 10).zipWith(Observable.interval(1, TimeUnit.SECONDS), (i, t) -> i)
    .subscribe(i -> System.out.println(i + ": " + System.currentTimeMillis()));
```

After:

```java
Observable.range(1, 10).lift(new OperatorFrequency(1, TimeUnit.SECONDS))
    .subscribe(i -> System.out.println(i + ": " + System.currentTimeMillis()));

// 1: 1428053481338
// 2: 1428053482339
// 3: 1428053483338
// 4: 1428053474339
// 5: 1428053475338
// 6: 1428053476338
// 7: 1428053477338
// 8: 1428053478338
// 9: 1428053479338
// 10: 1428053480338
```

### OperatorFlatList

```java
Observable.range(1, 10).toList().lift(new OperatorFlatList())
    .subscribe(System.out::println);
```

### OperatorToShuffledList/OperatorShuffle

```java
Observable.range(1, 10).toList().lift(new OperatorToShuffledList())
    .subscribe(System.out::println);
```

or

```java
Observable.range(1, 10).lift(new OperatorShuffle())
    .subscribe(System.out::println);
```

## Installation

```gradle
repositories {
    jcenter()
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.yongjhih:RxJava-Operators:-SNAPSHOT'
}
```

## Test

```
./gradlew test
```

## License

Apache 2.0 2014 8tory, Inc.
