package com.py.ysl.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lvfq.pickerview.TimePickerView;
import com.py.ysl.R;
import com.py.ysl.base.BaseActivity;
import com.py.ysl.bean.BaseBean;
import com.py.ysl.module.RetiofitModule;
import com.py.ysl.utils.DialogUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class RxjavaActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.text)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(RxjavaActivity.this);
        eventBind();
        initData();
                getData();
        Log.e("234","RxjavaActivity");

    }


    @Override
    protected void eventBind() {
        super.eventBind();
        textView.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void getData(){
        RetiofitModule module = new RetiofitModule(RxjavaActivity.this);
//        module.getIP(new Observer() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Object o) {
//                Log.e("234","userBean==="+o.toString());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("234","userBean==="+e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

        Map<String,String> map = new HashMap<>();
        map.put("customerId","KKLife");
        map.put("deviceType","android");
        map.put("pushDeviceToken","uojdslfjl");
        module.umengPush(map, new Observer() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Object o) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
//        module.bindDevices(map, new Observer<Object>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Object object) {
//                Log.e("234","userBean==="+object.toString());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("234","userBean==="+e.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

//        module.getCodeVesion("3", new Observer<BaseBean<Object>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//            }
//            @Override
//            public void onNext(BaseBean<Object> userBean) {
//                Gson gson = new Gson();
//              String  object = gson.toJson(userBean);
//                Log.e("234","userBean==="+object.toString());
////                Log.e("234","userBean==="+userBean.getData().getList().get(0).getDownloadurl());
//            }
//            @Override
//            public void onError(Throwable e) {
//                Log.e("234","e==="+e.getMessage());
//            }
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text:
                DialogUtils.alertTimerPicker(RxjavaActivity.this, TimePickerView.Type.YEAR_MONTH_DAY,
                        "yyyy-MM-dd", "选择日期", "确定", Gravity.CENTER,
                        new DialogUtils.TimerPickerCallBack() {
                            @Override
                            public void onTimeSelect(String date) {
                            }
                        });
                break;
            default:
                break;
        }
    }
}
