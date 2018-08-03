package com.py.ysl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.py.ysl.R;
import com.py.ysl.base.BaseActivity;
import com.py.ysl.bean.RoundInfo;
import com.py.ysl.view.myview.BarGraphView;
import com.py.ysl.view.myview.ChartView;
import com.py.ysl.view.myview.RoundView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lizhijun
 * 自定义view测试
 */
public class ViewTestActivity extends BaseActivity{
    @BindView(R.id.bar_view)
    BarGraphView bar_view;
    @BindView(R.id.cicyle)
    RoundView roundView;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.chartview)
    ChartView chartview;


    private List<String>list;
    private List<RoundInfo>roundList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_view1);
        ButterKnife.bind(ViewTestActivity.this);
        list = new ArrayList<>();
        roundList= new ArrayList<>();
        list.add("2500");
        list.add("6500");
        list.add("500");
        list.add("900");
        list.add("2597");
        list.add("2888");
        list.add("2500");
        list.add("6500");
        list.add("500");
        list.add("900");
        list.add("2597");
        list.add("2888");
        RoundInfo info = new RoundInfo();
        info.setCount("250");
        info.setColor("#ffd862");
        info.setName("衣服");
        RoundInfo info2 = new RoundInfo();
        info2.setCount("1250");
        info2.setName("饮食");
        info2.setColor("#71c6fe");

        RoundInfo info3 = new RoundInfo();
        info3.setCount("2250");
        info3.setName("购物");
        info3.setColor("#5f98fe");

        RoundInfo info4 = new RoundInfo();
        info4.setCount("10");
        info4.setName("桑拿");
        info4.setColor("#fe7614");

        RoundInfo info5 = new RoundInfo();
        info5.setCount("115");
        info5.setName("淘宝");
        info5.setColor("#a488fe");

        RoundInfo info6 = new RoundInfo();
        info6.setCount("350");
        info6.setName("喝酒");
        info6.setColor("#ff9693");
        roundList.add(info);
        roundList.add(info2);
        roundList.add(info3);
        roundList.add(info4);
        roundList.add(info5);
        roundList.add(info6);
//        list.add("2500");
//        list.add("9500");
//        list.add("6500");
//        list.add("4500");
//        list.add("1500");
//        list.add("2500");

        roundView.setData(roundList);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roundView.setData(roundList);
            }
        });
        List<String>StrList = new ArrayList<>();
        List<Integer>intList = new ArrayList<>();
        //折线对应的数据
         Map<String, Integer> value = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            StrList.add((i + 1) + "月");
        }
        value.put( "1月", 200);//60--240
        value.put( "2月", 250);//60--240
        value.put( "3月", 180);//60--240
        value.put( "4月", 190);//60--240
        value.put( "5月", 204);//60--240
        value.put( "6月", 190);//60--240
        value.put( "7月", 200);//60--240
        value.put( "8月", 230);//60--240
        value.put( "9月", 210);//60--240
        value.put( "10月", 240);//60--240
        value.put( "11月", 260);//60--240
        value.put( "12月", 200);//60--240 value.put( "1月", 200);//60--240

        for (int i = 0; i < 6; i++) {
            intList.add(i * 60);
        }
        chartview.setValue(value,StrList,intList);
        bar_view.setValue(value,StrList,intList);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
