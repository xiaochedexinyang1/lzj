package com.py.ysl.view.myview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.py.ysl.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lizhijun 2018.7.19
 * 自定义柱状图
 */
public class BarGraphView extends View{

    private int bargrapColor;//柱状图的颜色
    private int selectRightColor;
    private int xyColor;//
    private int textSize;//月份字体大小
    private int textColor;//月份字体颜色
    private Paint mPaint, mChartPaint,mbackgroundPaint;//横轴画笔、柱状图画笔、背景图画笔
    private Paint lineapaint;//折线的画笔
    private Paint dotPain;//圆点的画笔
    private Paint zxbPaint;//折线下面的背景色
    private Rect mBound;
    private float mHeight, mChartWidth, mSize;//屏幕宽度高度、柱状图起始位置、柱状图宽度
    private float textStartplace,textWidth;//月份起始位置、月份的间隔宽度
    //x轴的原点坐标
    private int xOri;
    //y轴的原点坐标
    private int yOri;
    //第一个点对应的最大Y坐标
    private float maxXInit;
    //第一个点对应的最小X坐标
    private float minXInit;

    //第一个柱形的最大Y坐标
    private float maxZxInit;
    //第一个柱形对应的最小X坐标
    private float minZxInit;
    private  LinearGradient gradient;//背景渐变色

    private float linearStartX;//折线第一个点
    private float zhuStartX;//柱形第一个位置
    private Context context;
    //是否在ACTION_UP时，根据速度进行自滑动，没有要求，建议关闭，过于占用GPU
    private boolean isScroll = false;
    //是否正在滑动
    private boolean isScrolling = false;
    //点击的点对应的X轴的第几个点，默认1
    private int selectIndex = 1;
    //数据源
    //x轴坐标对应的数据
    private List<String> xValue = new ArrayList<>();
    //y轴坐标对应的数据
    private List<Integer> yValue = new ArrayList<>();
    //折线对应的数据
    private Map<String, Integer> value = new HashMap<>();
    //速度检测器
    private VelocityTracker velocityTracker;

    public BarGraphView(Context context){
        this(context,null);

    }
    public BarGraphView(Context context,AttributeSet attrs){
        this(context,attrs,0);
    }
    public BarGraphView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        this.context = context;
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


    /**
     * 初始化画笔
     */
    private void init(){
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
//        mbackgroundPaint.setColor(Color.parseColor("#79ABFF"));

//折线的画笔
        lineapaint =  new Paint();
        lineapaint.setAntiAlias(true);

//圆点的画笔
        dotPain =  new Paint();
        dotPain.setAntiAlias(true);
//折线下面的背景色
        zxbPaint = new Paint();
        zxbPaint.setAntiAlias(true);

    }

    /**
     * 数据初始化
     */
    private void initData(){


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
        mHeight = getHeight();
        mSize = getWidth() / value.size()*2;
        mChartWidth = getWidth() / value.size()*2 ;
        textStartplace = getWidth() / value.size()*2;
        textWidth= getWidth() / value.size()*2 ;
        linearStartX = mSize/2;
        xOri = 0;
        yOri = getHeight();
        minXInit = getWidth()  - mSize * (xValue.size() - 1)-mSize/2;//计算最小的长度
        minZxInit = getWidth() -  mSize * (xValue.size());
        maxXInit = linearStartX;
        zhuStartX = 0;
        maxZxInit =mSize;
        initData();
        super.onLayout(changed, left, top, right, bottom);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.setColor(xyColor);
        //渐变
        gradient = new LinearGradient(0,0,getWidth(),mHeight/3*2,context.getResources().getColor(R.color.color_7c98ff),context.getResources().getColor(R.color.color_77b0ff),Shader.TileMode.REPEAT);
        mbackgroundPaint.setShader(gradient);
        canvas.drawRect(0, 0, getMeasuredWidth(), mHeight/3*2, mbackgroundPaint);
        //画柱状图
        drawRect(canvas);
        //划地下月份
       drawMonth(canvas);
       //画折线图和圆点
        drawLine(canvas);
    }

    //划柱形Rect
    private void drawRect(Canvas canvas){
        if (value.size()==0)
            return;
        int sizeCount = value.size();
        //新建图层
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        for (int i = 0; i < sizeCount; i++) {
            mChartPaint.setStyle(Paint.Style.FILL);
            //画柱状图
            float rectX = zhuStartX+  (mSize) * (i);
            RectF rectF = new RectF();
            rectF.left = rectX;
            rectF.right = rectX + mSize;
            rectF.bottom = mHeight/3*2;
            rectF.top = (mHeight/5);
            if (i%2!=0) {
                canvas.drawRect(rectF, mbackgroundPaint);
            }else {
                canvas.drawRect(rectF, mChartPaint);
            }
        }
        //保存图层
        canvas.restoreToCount(layerId);
    }
    //划地下月份
    private void drawMonth(Canvas canvas){
        if (value.size()==0)
            return;
        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(textColor);//要写在draw里面不然画不出来
        int sizeCount = value.size();
        for (int i=0;i<sizeCount;i++){
            float   tx = linearStartX + (mSize) * i;
            String text =String.valueOf(i + 1)+"月";
            mPaint.getTextBounds(text,0,text.length(),mBound);
            canvas.drawText(text,tx , mHeight/3*2+ mBound.height()+10,mPaint);
            textStartplace+= textWidth;
        }
    }

