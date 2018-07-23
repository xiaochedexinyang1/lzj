package com.py.ysl.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.TypedValue;

/**
 *@author  lizhijun
 * dp px sp 转换工具类
 */
public class DensityUtil {
	/**
	 * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}


	public static int spToPx(int sp,Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
	}
	public static int dpToPx(int dp, Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
	}
	/**
	 * 保留两位小数
	 * @param minAmount
	 * @return
	 */
	public static String decFormatOfTwoPoint(String minAmount){

		if (TextUtils.isEmpty(minAmount)){
			minAmount =  String.format("%.2f", Double.parseDouble("0"));
		}else {
			minAmount =  String.format("%.2f", Double.parseDouble(minAmount));
		}

		return minAmount;
	}
}
