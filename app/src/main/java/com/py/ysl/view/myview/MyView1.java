package com.py.ysl.view.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyView1 extends View{
    public MyView1(Context context){
        this(context,null);
    }
    public MyView1(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }
    public MyView1(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }
}
