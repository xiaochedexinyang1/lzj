package com.py.ysl.module;

import android.app.Activity;

import com.py.ysl.retiofit.RetrofitFactory;
import com.py.ysl.utils.Utils2API;
import com.py.ysl.utils.UtilsApi;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.RxActivity;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RetiofitModule {
    private RxActivity rxAppActivity;
    private Activity activity;
    public RetiofitModule(Activity activity){
        rxAppActivity = (RxActivity)activity;
        this.activity = activity;

    }


    public void getCodeVesion(String device,Observer observer) {
       RetrofitFactory.getRetrofit().create(UtilsApi.class).getCodeVesion(device)
        .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               .compose(rxAppActivity.bindUntilEvent(ActivityEvent.DESTROY))//在activity销毁时取消订阅
                .subscribe(observer);
    }

    public void bindDevices(Map<String,String> map, Observer observer) {
        RetrofitFactory.getRetrofit().create(UtilsApi.class).bindDevices(map)
                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxAppActivity.bindUntilEvent(ActivityEvent.DESTROY))//在activity销毁时取消订阅
                .subscribe(observer);
    }
    public void getIP(Observer observer) {
        RetrofitFactory.getRetrofit().create(Utils2API.class).getIp()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxAppActivity.bindUntilEvent(ActivityEvent.DESTROY))//在activity销毁时取消订阅
                .subscribe(observer);
    }
    public void umengPush(Map<String,String> map,Observer observer) {
        RetrofitFactory.getRetrofit().create(UtilsApi.class).umengPush(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(rxAppActivity.bindUntilEvent(ActivityEvent.DESTROY))//在activity销毁时取消订阅
                .subscribe(observer);
    }
}
