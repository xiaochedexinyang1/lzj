package com.py.ysl.base;

import android.app.Activity;
import android.os.Bundle;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;

import io.reactivex.subjects.BehaviorSubject;


public class BaseActivity extends RxActivity {
    protected final BehaviorSubject<ActivityEvent> lifeSubject = BehaviorSubject.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeSubject.onNext(ActivityEvent.CREATE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        lifeSubject.onNext(ActivityEvent.RESUME);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifeSubject.onNext(ActivityEvent.START);
    }

    @Override
    protected void onPause() {
        super.onPause();
        lifeSubject.onNext(ActivityEvent.PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        lifeSubject.onNext(ActivityEvent.STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        lifeSubject.onNext(ActivityEvent.DESTROY);
    }

}
