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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MainTest {
    // TODO
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
        Observable.range(1, 10).lift(OperatorFrequency.create(1, TimeUnit.SECONDS))
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

    @Test
    public void testFreq() {
        // TODO
        /*
        Observable.range(1, 10).zipWith(Observable.interval(1, TimeUnit.SECONDS), (i, t) -> i)
            .subscribe(i -> System.out.println(i + ": " + System.currentTimeMillis()));
        Observable.range(1, 10).lift(new OperatorFrequency(1, TimeUnit.SECONDS))
            .subscribe(i -> System.out.println(i + ": " + System.currentTimeMillis()));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        List<Long> list1 = Observable.range(1, 10).zipWith(Observable.interval(10, TimeUnit.MILLISECONDS), (i, t) -> i)
            .map(i -> System.currentTimeMillis())
            .toList().toBlocking().single();
        System.out.println(list1);
        List<Long> list2 = Observable.range(1, 10).lift(OperatorFrequency.create(10, TimeUnit.MILLISECONDS))
            .map(i -> System.currentTimeMillis())
            .toList().toBlocking().single();
        System.out.println(list2);
        assertTrue(true);

    }
}
