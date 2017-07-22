package com.example.artlab.forecast;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.artlab.forecast.adapters.MyFragmentPagerAdapter;
import com.example.artlab.forecast.Fragments.WeeklyWeather;
import com.example.artlab.forecast.Fragments.CurrentWeather;

import java.util.List;
import java.util.Vector;

/**
 * Created by artlab on 17. 7. 22.
 */

public class MainFragment extends Fragment implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    CurrentWeather currentWeather;
    WeeklyWeather weeklyWeather;
    private int previous=1;
    TabHost tabHost;
    Thread Draw_Panel;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myViewPagerAdapter;
    TextView x;
    boolean work=true;
    int i = 0;
    View v;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.tabs_viewpager_layout, container, false);
        i++;

        // init tabhost
        this.initializeTabHost(savedInstanceState);

        // init ViewPager
        this.initializeViewPager();


        //tabHost.getTabWidget().getChildAt(0).setBackgroundColor(getResources().getColor(R.color.transperentColorSelected));

        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        work=true;
        ((LinearLayout.LayoutParams)tabHost.getTabWidget().getChildAt(0).getLayoutParams()).weight = 0;
        ((LinearLayout.LayoutParams)tabHost.getTabWidget().getChildAt(1).getLayoutParams()).weight = 0;

        tabHost.getTabWidget().getChildAt(0).getLayoutParams().width = (width/2);
        tabHost.getTabWidget().getChildAt(1).getLayoutParams().width = (width/2);

        x = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        x.setTextColor(getResources().getColor(R.color.selected_text));


        return v;
    }

    // fake content for tabhost
    class FakeContent implements TabHost.TabContentFactory {
        private final Context mContext;

        public FakeContent(Context context) {
            mContext = context;
        }

        @Override
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumHeight(0);
            v.setMinimumWidth(0);
            return v;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initializeViewPager() {
        List<Fragment> fragments = new Vector<Fragment>();

        currentWeather = new CurrentWeather();
        weeklyWeather = new WeeklyWeather();
        weeklyWeather.setInterface(currentWeather);
        currentWeather.setInterface(weeklyWeather);

        fragments.add(currentWeather);
        fragments.add(weeklyWeather);

        this.myViewPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragments);
        this.viewPager = (ViewPager) v.findViewById(R.id.viewPager);
        this.viewPager.setAdapter(this.myViewPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);

    }
    private void initializeTabHost(Bundle args) {

        tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("Current");
        tabSpec.setIndicator("Current");
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);


        tabSpec = tabHost.newTabSpec("Weekly");
        tabSpec.setIndicator("Weekly");
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("");
        tabSpec.setIndicator("");
        tabSpec.setContent(new FakeContent(getActivity()));
        tabHost.addTab(tabSpec);


        tabHost.setOnTabChangedListener(this);
    }
    @Override
    public void onTabChanged(String tabId) {
        int pos = this.tabHost.getCurrentTab();

        if (pos != 2) {

            this.viewPager.setCurrentItem(pos);
            x = (TextView) tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).findViewById(android.R.id.title);
            x.setTextColor(getResources().getColor(R.color.selected_text));

            x = (TextView) tabHost.getTabWidget().getChildAt((tabHost.getCurrentTab() + 1) % 2).findViewById(android.R.id.title);
            x.setTextColor(getResources().getColor(R.color.normal_text));

            previous=pos;
        } else{

            pos = previous;
            this.viewPager.setCurrentItem(previous);
            tabHost.getTabWidget().getChildAt(previous).setPressed(true);
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position != 2)
            this.tabHost.setCurrentTab(position);

    }
}
