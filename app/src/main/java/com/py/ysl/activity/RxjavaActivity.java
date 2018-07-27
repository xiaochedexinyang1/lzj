package com.py.ysl.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lvfq.pickerview.TimePickerView;
import com.py.ysl.R;
import com.py.ysl.activity.kt.KtActivity1;
import com.py.ysl.base.BaseActivity;
import com.py.ysl.bean.KKBaseBean;
import com.py.ysl.retiofit.module.RetiofitModule;
import com.py.ysl.utils.DefaultPermissionSetting;
import com.py.ysl.utils.DefaultRationale;
import com.py.ysl.utils.DialogUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxjavaActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.text)
    TextView textView;
    @BindView(R.id.text1)
    TextView tvRxjava;
    @BindView(R.id.text2)
    TextView tvPerssion;
    @BindView(R.id.text3)
    TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(RxjavaActivity.this);
        eventBind();
        initData();
        Log.e("234", "RxjavaActivity");
//        showProDlg();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },2000);
    }
    @Override
    protected void eventBind() {
        super.eventBind();
        textView.setOnClickListener(this);
        tvRxjava.setOnClickListener(this);
        tvPerssion.setOnClickListener(this);
        text3.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void getData() {
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

        Map<String, String> map = new HashMap<>();
        map.put("customerId", "KKLife");
        map.put("deviceType", "android");
        map.put("pushDeviceToken", "uojdslfjl");
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

        module.getHomeInfo( new Observer<KKBaseBean<Object>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(KKBaseBean<Object> userBean) {
                Gson gson = new Gson();
              String  object = gson.toJson(userBean);
                Log.e("234","userBean==="+object.toString());
//                Log.e("234","userBean==="+userBean.getData().getList().get(0).getDownloadurl());
            }
            @Override
            public void onError(Throwable e) {
                Log.e("234","e==="+e.getMessage());
            }
            @Override
            public void onComplete() {

            }
        });

    }

    /**
     * 获取权限
     */
    private void requitPremission(){
        AndPermission.with(RxjavaActivity.this).permission(Manifest.permission.READ_PHONE_STATE)
                .rationale(new DefaultRationale())
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.e("234","有权限了");
                        Toast.makeText(RxjavaActivity.this,"有权限了",Toast.LENGTH_SHORT).show();
                    }
                })
                .onDenied(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.e("234","没有权限");
                        if (AndPermission.hasAlwaysDeniedPermission(RxjavaActivity.this, permissions)) {
                            new DefaultPermissionSetting(RxjavaActivity.this)
                                    .showSetting(permissions,getResources().getString(R.string.permission_write_phone));
                        }
                    }
                }).start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.text:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //没有权限去申请权限
                    requitPremission();
                }else {
                    TelephonyManager tm = (TelephonyManager) RxjavaActivity.this.getSystemService(TELEPHONY_SERVICE);
                    tm.getDeviceId();
                }
                break;
            case R.id.text1:
                DialogUtils.alertTimerPicker(RxjavaActivity.this, TimePickerView.Type.YEAR_MONTH_DAY,
                        "yyyy-MM-dd", "选择日期", "确定", Gravity.CENTER,
                        new DialogUtils.TimerPickerCallBack() {
                            @Override
                            public void onTimeSelect(String date) {
                            }
                        });
                break;
            case R.id.text2:
                getData();
                break;
            case R.id.text3:
//                startActivity(new Intent(RxjavaActivity.this, KtActivity1.class));ViewTestActivity
                startActivity(new Intent(RxjavaActivity.this, ViewTestActivity.class));
                break;
            default:
                break;
        }
    }
}
