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
package rx;

import rx.functions.*;

public class SimpleSubscriber<T> extends Subscriber<T> {
    private final Subscriber<? super T> op;

    protected SimpleSubscriber() {
        this(null, false);
    }

    protected SimpleSubscriber(Subscriber<? super T> op) {
        this(op, true);
    }

    protected SimpleSubscriber(Subscriber<? super T> op, boolean shareSubscriptions) {
        super(op, shareSubscriptions);
        this.op = op;
    }

    Action0 onStart;
    Action1<? super T> onNext;
    Action0 onCompleted;
    Action1<Throwable> onError;

    public SimpleSubscriber onStart(Action0 onStart) {
        this.onStart = onStart;
        return this;
    }

    public SimpleSubscriber onNext(Action1<T> onNext) {
        this.onNext = onNext;
        return this;
    }

    public SimpleSubscriber onError(Action1<Throwable> onError) {
        this.onError = onError;
        return this;
    }

    public SimpleSubscriber onCompleted(Action0 onCompleted) {
        this.onCompleted = onCompleted;
        return this;
    }

    @Override
    public void onStart() {
        if (onStart != null) {
            onStart.call();
        } else {
            op.onStart();
        }
    }

    @Override
    public void onCompleted() {
        if (onStart != null) {
            onCompleted.call();
        } else {
            op.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (onStart != null) {
            onError.call(e);
        } else {
            op.onError(e);
        }
    }

    @Override
    public void onNext(T value) {
        if (onStart != null) {
            onNext.call(value);
        } else {
            op.onNext(value);
        }
    }
}
