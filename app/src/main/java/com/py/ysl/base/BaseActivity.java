package com.py.ysl.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.py.ysl.view.CustomProgress;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;

import io.reactivex.subjects.BehaviorSubject;


public class BaseActivity extends RxActivity {
    public static CustomProgress proDlg;
    protected final BehaviorSubject<ActivityEvent> lifeSubject = BehaviorSubject.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifeSubject.onNext(ActivityEvent.CREATE);
        Log.e("234","BaseActivity");
    }

    /**
     * 绑定view
     */
    protected void bindView(){

    }
    /**
     * 事件绑定
     */
    protected void eventBind(){

    }

    /**
     * 加载数据
     */
    protected void initData(){

    }

    protected void showProDlg() {
        proDlg = CustomProgress.show(BaseActivity.this, "", true, null);
    }
    protected void setMessageProDlg() {
        if (proDlg != null && proDlg.isShowing() ) {
            proDlg.setMessage("加载中...");
        }

    }

    protected void cancelProDlg() {
        if (proDlg != null) {
            proDlg.setCancelable(true);
            proDlg.dismiss();
            proDlg = null;
        }
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
