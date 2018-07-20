package com.py.ysl.view.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.py.ysl.R;
import com.py.ysl.bean.RoundInfo;

import java.util.ArrayList;
import java.util.List;


/**
 * 自定义饼状图
 */
public class RoundView extends View{



    private List<RoundInfo>list;
    private List<Float> presents;

    private Paint mPaint;
    private RectF mRectF;
    private Paint mRectPaint;

    private int mHeight;
    private int mWidth;
    private float circleCenterWith;
    private static final float DEFAULT_GRENN_CENYER= 55;//空白圆的半径
    private static final float DEFAULT_GRENN_WIDTH= 35;//圆弧的宽度

    private Context context;
    public RoundView(Context context){
        this(context,null);

    }
    public RoundView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }
    public RoundView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        this.context =context;
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize * 1 / 2;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = heightSize * 1 / 2;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHeight = getHeight();
        mWidth = getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sweepAngle = 0.0f;
        float startAngle =0.0f;
        mRectPaint.setColor(Color.YELLOW);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mRectPaint);
        for (int i=0;i<list.size();i++){
            startAngle += sweepAngle;
            sweepAngle =(float) 3.6*presents.get(i);
            mPaint.setColor(Color.parseColor(list.get(i).getColor()));
            mPaint.setStyle(Paint.Style.STROKE);// 设置中空的样式
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿
            mPaint.setStrokeWidth(dip2px(context,DEFAULT_GRENN_WIDTH));
            mRectF.set(getWidth()/4 - circleCenterWith, getHeight()/4 - circleCenterWith,
                getWidth()/4 + circleCenterWith, getHeight()/4 + circleCenterWith);

        canvas.drawArc(mRectF, startAngle-0.5f, sweepAngle+0.5f, false, mPaint);
        }
    }

    private void initView(){
        circleCenterWith =dip2px(context, DEFAULT_GRENN_CENYER);
        list = new ArrayList<>();
        presents = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mRectF = new RectF();
        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
    }

    /**
     * 元数据
     * @param list
     */
    public   void setData(List<RoundInfo>list){
        this.list = list;
        int total = 0;
        for (int i=0;i<list.size();i++){
            total += Integer.parseInt(list.get(i).getCount());
        }
        for (int i=0;i<list.size();i++){
            float cursent  = (float) (Integer.parseInt(list.get(i).getCount()))*100/total;
            presents.add(cursent);
        }
        invalidate();
    }
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
