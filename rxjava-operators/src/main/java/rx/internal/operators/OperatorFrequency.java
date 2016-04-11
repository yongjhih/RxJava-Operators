/**
 * Copyright 2015 8tory, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rx.internal.operators;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observable.Operator;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.subjects.*;
import rx.subscriptions.Subscriptions;
import java.util.concurrent.*;

public class OperatorFrequency<T> implements Operator<T, T> {
    private long interval;
    private TimeUnit unit;

    public OperatorFrequency(long interval, TimeUnit unit) {
        this.interval = interval;
        this.unit = unit;
    }

    @Override
    public Subscriber<? super T> call(final Subscriber<? super T> child) {
        return new FrequencySubscriber<>(interval, unit, child);
    }

    // lazy new OperatorFrequency<>();
    public static <R> OperatorFrequency<R> create(long interval, TimeUnit unit) {
        return new OperatorFrequency<R>(interval, unit);
    }

    static class FrequencySubscriber<T> extends Subscriber<T> {
        private long interval;
        private TimeUnit unit;
        private final Subscriber<? super T> child;
        private final Observable<Long> tick;
        private PublishSubject stop = PublishSubject.create();
        private Subject<T, T> subject;
        private long zipCount = 0;

        public FrequencySubscriber(long interval, TimeUnit unit, final Subscriber<? super T> child) {
            super();

            this.interval = interval;
            this.unit = unit;
            this.child = child;

            tick = Observable.interval(interval, unit).map(l -> zipCount).distinct();
        }

        @Override
        public void onStart() {
            subject = PublishSubject.create();
            Observable.zip(subject.asObservable(), tick, (emit, t) -> {
                    zipCount++;
                    return emit;
                })
                .subscribe(emit -> child.onNext(emit), e -> child.onError(e), () -> child.onCompleted());
        }

        @Override
        public void onError(Throwable e) {
            try {
                child.onError(e);
            } finally {
                unsubscribe();
            }
        }

        @Override
        public void onCompleted() {
            subject.onCompleted();
            //stop.onNext(null); // stop tick onCompleted if needed
        }

        @Override
        public void onNext(T t) {
            subject.onNext(t);
        }
    }
}
