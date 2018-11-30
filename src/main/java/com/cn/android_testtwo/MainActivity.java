package com.cn.android_testtwo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.util.ArrayList;
import java.util.List;
import CarFragment.CarActivity;
import HomeFragment.HomeActivity;
import LayFragment.LayActivity;
import MineFragment.MineActivity;
import SeeFragment.SeeActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.but1)
    RadioButton but1;
    @BindView(R.id.but2)
    RadioButton but2;
    @BindView(R.id.but3)
    RadioButton but3;
    @BindView(R.id.but4)
    RadioButton but4;
    @BindView(R.id.but5)
    RadioButton but5;
    @BindView(R.id.radio)
    RadioGroup radio;
    private List<Fragment> list = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        list.add(new HomeActivity());
        list.add(new LayActivity());
        list.add(new SeeActivity());
        list.add(new CarActivity());
        list.add(new MineActivity());

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        vp.setAdapter(fragmentPagerAdapter);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.but1:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.but2:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.but3:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.but4:
                        vp.setCurrentItem(3);
                        break;
                    case R.id.but5:
                        vp.setCurrentItem(4);
                        break;

                }
            }
        });
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        but1.setChecked(true);
                        break;
                    case 1:
                        but2.setChecked(true);
                        break;
                    case 2:
                        but3.setChecked(true);
                        break;
                    case 3:
                        but4.setChecked(true);
                        break;
                    case 4:
                        but5.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
