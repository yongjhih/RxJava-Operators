package com.github.yongjhih;

import rx.schedulers.*;
import rx.Observable;
import rx.functions.*;
import rx.observables.*;
import rx.internal.operators.*;
import rx.subjects.*;
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

        Subject<Integer, Integer> mBus = PublishSubject.create();
        mBus.onNext(1);
        mBus.asObservable()
            .lift(new OperatorTick<Integer>(1, TimeUnit.SECONDS))
            .subscribe(System.out::println, e -> {}, () -> System.out.println("onCompleted"));

        mBus.onNext(2);
        mBus.onNext(4);
        mBus.onNext(6);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mBus.onNext(1);
        mBus.onNext(3);
        mBus.onNext(5);
        while (true);
    }
}
