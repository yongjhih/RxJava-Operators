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

import java.util.List;

import rx.Observable.Operator;
import rx.Subscriber;

/**
 * Return an {@code Observable} that emits the items emitted by the source {@code Observable}, in a reversed order.
 *
 * @param <T>
 *          the type of the items emitted by the source and the resulting {@code Observable}s
 */
public final class OperatorShuffle<T> implements Operator<T, T> {
    @Override
    public Subscriber<? super T> call(final Subscriber<? super T> o) {
        return new OperatorToShuffledList().call(new OperatorFlatList().call(o));
    }
}
