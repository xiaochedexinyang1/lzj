package com.py.ysl.utils;

import android.content.Context;

import com.lvfq.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @author  lizhijun
 * 弹框工具类
 */
public class DialogUtils {

    /**
     * 弹出时间选择
     * @param context
     * @param type     TimerPickerView 中定义的 选择时间类型 TimePickerView.Type.YEAR_MONTH_DAY y
     * @param format   时间格式化yyyy-MM-dd HH:mm
     * @param subTitle  底部确定按钮的标题
     * @param gravity  显示的位置
     * @param callBack 时间选择回调
     */
    public static void alertTimerPicker(Context context, TimePickerView.Type type, final String format, String title, String subTitle, int gravity, final TimerPickerCallBack callBack) {
        TimePickerView pvTime = new TimePickerView(context, type, gravity);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 10, calendar.get(Calendar.YEAR) + 10);
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setSubmitTitle(subTitle);
        pvTime.setCancelable(false);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
//                        tvTime.setText(getTime(date));
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                callBack.onTimeSelect(sdf.format(date));
            }
        });
        pvTime.setTextSize(17);
        pvTime.setTitle(title);
        //弹出时间选择器
        pvTime.show();
    }
    /**
     * 时间选择回调
     */
    public interface TimerPickerCallBack {
        void onTimeSelect(String date);
    }

}
