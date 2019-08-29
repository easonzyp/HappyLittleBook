package com.zhangyp.develop.HappyLittleBook.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;
import com.zhangyp.develop.HappyLittleBook.wight.WarpLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseActivity extends BaseActivity {

    private TextView tv_back;
    private RelativeLayout rl_title;
    private EditText et_money;
    private TextView tv_time;
    private EditText et_note;
    private WarpLinearLayout wll_one_cate;
    private WarpLinearLayout wll_two_cate;
    private LinearLayout ll_two_cate;
    private TextView tv_save;

    private List<String> oneCateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initClick();
    }

    private void initView() {
        tv_back = findViewById(R.id.tv_back);
        rl_title = findViewById(R.id.rl_title);
        et_money = findViewById(R.id.et_money);
        tv_time = findViewById(R.id.tv_time);
        et_note = findViewById(R.id.et_note);
        wll_one_cate = findViewById(R.id.wll_one_cate);
        wll_two_cate = findViewById(R.id.wll_two_cate);
        ll_two_cate = findViewById(R.id.ll_two_cate);
        tv_save = findViewById(R.id.tv_save);
    }

    private void initData() {
        oneCateList = new ArrayList<>();
        oneCateList.add("默认");
        oneCateList.add("娱乐");
        oneCateList.add("出行");
        oneCateList.add("水果");
        oneCateList.add("休闲食品");
        oneCateList.add("理发");
        oneCateList.add("生活用品");
        oneCateList.add("其他");

        wll_one_cate.setData(oneCateList, context, 13, 10, 5, 10, 5, 0, 10, 10, 0);

    }

    private void initClick() {
        wll_one_cate.setMarkClickListener(new WarpLinearLayout.MarkClickListener() {
            @Override
            public void clickMark(String text, int position) {
                ToastUtil.showShortToast(context, text);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_expense;
    }

}
