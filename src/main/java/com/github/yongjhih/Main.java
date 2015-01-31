package com.github.yongjhih;

import rx.schedulers.*;
import rx.Observable;
import rx.functions.*;
import rx.observables.*;
import rx.util.*;
import rx.internal.operators.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        /*
        Observable<Integer> ranger = Observable.range(1, 100);
        Observable<Long> timer = Observable.interval(100, TimeUnit.MILLISECONDS);
        Observable.zip(ranger, timer, (i, t) -> {
                sub.onNext(i);
                return i;
            }).subscribe(System.out::println);
        */

        Observable.range(1, 10)
            .lift(new OperatorTick<Integer>(1, TimeUnit.SECONDS))
            .subscribe(System.out::println, e -> {}, () -> System.out.println("onCompleted"));
        while (true);
    }
}
