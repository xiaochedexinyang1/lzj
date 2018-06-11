package com.py.ysl.activity;

import android.content.Intent;
import android.os.Bundle;

import com.py.ysl.R;
import com.py.ysl.base.BaseActivity;

public class LanchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(LanchActivity.this,RxjavaActivity.class));
        finish();
    }
}