    /**
     * 画折线图和圆点
     * @param canvas
     */
    private void drawLine(Canvas canvas){
        if (value.size()==0)
            return;
        int sizeCount = value.size();
        lineapaint.setColor(Color.parseColor("#ffffff"));
        lineapaint.setStrokeWidth(dpToPx(1));
        lineapaint.setAntiAlias(true);
        lineapaint.setStyle(Paint.Style.STROKE);
        dotPain.setColor(Color.parseColor("#ffffff"));
        dotPain.setAntiAlias(true);
        //划折线
        Path path = new Path();
        Path bcpath = new Path();
        float x = linearStartX + (mSize) * 0;
        float y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(0)) / yValue.get(yValue.size() - 1);
        path.moveTo(x,y);
        bcpath.moveTo(x,mHeight/3*2);
        bcpath.lineTo(x,y);
        LinearGradient bgGradient = new LinearGradient(0,y,0,mHeight/3*2,
                context.getResources().getColor(R.color.color_4E7FE0),
                context.getResources().getColor(R.color.color_7C98FF),Shader.TileMode.CLAMP);
        for (int i=1;i<sizeCount;i++){
            y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
            x = linearStartX + (mSize) * i;
            path.lineTo(x,y);
            bcpath.lineTo(x,y);
        }
        bcpath.lineTo(x,mHeight/3*2);
        bcpath.close();
        canvas.drawPath(path,lineapaint);

