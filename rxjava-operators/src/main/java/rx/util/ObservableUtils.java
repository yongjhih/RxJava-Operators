package rx.util;

import rx.schedulers.*;
import rx.Observable;
import rx.functions.*;
import rx.observables.*;
import rx.util.*;

public class ObservableUtils {

    private ObservableUtils() {
    }

    public static <T, R> Func1<GroupedObservable<T, R>, Observable<R>> flatGroup() {
        return new Func1<GroupedObservable<T, R>, Observable<R>>() {
            @Override
            public Observable<R> call(GroupedObservable<T, R> t) {
                return t;
            }
        };
    }
}
