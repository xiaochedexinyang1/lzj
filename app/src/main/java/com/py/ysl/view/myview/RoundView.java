package com.py.ysl.view.myview;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.py.ysl.R;
import com.py.ysl.bean.RoundInfo;
import com.py.ysl.utils.DensityUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * 自定义饼状图
 */
public class RoundView extends View{



    private List<RoundInfo>list;
    private List<Float> presents;

    private Paint mPaint;
    private Paint textPaint;//字的画笔
    private Paint circlePaint;//小圆的的画笔
     private Rect mBound;
    private RectF mRectF;
    private Paint mRectPaint;

    private int roundTextColor;
    private int roundTextSize;

    private int mHeight;
    private int mWidth;
    private float circleCenterWith;
    private static final float DEFAULT_GRENN_CENYER= 55;//空白圆的半径
    private static final float DEFAULT_GRENN_WIDTH= 35;//圆弧的宽度

    private ValueAnimator cakeValueAnimator;
    private float  mAngle = 0.0f;
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
        initView(context,attrs,defStyleAttr);
    }
    private void initView(Context context,AttributeSet attrs,int defStyleAttr){
        TypedArray array =context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyRoundView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i=0;i<count;i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.MyRoundView_roundTextColor:
                    roundTextColor = array.getColor(attr, Color.BLACK);
                    break;
                    case R.styleable.MyRoundView_roundTextSize:
                    roundTextSize = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;


            }
        }
        array.recycle();
        init();
    }
    private void init(){
        initValueAnimator();
        circleCenterWith =dip2px(context, DEFAULT_GRENN_CENYER);
        list = new ArrayList<>();
        presents = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        circlePaint= new Paint();
        circlePaint.setAntiAlias(true);
        mBound = new Rect();
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        mRectF = new RectF();
        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
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
        mRectPaint.setColor(Color.YELLOW);
        //画的背景
//        canvas.drawRect(0, 0, getWidth(), getHeight(), mRectPaint);
//        Log.e("234","999966666==="+Thread.currentThread());
        ondraw(canvas);
        float textNameX = getHeight()/4 + circleCenterWith+dip2px(context,70);
        float textNameY = getHeight()/2-circleCenterWith;
        float textPrenX = getHeight()/4 + circleCenterWith+dip2px(context,95);
        float textPrenY = getHeight()/2-circleCenterWith;
        textPaint.setColor(roundTextColor);
        textPaint.setTextSize(roundTextSize);
        for (int i=0;i<list.size();i++){
            circlePaint.setColor(Color.parseColor(list.get(i).getColor()));
            canvas.drawCircle(textNameX,textNameY,dip2px(context,3),circlePaint);
            textPaint.getTextBounds(list.get(i).getName(),0,list.get(i).getName().length(),mBound);
            String prsent = DensityUtil.decFormatOfTwoPoint(presents.get(i)+"")+"%";
            canvas.drawText(list.get(i).getName(),textNameX+dip2px(context,20),textNameY+mBound.height()/2-dip2px(context,2),textPaint);
            canvas.drawText(prsent,textPrenX+dip2px(context,40),textPrenY+mBound.height()/2-dip2px(context,2),textPaint);
            textNameY+=dip2px(context,20);
            textPrenY+=dip2px(context,20);
        }

    }

    /**
     * 画饼状图
     * @param canvas
     */
    private void ondraw(Canvas canvas){
        float sweepAngle = 0.0f;
//        float startAngle =0.0f;
        float startAngleY =0.0f;
        boolean isBreak=false;//退出循环
        for (int i=0;i<list.size();i++){
//            startAngle += sweepAngle;
            sweepAngle =(float) 3.6*presents.get(i);
            mPaint.setColor(Color.parseColor(list.get(i).getColor()));
            mPaint.setStyle(Paint.Style.STROKE);// 设置中空的样式
            mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿
            mPaint.setStrokeWidth(dip2px(context,DEFAULT_GRENN_WIDTH));
            mRectF.set(getWidth()/4 - circleCenterWith, getHeight()/2 - circleCenterWith,
                    getWidth()/4 + circleCenterWith, getHeight()/2 + circleCenterWith);

            int leng =(int)((float)(presents.get(i)));
            float sweepAngleY = 0.0f;
            sweepAngleY = sweepAngle/leng;
            if (leng<1){
                sweepAngleY = sweepAngle;
                canvas.drawArc(mRectF, startAngleY-0.5f, sweepAngleY+0.5f, false, mPaint);
                startAngleY +=sweepAngle;//要画完再重新计算开始的角度不然会有空隙 错乱
            }
            for (int j=0;j<leng;j++){
                if (mAngle <= startAngleY)
                    isBreak = true;
                if (isBreak)
                       break;
                    canvas.drawArc(mRectF, startAngleY - 0.5f, sweepAngleY + 0.5f, false, mPaint);
                    startAngleY +=sweepAngleY;//要画完再重新计算开始的角度不然会有空隙 错乱
                }
            if (isBreak)
                break;

        }
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
        cakeValueAnimator.start();
        invalidate();
    }
    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void initValueAnimator() {

        PropertyValuesHolder angleValues = PropertyValuesHolder.ofFloat(
                "angle", 0f, 360f);
        cakeValueAnimator = ValueAnimator.ofPropertyValuesHolder(angleValues);
        cakeValueAnimator.setDuration(1500);
        cakeValueAnimator.setRepeatCount(0);
        cakeValueAnimator.setInterpolator(new DecelerateInterpolator());
        cakeValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        updataAnimator();

    }

    /**
     * 动作效果
     * @param
     */
    private void updataAnimator(){

        cakeValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                 mAngle = (Float)animation.getAnimatedValue("angle");
//               long now = Calendar.getInstance().getTimeInMillis();
//                long vi=now-time;
//
//               time = now;

               invalidate();
            }
        });
    }
}