        zxbPaint.setShader(bgGradient);
        zxbPaint.setStrokeWidth(dpToPx(3));
        canvas.drawPath(bcpath,zxbPaint);
        //划圆点
        for (int i=0;i<sizeCount;i++){
            float  ry = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
            float   rx = linearStartX + (mSize) * i;
            canvas.drawCircle(rx,ry,dpToPx(3),dotPain);
            if (i == selectIndex - 1) {
                canvas.drawCircle(rx,ry,dpToPx(4),dotPain);
                canvas.drawCircle(rx, ry, dpToPx(10), mChartPaint);
                String text = value.get(xValue.get(i))+"";
                drawFloatTextBox(canvas, rx, ry - dpToPx(7), text,i);
            }
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
//            mHeight = getHeight();
//            mSize = getWidth() / value.size()*2;
//            mChartWidth = getWidth() / value.size()*2 ;
//            textStartplace = getWidth() / value.size()*2;
//            textWidth= getWidth() / value.size()*2 ;
//            linearStartX = mSize/2;
//            xOri = 0;
//            yOri = getHeight();
//            minXInit = getWidth() - (getWidth() - xOri) * 0.1f - mSize * (xValue.size() - 1);//减去0.1f是因为最后一个X周刻度距离右边的长度为X轴可见长度的10%
//            minZxInit = 0;
////        minZxInit = 0;
//            maxXInit = linearStartX;
//            zhuStartX = 0;
//            maxZxInit =mSize;
        }
    }
    private float lastX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isScrolling)
            return super.onTouchEvent(event);
        this.getParent().requestDisallowInterceptTouchEvent(true);//当该view获得点击事件，就请求父控件不拦截事件
        obtainVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float offX = event.getX() - lastX;
                lastX = event.getX();
                if (linearStartX + offX < minXInit) {
                    linearStartX = minXInit;
                    zhuStartX = minZxInit;
                } else if (linearStartX + offX > maxXInit) {
                    linearStartX = maxXInit;
                    zhuStartX = 0;
                } else {
                    linearStartX = linearStartX + offX;
                    zhuStartX += offX;
                }
               invalidate();
                break;
            case MotionEvent.ACTION_UP:
                this.getParent().requestDisallowInterceptTouchEvent(false);//当该view获得点击事件，就请求父控件不拦截事件
                clickAction(event);
                scrollAfterActionUp();
                recycleVelocityTracker();
                break;

        }
        return true;
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
        int dp6 = dpToPx(6);
        int dp7 = dpToPx(7);
        int dp35 = dpToPx(35);
        int dp40 = dpToPx(40);
        //p1
        Path path = new Path();
        lineapaint.setColor(Color.parseColor("#ffffff"));
        lineapaint.setStrokeWidth(dpToPx(1));
        lineapaint.setAntiAlias(true);
        lineapaint.setStyle(Paint.Style.FILL);
        float left = x-dp40;
        float top = y-dp35;
        float right =x+dp40;
        float bottom =y-dp6;
        if (pos==0){
            left=x-mSize/3;
            right=dp40*2+x-mSize/3;
        }
        if (pos==value.size()-1){
            left=x+mSize/3-dp40*2;
            right=x+mSize/3;
        }
        RectF oval3 = new RectF(left, top, right,bottom);// 设置个新的长方形
        canvas.drawRoundRect(oval3, dpToPx(7), dpToPx(7), lineapaint);//第二个参数是x半径，第三个参数是y半径
        path.moveTo(x,y);
        path.lineTo(x-dp7,y-dp7);
        path.lineTo(x+dp7,y-dp7);
        path.close();
        canvas.drawPath(path, lineapaint);



        mPaint.setTextSize(textSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(textColor);//要写在draw里面不然画不出来
        mPaint.getTextBounds(text,0,text.length(),mBound);
//        canvas.drawText(text,x , y+ mBound.height()+10,mPaint);
        if (pos==value.size()-1){
            canvas.drawText(text, x-mSize/3, y  - mBound.height()/3*2-dp7, mPaint);
        }else if (pos==0){
            canvas.drawText(text, x+mSize/3, y  - mBound.height()/3*2-dp7, mPaint);
        }else {
            canvas.drawText(text, x , y  - mBound.height()/3*2-dp7, mPaint);
        }
    }
    /**
     * 获取丈量文本的矩形
     *
     * @param text
     * @param paint
     * @return
     */
    private Rect getTextBounds(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
    /**
     * 点击X轴坐标或者折线节点
     *
     * @param event
     */
    private void clickAction(MotionEvent event) {
        int dp8 = dpToPx(18);
        float eventX = event.getX();
        float eventY = event.getY();
        for (int i = 0; i < xValue.size(); i++) {
            //节点
            float x = linearStartX + mSize * i;
            float y = yOri - yOri * (1 - 0.1f) * value.get(xValue.get(i)) / yValue.get(yValue.size() - 1);
            if (eventX >= x - dp8 && eventX <= x + dp8 &&
                    eventY >= y - dp8 && eventY <= y + dp8 && selectIndex != i + 1) {//每个节点周围8dp都是可点击区域
                selectIndex = i + 1;
                invalidate();
                return;
            }
        }
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
    /**
     * 获取速度跟踪器
     *
     * @param event
     */
    private void obtainVelocityTracker(MotionEvent event) {
        if (!isScroll)
            return;
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }
    /**
     * 回收速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }
    /**
     * 获取速度
     *
     * @return
     */
    private float getVelocity() {
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(1000);
            return velocityTracker.getXVelocity();
        }
        return 0;
    }
    /**
     * 手指抬起后的滑动处理
     */
    private void scrollAfterActionUp() {
        if (!isScroll)
            return;
        final float velocity = getVelocity();
        float scrollLength = maxXInit - minXInit;
        if (Math.abs(velocity) < 10000)//10000是一个速度临界值，如果速度达到10000，最大可以滑动(maxXInit - minXInit)
            scrollLength = (maxXInit - minXInit) * Math.abs(velocity) / 10000;
        ValueAnimator animator = ValueAnimator.ofFloat(0, scrollLength);
        animator.setDuration((long) (scrollLength / (maxXInit - minXInit) * 1000));//时间最大为1000毫秒，此处使用比例进行换算
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                if (velocity < 0 && linearStartX > minXInit) {//向左滑动
                    if (linearStartX - value <= minXInit)
                        linearStartX = minXInit;
                    else
                        linearStartX = linearStartX - value;
                } else if (velocity > 0 && linearStartX < maxXInit) {//向右滑动
                    if (linearStartX + value >= maxXInit)
                        linearStartX = maxXInit;
                    else
                        linearStartX = linearStartX + value;
                }
                invalidate();
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                isScrolling = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                isScrolling = false;
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator.start();

    }

    private List<Integer> getShowMonth(){
        if (value==null )
            return null;

        List<Integer>monthList = new ArrayList<>();
        //本月七月返回的是六月
        Calendar calendar = Calendar.getInstance();
//        int month = calendar.get(Calendar.MONTH);
        int month = 1;
        for (int i=value.size();i>0;i--){
            if (i<=month){
                if (monthList.size()<6){
                    monthList.add(i);
                }

            }

        }
        Log.e("234","monthList==="+monthList.toString());
    return monthList;
    }

    public void setValue(Map<String, Integer> value, List<String> xValue, List<Integer> yValue) {
        this.value = value;
        this.xValue = xValue;
        this.yValue = yValue;
        getShowMonth();
        invalidate();
    }
}
