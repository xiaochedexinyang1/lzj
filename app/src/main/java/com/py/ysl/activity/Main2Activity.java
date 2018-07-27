package com.py.ysl.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;


import com.py.ysl.R;
import com.py.ysl.adapter.FragAdapter;
import com.py.ysl.fragment.MyFragment;
import com.py.ysl.view.MyTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends FragmentActivity {
    @BindView(R.id.tl_news)
    MyTabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager viewPager;

    private List<String>mList;
    private List<Fragment>fragmentsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        initData();

    }
    private void initData(){
        mList = new ArrayList<>();
        fragmentsList = new ArrayList<>();
        for (int i=0;i<18;i++){
            mList.add(201+""+i);
                tabLayout.addTab(tabLayout.newTab().setText(mList.get(i)));
            Fragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("data",201+""+i);
            fragment.setArguments(bundle);
            fragmentsList.add(fragment);
        }
        viewPager.setAdapter(new FragAdapter(getSupportFragmentManager(),fragmentsList));
        tabLayout.setupWithViewPager(viewPager);
        for (int i=0;i<18;i++){
            tabLayout.getTabAt(i).setText(mList.get(i));
        }
        tabLayout.getTabAt(4).select();
    }
}
