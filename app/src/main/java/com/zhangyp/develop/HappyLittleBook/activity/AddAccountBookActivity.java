package com.zhangyp.develop.HappyLittleBook.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;
import com.zhangyp.develop.HappyLittleBook.bean.ExpendLevelOneCate;
import com.zhangyp.develop.HappyLittleBook.bean.ExpendLevelTwoCate;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.util.BasisTimesUtils;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;
import com.zhangyp.develop.HappyLittleBook.wight.WarpLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class AddAccountBookActivity extends BaseActivity {

    private DaoSession daoSession;

    private TextView tv_back;
    private RelativeLayout rl_title;
    private EditText et_money;
    private TextView tv_time;
    private EditText et_note;
    private WarpLinearLayout wll_one_cate;
    private WarpLinearLayout wll_two_cate;
    private LinearLayout ll_two_cate;
    private TextView tv_save;

    private String timeStr;

    private List<ExpendLevelOneCate> oneCateList;
    private List<String> oneCateNameList;
    private ExpendLevelOneCate oneCate;

    private List<ExpendLevelTwoCate> twoCateList;
    private List<String> twoCateNameList;
    private ExpendLevelTwoCate twoCate;

    private int bookType;   //0:支出  1:收入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntentData();
        initView();
        initData();
        initClick();
    }

    private void initIntentData() {
        bookType = getIntent().getIntExtra("bookType", 0);
    }

    private void initView() {
        daoSession = ((ExampleApplication) getApplication()).getDaoSession();
        oneCateList = new ArrayList<>();
        twoCateList = new ArrayList<>();
        oneCateNameList = new ArrayList<>();
        twoCateNameList = new ArrayList<>();
        tv_back = findViewById(R.id.tv_back);
        rl_title = findViewById(R.id.rl_title);
        et_money = findViewById(R.id.et_money);
        tv_time = findViewById(R.id.tv_time);
        et_note = findViewById(R.id.et_note);
        wll_one_cate = findViewById(R.id.wll_one_cate);
        wll_two_cate = findViewById(R.id.wll_two_cate);
        ll_two_cate = findViewById(R.id.ll_two_cate);
        tv_save = findViewById(R.id.tv_save);

        if (bookType == 0) {
            tv_back.setText("添加支出");
        } else {
            tv_back.setText("添加收入");
        }

        tv_time.setText(BasisTimesUtils.getDeviceTime());
    }

    private void initData() {
        getOneCateList();
    }

    private void initClick() {
        wll_one_cate.setMarkClickListener((text, position) -> {
            oneCate = oneCateList.get(position);
            getTwoCateList(oneCate.getId());
        });

        wll_two_cate.setMarkClickListener((text, position) -> twoCate = twoCateList.get(position));

        tv_time.setOnClickListener(v -> BasisTimesUtils.showDatePickerDialog(context, "选择日期", new BasisTimesUtils.OnDatePickerListener() {
            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                timeStr = year + "年" + month + "月" + dayOfMonth + "日 ";
                BasisTimesUtils.showTimerPickerDialog(context, "选择时间", true, new BasisTimesUtils.OnTimerPickerListener() {
                    @Override
                    public void onConfirm(int hourOfDay, int minute) {
                        timeStr += hourOfDay + ":" + minute;
                        tv_time.setText(timeStr);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }

            @Override
            public void onCancel() {

            }
        }));

        tv_save.setOnClickListener(v -> {
            String money = et_money.getText().toString().trim();
            if (TextUtils.isEmpty(money)) {
                ToastUtil.showShortToast(context, "请填写支出金额");
                return;
            }

            if (twoCate == null && oneCate == null) {
                ToastUtil.showShortToast(context, "请选择支出分类");
                return;
            }
            String cateName;
            if (twoCate != null) {
                cateName = twoCate.getCateName();
            } else {
                cateName = oneCate.getCateName();
            }

            AccountBookInfo bookInfo = new AccountBookInfo();
            if (bookType == 0) {
                //支出
                bookInfo.setMoney(-Double.valueOf(money));
            } else {
                //收入
                bookInfo.setMoney(Double.valueOf(money));
            }
            bookInfo.setMoney(Double.valueOf(money));
            bookInfo.setTimeStr(tv_time.getText().toString());
            String note = et_note.getText().toString().trim();
            if (TextUtils.isEmpty(note)) {
                bookInfo.setNoteStr("空");
            } else {
                bookInfo.setNoteStr(note);
            }
            bookInfo.setCateStr(cateName);
            bookInfo.setWalletType("现金");
            bookInfo.setBookType(bookType);

            daoSession.insert(bookInfo);
            finish();
            ToastUtil.showShortToast(context, "保存成功");
        });
    }

    private void getOneCateList() {
        oneCateList.clear();
        oneCateNameList.clear();
        oneCateList.addAll(daoSession.loadAll(ExpendLevelOneCate.class));
        if (oneCateList == null || oneCateList.size() == 0) {
            return;
        }
        for (int i = 0; i < oneCateList.size(); i++) {
            oneCateNameList.add(oneCateList.get(i).getCateName());
        }
        wll_one_cate.setData(oneCateNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
    }

    private void getTwoCateList(long oneCateId) {
        twoCate = null;
        twoCateList.clear();
        twoCateNameList.clear();
        twoCateList.addAll(daoSession.queryRaw(ExpendLevelTwoCate.class, "where ONE_CATE_ID = ?", String.valueOf(oneCateId)));
        if (twoCateList == null || twoCateList.size() == 0) {
            ll_two_cate.setVisibility(View.GONE);
            return;
        }
        ll_two_cate.setVisibility(View.VISIBLE);
        for (int i = 0; i < twoCateList.size(); i++) {
            twoCateNameList.add(twoCateList.get(i).getCateName());
        }
        wll_two_cate.setData(twoCateNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_account_book;
    }
}
