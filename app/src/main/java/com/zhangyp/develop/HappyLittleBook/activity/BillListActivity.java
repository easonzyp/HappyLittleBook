package com.zhangyp.develop.HappyLittleBook.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.adapter.ViewPagerAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.fragment.BillListFragment;

import java.lang.reflect.Field;

/**
 * Created by zyp on 2019/8/20 0020.
 * class note:账单列表
 */

public class BillListActivity extends BaseActivity {

    private TextView tv_back;
    private TabLayout tl_main_tab;
    private ViewPager vp_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initFragment();
        initClick();
    }

    private void initView() {
        tv_back = findViewById(R.id.tv_back);
        tl_main_tab = findViewById(R.id.tl_main_tab);
        vp_main = findViewById(R.id.vp_main);
    }

    private void initFragment() {
        BillListFragment published = new BillListFragment();
        Bundle publishedBundle = new Bundle();
        publishedBundle.putInt("bookType", 2);
        published.setArguments(publishedBundle);

        BillListFragment waitPublished = new BillListFragment();
        Bundle waitPublishedBundle = new Bundle();
        waitPublishedBundle.putInt("bookType", 0);
        waitPublished.setArguments(waitPublishedBundle);

        BillListFragment unqualified = new BillListFragment();
        Bundle unqualifiedBundle = new Bundle();
        unqualifiedBundle.putInt("bookType", 1);
        unqualified.setArguments(unqualifiedBundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(published, "全部");
        adapter.addFragment(waitPublished, "支出");
        adapter.addFragment(unqualified, "收入");

        vp_main.setAdapter(adapter);
        tl_main_tab.setupWithViewPager(vp_main);
        tl_main_tab.post(() -> setIndicator(tl_main_tab));
    }

    private void initClick() {
        tv_back.setOnClickListener(v -> finish());
    }

    public void setIndicator(TabLayout tabs) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        if (tabStrip != null) {
            tabStrip.setAccessible(true);
        }
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) (tabStrip != null ? tabStrip.get(tabs) : null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < (llTab != null ? llTab.getChildCount() : 0); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_bill_list;
    }
}
