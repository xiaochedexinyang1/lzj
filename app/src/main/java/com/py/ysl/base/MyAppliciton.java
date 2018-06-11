package com.py.ysl.base;

import android.app.Application;
import android.content.Context;

public class MyAppliciton  extends Application{

    public static Context appContext;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}
