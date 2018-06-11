package com.py.ysl.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lvfq.pickerview.TimePickerView;
import com.py.ysl.R;
import com.py.ysl.base.BaseActivity;
import com.py.ysl.module.RetiofitModule;
import com.py.ysl.utils.DefaultPermissionSetting;
import com.py.ysl.utils.DefaultRationale;
import com.py.ysl.utils.DialogUtils;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxjavaActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.text)
    TextView textView;
    @Bind(R.id.text1)
    TextView tvRxjava;
    @Bind(R.id.text2)
    TextView tvPerssion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        ButterKnife.bind(RxjavaActivity.this);
        eventBind();
        initData();
        Log.e("234", "RxjavaActivity");

    }


    @Override
    protected void eventBind() {
        super.eventBind();
        textView.setOnClickListener(this);
        tvRxjava.setOnClickListener(this);
        tvPerssion.setOnClickListener(this);
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



        File file = new File(Environment.getExternalStorageDirectory()+"lizhijun");
        if (!file.exists()){
            file.mkdir();
        }
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
        ButterKnife.unbind(this);
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
            default:
                break;
        }
    }
}
