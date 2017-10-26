package com.tanx.expirit.util;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.functions.Func0;
import rx.internal.operators.OnSubscribeDefer;
import rx.Subscriber;

@Component
public class ObservableTxFactory {
	
    public final <T> Observable<T> create(Observable.OnSubscribe<T> f) {
        return new ObservableTx<>(this, f);
    }
    
    public final <T> Observable<T> defer(Func0<Observable<T>> observableFactory) {
        return create(new OnSubscribeDefer<T>(observableFactory));
    }
    

//    public final static <T> Observable<T> defer(Func0<Observable<T>> observableFactory) {
//        return Observable.create((new OnSubscribeDefer<T>(observableFactory)));
//    }

    @Transactional
    public void call(Observable.OnSubscribe onSubscribe, Subscriber subscriber) {
    	System.out.println("call before");
        onSubscribe.call(subscriber);
        System.out.println("call after");
    }

    private static class ObservableTx<T> extends Observable<T> {

        public ObservableTx(ObservableTxFactory observableTxFactory, OnSubscribe<T> f) {
            super(new OnSubscribeDecorator<>(observableTxFactory, f));
        }
    }

    private static class OnSubscribeDecorator<T> implements Observable.OnSubscribe<T> {

        private final ObservableTxFactory observableTxFactory;
        private final Observable.OnSubscribe<T> onSubscribe;

        OnSubscribeDecorator(final ObservableTxFactory observableTxFactory, final Observable.OnSubscribe<T> s) {
            this.onSubscribe = s;
            this.observableTxFactory = observableTxFactory;
        }

        @Override
        public void call(Subscriber<? super T> subscriber) {
            observableTxFactory.call(onSubscribe, subscriber);
        }
    }
}