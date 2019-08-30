package com.zhangyp.develop.HappyLittleBook.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;
import com.zhangyp.develop.HappyLittleBook.bean.ExpendLevelOneCate;
import com.zhangyp.develop.HappyLittleBook.bean.ExpendLevelTwoCate;
import com.zhangyp.develop.HappyLittleBook.bean.IncomeLevelOneCate;
import com.zhangyp.develop.HappyLittleBook.bean.IncomeLevelTwoCate;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.util.BasisTimesUtils;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;
import com.zhangyp.develop.HappyLittleBook.wight.WarpLinearLayout;

import java.util.ArrayList;
import java.util.List;

public class AddAccountBookActivity extends BaseActivity {

    private DaoSession daoSession;

    private TextView tv_back;
    private EditText et_money;
    private TextView tv_time;
    private EditText et_note;
    private WarpLinearLayout wll_one_cate;
    private WarpLinearLayout wll_two_cate;
    private LinearLayout ll_two_cate;
    private TextView tv_save;

    private String timeStr;

    private List<ExpendLevelOneCate> expendOneCateList;
    private List<IncomeLevelOneCate> incomeOneCateList;
    private ExpendLevelOneCate expendOneCate;
    private IncomeLevelOneCate incomeOneCate;
    private List<String> oneCateNameList;

    private List<ExpendLevelTwoCate> expendTwoCateList;
    private List<IncomeLevelTwoCate> incomeTwoCateList;
    private ExpendLevelTwoCate expendTwoCate;
    private IncomeLevelTwoCate incomeTwoCate;
    private List<String> twoCateNameList;

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

        oneCateNameList = new ArrayList<>();
        twoCateNameList = new ArrayList<>();
        tv_back = findViewById(R.id.tv_back);
        et_money = findViewById(R.id.et_money);
        tv_time = findViewById(R.id.tv_time);
        et_note = findViewById(R.id.et_note);
        wll_one_cate = findViewById(R.id.wll_one_cate);
        wll_two_cate = findViewById(R.id.wll_two_cate);
        ll_two_cate = findViewById(R.id.ll_two_cate);
        tv_save = findViewById(R.id.tv_save);

        if (bookType == 0) {
            tv_back.setText("添加支出");
            expendOneCateList = new ArrayList<>();
            expendTwoCateList = new ArrayList<>();
        } else {
            tv_back.setText("添加收入");
            incomeOneCateList = new ArrayList<>();
            incomeTwoCateList = new ArrayList<>();
        }

        tv_time.setText(BasisTimesUtils.getDeviceTime());
    }

    private void initData() {
        if (bookType == 0) {
            getExpendOneCateList();
        } else {
            getIncomeOneCateList();
        }
    }

    private void initClick() {
        tv_back.setOnClickListener(v -> finish());

        wll_one_cate.setMarkClickListener((text, position) -> {
            if (bookType == 0) {
                expendOneCate = expendOneCateList.get(position);
                getExpendTwoCateList(expendOneCate.getId());
            } else {
                incomeOneCate = incomeOneCateList.get(position);
                getIncomeTwoCateList(incomeOneCate.getId());
            }
        });

        wll_two_cate.setMarkClickListener((text, position) -> {
            if (bookType == 0) {
                expendTwoCate = expendTwoCateList.get(position);
            } else {
                incomeTwoCate = incomeTwoCateList.get(position);
            }
        });

        tv_time.setOnClickListener(v -> showDateView());

        tv_save.setOnClickListener(v -> {
            String money = et_money.getText().toString().trim();
            if (TextUtils.isEmpty(money)) {
                ToastUtil.showShortToast(context, "请填写支出金额");
                return;
            }
            if (bookType == 0) {
                if (expendTwoCate == null && expendOneCate == null) {
                    ToastUtil.showShortToast(context, "请选择支出分类");
                    return;
                }
            } else {
                if (incomeTwoCate == null && incomeOneCate == null) {
                    ToastUtil.showShortToast(context, "请选择支出分类");
                    return;
                }
            }

            String cateName;
            if (bookType == 0) {
                if (expendTwoCate != null) {
                    cateName = expendTwoCate.getCateName();
                } else {
                    cateName = expendOneCate.getCateName();
                }
            } else {
                if (incomeTwoCate != null) {
                    cateName = incomeTwoCate.getCateName();
                } else {
                    cateName = incomeOneCate.getCateName();
                }
            }

            AccountBookInfo bookInfo = new AccountBookInfo();
            bookInfo.setMoney(Double.valueOf(money));
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

    private void getExpendOneCateList() {
        expendOneCateList.clear();
        oneCateNameList.clear();
        expendOneCateList.addAll(daoSession.loadAll(ExpendLevelOneCate.class));
        if (expendOneCateList == null || expendOneCateList.size() == 0) {
            return;
        }
        for (int i = 0; i < expendOneCateList.size(); i++) {
            oneCateNameList.add(expendOneCateList.get(i).getCateName());
        }
        wll_one_cate.setData(oneCateNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
    }

    private void getIncomeOneCateList() {
        incomeOneCateList.clear();
        oneCateNameList.clear();
        incomeOneCateList.addAll(daoSession.loadAll(IncomeLevelOneCate.class));
        if (incomeOneCateList == null || incomeOneCateList.size() == 0) {
            return;
        }
        for (int i = 0; i < incomeOneCateList.size(); i++) {
            oneCateNameList.add(incomeOneCateList.get(i).getCateName());
        }
        wll_one_cate.setData(oneCateNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
    }

    private void getExpendTwoCateList(long oneCateId) {
        expendTwoCate = null;
        expendTwoCateList.clear();
        twoCateNameList.clear();
        expendTwoCateList.addAll(daoSession.queryRaw(ExpendLevelTwoCate.class, "where ONE_CATE_ID = ?", String.valueOf(oneCateId)));
        if (expendTwoCateList == null || expendTwoCateList.size() == 0) {
            ll_two_cate.setVisibility(View.GONE);
            return;
        }
        ll_two_cate.setVisibility(View.VISIBLE);
        for (int i = 0; i < expendTwoCateList.size(); i++) {
            twoCateNameList.add(expendTwoCateList.get(i).getCateName());
        }
        wll_two_cate.setData(twoCateNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
    }

    private void getIncomeTwoCateList(long oneCateId) {
        incomeTwoCate = null;
        incomeTwoCateList.clear();
        twoCateNameList.clear();
        incomeTwoCateList.addAll(daoSession.queryRaw(IncomeLevelTwoCate.class, "where ONE_CATE_ID = ?", String.valueOf(oneCateId)));
        if (incomeTwoCateList == null || incomeTwoCateList.size() == 0) {
            ll_two_cate.setVisibility(View.GONE);
            return;
        }
        ll_two_cate.setVisibility(View.VISIBLE);
        for (int i = 0; i < incomeTwoCateList.size(); i++) {
            twoCateNameList.add(incomeTwoCateList.get(i).getCateName());
        }
        wll_two_cate.setData(twoCateNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
    }

    private void showDateView() {
        BasisTimesUtils.showDatePickerDialog(context, "选择日期", new BasisTimesUtils.OnDatePickerListener() {
            @Override
            public void onConfirm(int year, int month, int dayOfMonth) {
                timeStr = year + "年" + month + "月" + dayOfMonth + "日 ";
                showTimeView();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    private void showTimeView() {
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
    public int getLayoutId() {
        return R.layout.activity_add_account_book;
    }
}
