package com.py.ysl.view.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.py.ysl.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhijun 2018.7.19
 * 自定义柱状图
 */
public class BarGraphView extends View{

    private int bargrapColor;
    private int selectRightColor;
    private int xyColor;
    private int textSize;
    private int textColor;
    private Paint mPaint, mChartPaint,mbackgroundPaint;//横轴画笔、柱状图画笔、背景图画笔
    private Paint lineapaint;//折线的画笔
    private Paint dotPain;//圆点的画笔
    private Rect mBound;
    private int mHeight, mChartWidth, mSize;//屏幕宽度高度、柱状图起始位置、柱状图宽度
    private int textStartplace,textWidth;//月份起始位置、月份的间隔宽度
    private int lineStartPlace;//折线开始的地方
    private List<Point>points;



    //数据源
    private List<String>listData;



    public BarGraphView(Context context){
        this(context,null);

    }
    public BarGraphView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }
    public BarGraphView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        initView(context,attrs,defStyleAttr);
    }

    private void initView(Context context,AttributeSet attrs,int defStyleAttr){
        TypedArray array =context.getTheme().obtainStyledAttributes(attrs, R.styleable.MyChartView,defStyleAttr,0);
        int count = array.getIndexCount();
        for (int i=0;i<count;i++){
            int attr = array.getIndex(i);
            switch (attr){
                case R.styleable.MyChartView_bargrapColor:
                    bargrapColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.MyChartView_selectRightColor:
                    selectRightColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.MyChartView_xyColor:
                    xyColor = array.getColor(attr, Color.YELLOW);
                    break;
                case R.styleable.MyChartView_textSize:
                    textSize =array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.MyChartView_textColor:
                    textColor = array.getColor(attr, Color.YELLOW);
                    break;

            }
        }
        array.recycle();
        init();
    }

    public void setData(List<String>listData){
        this.listData = listData;
    }

    /**
     * 初始化画笔
     */
    private void init(){
        listData = new ArrayList<>();

        //地下字体的画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        //柱状体的画笔
        mBound = new Rect();
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        mChartPaint.setColor(bargrapColor);

//背景的画笔
        mbackgroundPaint = new Paint();
        mbackgroundPaint.setAntiAlias(true);
        mbackgroundPaint.setColor(Color.parseColor("#79ABFF"));
//折线的画笔
        lineapaint =  new Paint();
        lineapaint.setAntiAlias(true);

//圆点的画笔
        dotPain =  new Paint();
        dotPain.setAntiAlias(true);

    }

    /**
     * 数据初始化
     */
    private void initData(){
        points = new ArrayList<>();
       int  lineStartplace = getWidth() / 12;
       int []po ={150,200,250,170,150,280};
        for (int i=0;i<listData.size();i++){
            Point point = new Point(lineStartplace,po[i]);
            points.add(point);
            lineStartplace+= textWidth;
        }

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
    //计算高度宽度
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHeight = getHeight();
        mSize = getWidth() / listData.size();
        mChartWidth = getWidth() / listData.size() ;
        textStartplace = getWidth() / listData.size()*2;
        textWidth= getWidth() / listData.size() ;
        lineStartPlace = 0;
        initData();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(xyColor);
        canvas.drawRect(0, 0, getMeasuredWidth(), mHeight/3*2, mbackgroundPaint);
        //画柱状图
        int sizeCount = listData.size()/2;
        //划柱形
        for (int i = 0; i < sizeCount; i++) {
            mChartPaint.setStyle(Paint.Style.FILL);
            //画柱状图
            RectF rectF = new RectF();
            rectF.left = mChartWidth;
            rectF.right = mChartWidth + mSize;
            rectF.bottom = mHeight/3*2;
            rectF.top = (float) (mHeight/5);
            canvas.drawRect(rectF, mChartPaint);
            //canvas.drawRect(mChartWidth, mHeight - 100 - list.get(i) * size, mChartWidth + mSize, mHeight - 100, mChartPaint)
            // ;// 长方形
//            canvas.drawText("1月",);

            mChartWidth += mSize*2;
        }

        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(textColor);//要写在draw里面不然画不出来
        //划地下月份
        for (int i=0;i<sizeCount*2;i++){
            String text =String.valueOf(i + 1)+"月";
            mPaint.getTextBounds(text,0,text.length(),mBound);
            canvas.drawText(text,textStartplace , mHeight/3*2+ mBound.height()+10,mPaint);
            textStartplace+= textWidth;

        }
        lineapaint.setColor(Color.parseColor("#ffffff"));
        lineapaint.setStrokeWidth(5);
        lineapaint.setAntiAlias(true);
        dotPain.setColor(Color.parseColor("#ffffff"));
        dotPain.setAntiAlias(true);
        //划折线
        for (int i=0;i<sizeCount*2-1;i++){
            Point startPoint = points.get(i);
            Point endPoint = points.get(i+1);
            canvas.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y,lineapaint);
        }
        //划圆点
        for (int i=0;i<sizeCount*2;i++){
            Point startPoint = points.get(i);
            canvas.drawCircle(startPoint.x,startPoint.y,10,dotPain);
        }
    }
    /**
     * 注意:
     * 当屏幕焦点变化时重新侧向起始位置,必须重写次方法,否则当焦点变化时柱状图会跑到屏幕外面
     */

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            mSize = getWidth() / listData.size();
            mChartWidth = getWidth() / listData.size();
            textStartplace = getWidth() / listData.size()*2;
            textWidth= getWidth() / listData.size() ;
            lineStartPlace = 0;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int left = 0;
        int top = 0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < 6; i++) {
//                    rect = new Rect(left, top, right, bottom);
//                    left += mWidth / 12;
//                    right += mWidth / 12;
//                    if (rect.contains(x, y)) {
//                        listener.getNumber(i, x, y);
//                        number = i;
//                        selectIndex = i;
//                        selectIndexRoles.clear();
//                        ;
//                        selectIndexRoles.add(selectIndex * 2 + 1);
//                        selectIndexRoles.add(selectIndex * 2);
//                        invalidate();
//                    }
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
