package com.py.ysl.view;

/**
 * Created by liyuan on 2017/7/4.
 * 自定义view
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 * @author xiaanming
 *
 */
public class RoundProgressBar extends View {
	private float wx;
	private float wy;
	private Paint mPaint;
	private RectF mRectF;
	private float circleGreenWidth;
	private float circleCenterWith;
	private float textWith;
	private static final float DEFAULT_GRENN_WIDTH = 3;// 绿色(灰色)画笔宽度
	private static final float DEFAULT_GRENN_CENYER= 35;// 绿色(灰色)画笔宽度
	private static final float DEFAULT_Text_Size= 14;// 绿色(灰色)画笔宽度
	/**
	 * 当前进度
	 */
	private int progress = 0;
	private String text="";
	public RoundProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();
		mRectF = new RectF();
		circleGreenWidth = TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, DEFAULT_GRENN_WIDTH, getContext()
						.getResources().getDisplayMetrics());
		circleCenterWith =dip2px(context, DEFAULT_GRENN_CENYER);
		textWith =dip2px(context,DEFAULT_Text_Size);
	}

	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		wx = MeasureSpec.getSize(widthMeasureSpec) / 2;
		wy = MeasureSpec.getSize(heightMeasureSpec) / 2;
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}


	public RoundProgressBar(Context context) {
		super(context);

	}
	/**
	 * 获取进度.需要同步
	 * @return
	 */
	public synchronized int getProgress() {
		return progress;
	}
	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
	 * 刷新界面调用postInvalidate()能在非UI线程刷新
	 * @param progress
	 */
	public synchronized void setProgress(int progress) {
		if(progress <= 0){
			this.progress = 1;
		}else if(progress >=100) {
			this.progress = 100;
		}else {
			this.progress = progress;
		}
		invalidate();
	}
	public synchronized void setText(String text) {
		this.text = text;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Paint paint2 = new Paint();
		paint2.setAntiAlias(true);// 设置是否抗锯齿
		paint2.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿
		paint2.setColor(0x99ABD8FF);// 设置画笔为绿色
		paint2.setStrokeWidth(circleGreenWidth);// 设置画笔宽度
		paint2.setStyle(Paint.Style.STROKE);// 设置中空的样式
		mRectF.set(getWidth()/2 - circleCenterWith, getHeight()/2 - circleCenterWith,
				getWidth()/2 + circleCenterWith, getHeight()/2 + circleCenterWith);
		canvas.drawArc(mRectF, 120, 300, false, paint2);
		canvas.save();
		// 画数字
		Paint numberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		numberPaint.setColor(Color.WHITE);
		numberPaint.setTextSize(textWith);
		numberPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText("已抢", getWidth() / 2, getHeight() / 2, numberPaint);
		canvas.drawText(text, getWidth()/2, getHeight()/2+textWith+2,numberPaint);
		canvas.save();
		Paint paint = new Paint();
		paint.setAntiAlias(true);// 设置是否抗锯齿
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);// 帮助消除锯齿
		paint.setColor(Color.WHITE);// 设置画笔为绿色
		paint.setStrokeWidth(circleGreenWidth);// 设置画笔宽度
		paint.setStyle(Paint.Style.STROKE);// 设置中空的样式
		canvas.drawArc(mRectF, 120, progress*3, false, paint);

		canvas.save();

	}





}
