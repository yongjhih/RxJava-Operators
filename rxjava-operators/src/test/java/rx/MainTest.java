package rx;

import rx.schedulers.*;
import rx.Observable;
import rx.functions.*;
import rx.observables.*;
import rx.internal.operators.*;
import rx.subjects.*;
import java.util.concurrent.*;
import java.util.Collections;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MainTest {
    @Test
    public void testMain() {
        Observable<Integer> ranger = Observable.range(1, 10);
        Observable<Long> timer = Observable.interval(1, TimeUnit.SECONDS);
        /*
        Observable.zip(ranger, timer, (i, t) -> {
                return i;
            }).subscribe(System.out::println);
        */

        Observable.range(1, 10).toList().doOnNext(list -> Collections.reverse(list))
            .subscribe(System.out::println);
        Observable.range(1, 10).toList().doOnNext(list -> Collections.reverse(list)).flatMap(l -> Observable.from(l))
            .subscribe(System.out::println);
        Observable.range(1, 10).lift(new OperatorReverse())
            .subscribe(System.out::println);
        Observable.range(1, 10).lift(new OperatorShuffle())
            .subscribe(System.out::println);
        Observable.range(1, 10).lift(new OperatorToReversedList())
            .subscribe(System.out::println);
        Observable.range(1, 10).lift(new OperatorToShuffledList())
            .subscribe(System.out::println);
        Observable.range(1, 10).lift(new OperatorFrequency(1, TimeUnit.SECONDS))
            .subscribe(i -> System.out.println(i + ": " + System.currentTimeMillis()));

        assertTrue(true);
        //Observable.from(Arrays.asList("1", "2")).single().subscribe(System.out::println);

        /*
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
        */
    }
}
