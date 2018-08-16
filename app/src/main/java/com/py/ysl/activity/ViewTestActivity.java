package com.py.ysl.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.py.ysl.R;
import com.py.ysl.base.BaseActivity;
import com.py.ysl.bean.RoundInfo;
import com.py.ysl.view.myview.BarGraphView;
import com.py.ysl.view.myview.ChartView;
import com.py.ysl.view.myview.LineaView;
import com.py.ysl.view.myview.MonthView;
import com.py.ysl.view.myview.RoundView;
import com.py.ysl.view.myview.WaveView;

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
    @BindView(R.id.month)
    MonthView month;
    @BindView(R.id.wave_view)
    WaveView wave_view;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.lineavire)
    LineaView lineavire;

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

        roundView.setData(roundList,false);
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roundView.setData(roundList,true);
            }
        });
        List<String>StrList = new ArrayList<>();
        List<Integer>intList = new ArrayList<>();
        //折线对应的数据
         Map<String, Integer> value = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            StrList.add((i + 1) + "月");
        }
        value.put( "1月", 600);//60--240
        value.put( "2月", 500);//60--240
        value.put( "3月", 800);//60--240
        value.put( "4月", 900);//60--240
        value.put( "5月", 400);//60--240
        value.put( "6月", 900);//60--240
        value.put( "7月", 1100);//60--240
        value.put( "8月", 1300);//60--240
        value.put( "9月", 1800);//60--240
        value.put( "10月", 1400);//60--240
        value.put( "11月", 1600);//60--240
        value.put( "12月", 1000);//60--240 value.put( "1月", 200);//60--240

        chartview.setValue(value,StrList,intList);
        bar_view.setValue(value,StrList,1800*3/2);
        bar_view.setCurrentMonth(11);//当没有满一年的时候需要用到
        List<String>monList = new ArrayList<>();
        for (int i=0;i<31;i++){
            int val = (int)(Math.random()*100+1);
            monList.add(val+"");
        }

        month.setValue(monList,150,31);
        month.setCurrentDay(21);
        progressbar.setMax(100);
        progressbar.setProgress(50);
        lineavire.setVaule("#ffd862",10,8);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private String getColor(String category){
        HashMap<String,String>map = new HashMap<>();
        map.put("交通","#5F9BFF");
        map.put("衣服鞋包","#FF78E5");
        map.put("日常生活","#FFD764");
        map.put("医疗卫生","#FF2937");
        map.put("通讯","#3EDFFF");
        map.put("第三方支付","#FB804F");
        map.put("还款","#C977FF");
        map.put("其他","#94BCFF");
        map.put("","#94BCFF");
        map.put(null,"#94BCFF");
        return map.get(category);
    }
}
