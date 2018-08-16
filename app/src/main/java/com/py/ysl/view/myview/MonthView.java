package com.py.ysl.view.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.py.ysl.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lizhijun 2018.8.7
 *自定义每个月每日的 消费情况
 */
public class MonthView extends View {

    private Context context;
    private int backgroundColor,rectfColor,rectfChoseColor,textColor,tktextColor;
    private int textSize,tktextSize;
    private int monthDay = 31;
    private float mHeight;
    private float mWidth;
    private float rectfWidth;
    private Rect mBound;
    private Paint bgPaint,textPaint,tkTextPaint,rectPint,tkPant;//背景,底下的text,弹框的text,柱形的
    private LinearGradient gradient;//背景渐变色
    private int selectIndex = 1;

    private List<String> value = new ArrayList<>();
    private  int yValue;
    //x轴的原点坐标
    private int xOri;
    //y轴的原点坐标
    private int yOri;

    private int currentDay = 32;
    public MonthView(Context context){
        this(context,null);

    }
    public MonthView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }
    public MonthView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        this.context = context;
        initView(context,attrs,defStyleAttr);
    }
    private void initView(Context context,AttributeSet attrs,int defStyleAttr){
        TypedArray array =context.getTheme().obtainStyledAttributes(attrs, R.styleable.MonthView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i=0;i<count;i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.MonthView_backgroundColor:
                    backgroundColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.MonthView_rectfColor:
                    rectfColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.MonthView_rectfChoseColor:
                    rectfChoseColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.MonthView_monthtextColor:
                    textColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.MonthView_monthtextSize:
                    textSize =array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MonthView_tktextColor:
                    tktextColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.MonthView_tktextSize:
                    tktextSize
                            =array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
            }
        }
        array.recycle();
        init();
    }

    /**
     * 初始化画笔
     */
    private void init(){
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);

        mBound = new Rect();
        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);


        tkTextPaint = new Paint();
        tkTextPaint.setTextSize(tktextSize);
        tkTextPaint.setColor(tktextColor);
        tkTextPaint.setAntiAlias(true);


        rectPint = new Paint();
        rectPint.setColor(rectfColor);
        rectPint.setAntiAlias(true);
        tkPant = new Paint();
        tkPant.setAntiAlias(true);
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
        rectfWidth = mWidth/monthDay/2;
        xOri = 0;
        yOri = getHeight()/3*2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gradient = new LinearGradient(0,0,getWidth(),0,context.getResources().getColor(R.color.color_7c98ff),context.getResources().getColor(R.color.color_77b0ff), Shader.TileMode.REPEAT);
        bgPaint.setShader(gradient);
        canvas.drawRect(0, 0, getMeasuredWidth(), mHeight/3*2, bgPaint);
        for (int i = 1; i <= monthDay; i++) {
            rectPint.setStyle(Paint.Style.FILL);
            //画柱状图
            float rectX =  (rectfWidth) * (2*i-1);
            float rectTop = yOri - yOri  *Integer.parseInt(value.get(i-1)) / yValue;
            RectF rectF = new RectF();
            rectF.left = rectX;
            rectF.right = rectX + rectfWidth;
            rectF.bottom = mHeight/3*2;
            rectF.top = rectTop;
            if (currentDay>=i) {
                if (i == selectIndex) {
                    rectPint.setColor(rectfChoseColor);
                    canvas.drawRect(rectF, rectPint);
                    String text = value.get(i - 1);
                    drawFloatTextBox(canvas, rectX + rectfWidth / 2, rectTop - rectfWidth / 2, text, i);
                } else {
                    rectPint.setColor(rectfColor);
                    canvas.drawRect(rectF, rectPint);
                }
            }else {
                rectPint.setColor(Color.parseColor("#0dffffff"));
                rectF.top= mHeight/3*2-rectfWidth*2;
                canvas.drawRect(rectF, rectPint);
            }
        }
        float   tx = 0f;
        String text ="";
        for (int i=0;i<4;i++){
            if (i==0){
                tx= rectfWidth ;
                text="1";
            }
            if (i==1){
                tx= rectfWidth*18+mBound.width()/3 ;
                text="10";
            }
            if (i==2){
                tx= rectfWidth*38+mBound.width()/4 ;
                text="20";
            }
            if (i==3){
                tx= rectfWidth*58+mBound.width()/4 ;
                text="30";
            }

            textPaint.getTextBounds(text,0,text.length(),mBound);
            textPaint.setColor(textColor);//要写在draw里面不然画不出来
                canvas.drawText(text,tx, mHeight/3*2+ mBound.height()+10,textPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                this.getParent().requestDisallowInterceptTouchEvent(false);//当该view获得点击事件，就请求父控件不拦截事件
                clickAction(event);
                break;

        }
        return true;
    }

    /**
     * 点击X轴坐标或者折线节点
     *
     * @param event
     */
    private void clickAction(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        for (int i = 1; i <= monthDay; i++) {
            //节点
            float x = rectfWidth*(2*i-1);
            float y =   yOri - yOri  *Integer.parseInt(value.get(i-1)) / yValue;
            if (eventX >=x && eventX <= x+rectfWidth*2 && eventY >= y&&eventY <= mHeight/3*2){
                if (currentDay>=i) {
                    selectIndex = i;
                    invalidate();
                }
                return;
            }
        }
    }
    /**
     * 绘制显示Y值的浮动框
     *
     * @param canvas
     * @param x
     * @param y
     * @param text
     * @param pos 第几个
     */
    private void drawFloatTextBox(Canvas canvas, float x, float y, String text,int pos) {
        if (value==null ||value.size()==0)
            return;
        int dp6 = dpToPx(6);
        int dp7 = dpToPx(7);
        int dp18 = dpToPx(18);
        int dp35 = dpToPx(35);
        int dp40 = dpToPx(40);
        //p1
        Path path = new Path();
        tkPant.setColor(Color.parseColor("#ffffff"));
        tkPant.setStrokeWidth(dpToPx(1));
        tkPant.setAntiAlias(true);
        tkPant.setStyle(Paint.Style.FILL);
        float left = x-dp40;
        float top = y-dp35;
        float right =x+dp40;
        float bottom =y-dp6;
        if (pos==1){
            left=x;
            right=dp40*2;
        }
        if (pos<4 && pos>1){
            left=x-rectfWidth*3;
            right=dp40*2+x-rectfWidth*3;
        }
        if (pos>=value.size()-3&&pos < value.size()-1){
            left=x+rectfWidth*3-dp40*2;
            right=x+rectfWidth*3;
        }
        if (pos >= value.size()-1&&pos <= value.size()){
            left=x-dp40*2;
            right=x;
        }

        RectF oval3 = new RectF(left, top, right,bottom);// 设置个新的长方形
        canvas.drawRoundRect(oval3, dpToPx(7), dpToPx(7), tkPant);//第二个参数是x半径，第三个参数是y半径
        if (pos==1){
            path.moveTo(x+dp18,y);
            path.lineTo(x+dp18-dp7,y-dp7);
            path.lineTo(x+dp18+dp7,y-dp7);
            path.close();
            canvas.drawPath(path, tkPant);
        }else if (pos>=value.size()-1 && pos<=value.size()){
            path.moveTo(x-dp18, y);
            path.lineTo(x - dp7-dp18, y - dp7);
            path.lineTo(x + dp7-dp18, y - dp7);
            path.close();
            canvas.drawPath(path, tkPant);
        }else {
            path.moveTo(x, y);
            path.lineTo(x - dp7, y - dp7);
            path.lineTo(x + dp7, y - dp7);
            path.close();
            canvas.drawPath(path, tkPant);
        }

        tkTextPaint.setTextSize(tktextSize);
        tkTextPaint.setTextAlign(Paint.Align.CENTER);
        tkTextPaint.setColor(tktextColor);//要写在draw里面不然画不出来
        tkTextPaint.getTextBounds(text,0,text.length(),mBound);
        if (pos>=value.size()-3&&pos < value.size()-1){
            canvas.drawText(text, x-rectfWidth*3, y  - mBound.height()/3*2-dp7, tkTextPaint);
        }else if (pos<4){
            canvas.drawText(text, x+rectfWidth*3, y  - mBound.height()/3*2-dp7, tkTextPaint);
        }else if (pos >= value.size()-1&&pos <= value.size()) {
            canvas.drawText(text+text, x-dp40, y  - mBound.height()/3*2-dp7, tkTextPaint);
        }else {
            canvas.drawText(text, x , y  - mBound.height()/3*2-dp7, tkTextPaint);
        }
    }
    /**
     * 设置初始值
     * @param value
     * @param yValue 纵坐标的最大值
     */
    public void setValue( List<String> value, int yValue,int monthDay) {
        this.value = value;
        this.yValue = yValue;
        this.monthDay = monthDay;
        invalidate();
    }
    /**
     * dp转化成为px
     *
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }
    public void setCurrentDay(int currentDay){
        this.currentDay = currentDay;
        selectIndex = currentDay;
        invalidate();


    }
}
