package com.zhangyp.develop.HappyLittleBook.activity;

import android.content.Intent;
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
import com.zhangyp.develop.HappyLittleBook.bean.WalletInfo;
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
    private TextView tv_wallet_tips;
    private TextView tv_wallet_manager;
    private TextView tv_cate_tips;
    private TextView tv_cate_manager;
    private WarpLinearLayout wll_wallet;
    private LinearLayout ll_cate_main;
    private WarpLinearLayout wll_one_cate;
    private WarpLinearLayout wll_two_cate;
    private LinearLayout ll_two_cate;
    private TextView tv_save;

    private String timeStr;

    private List<WalletInfo> walletInfoList;
    private List<String> walletNameList;
    private String walletName;

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

    private AccountBookInfo bean;
    private int bookType;   //0:支出  1:收入

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntentData();
        initView();
        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initIntentData() {
        Intent intent = getIntent();
        bean = (AccountBookInfo) intent.getSerializableExtra("bookInfo");
        bookType = intent.getIntExtra("bookType", 0);
    }

    private void initView() {
        daoSession = ((ExampleApplication) getApplication()).getDaoSession();

        tv_back = findViewById(R.id.tv_back);
        et_money = findViewById(R.id.et_money);
        tv_time = findViewById(R.id.tv_time);
        et_note = findViewById(R.id.et_note);
        tv_wallet_tips = findViewById(R.id.tv_wallet_tips);
        tv_wallet_manager = findViewById(R.id.tv_wallet_manager);
        tv_cate_tips = findViewById(R.id.tv_cate_tips);
        tv_cate_manager = findViewById(R.id.tv_cate_manager);
        wll_wallet = findViewById(R.id.wll_wallet);
        ll_cate_main = findViewById(R.id.ll_cate_main);
        wll_one_cate = findViewById(R.id.wll_one_cate);
        wll_two_cate = findViewById(R.id.wll_two_cate);
        ll_two_cate = findViewById(R.id.ll_two_cate);
        tv_save = findViewById(R.id.tv_save);

        walletInfoList = new ArrayList<>();
        walletNameList = new ArrayList<>();
        oneCateNameList = new ArrayList<>();
        twoCateNameList = new ArrayList<>();
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
        getWalletList();
        if (bookType == 0) {
            getExpendOneCateList();
        } else {
            getIncomeOneCateList();
        }
    }

    private void initClick() {
        tv_back.setOnClickListener(v -> finish());

        wll_wallet.setMarkClickListener((text, position) -> walletName = text);

        tv_wallet_manager.setOnClickListener(v -> startActivity(new Intent(context, AddWalletActivity.class)));

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

        tv_cate_manager.setOnClickListener(v -> {
            if (bookType == 0) {
                //跳转到添加支出
                startActivity(new Intent(context, ExpendCateManagerActivity.class));
            } else {
                //跳转到添加收入
                startActivity(new Intent(context, IncomeCateManagerActivity.class));
            }
        });

        tv_time.setOnClickListener(v -> showDateView());

        tv_save.setOnClickListener(v -> {
            String money = et_money.getText().toString().trim();
            if (TextUtils.isEmpty(money)) {
                ToastUtil.showShortToast(context, "请填写支出金额");
                return;
            }

            if (TextUtils.isEmpty(walletName)) {
                ToastUtil.showShortToast(context, "请选择钱包");
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
                bookInfo.setNoteStr("未添加备注");
            } else {
                bookInfo.setNoteStr(note);
            }
            bookInfo.setCateStr(cateName);
            bookInfo.setWalletType(walletName);
            bookInfo.setBookType(bookType);

            daoSession.insert(bookInfo);
            finish();
            ToastUtil.showShortToast(context, "保存成功");
        });
    }

    private void getWalletList() {
        walletName = null;
        walletInfoList.clear();
        walletNameList.clear();
        walletInfoList.addAll(daoSession.loadAll(WalletInfo.class));
        if (walletInfoList.size() == 0) {
            tv_wallet_tips.setVisibility(View.VISIBLE);
            wll_wallet.setVisibility(View.GONE);
            return;
        }
        tv_wallet_tips.setVisibility(View.GONE);
        wll_wallet.setVisibility(View.VISIBLE);
        for (int i = 0; i < walletInfoList.size(); i++) {
            walletNameList.add(walletInfoList.get(i).getWalletName());
        }
        wll_wallet.setData(walletNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
    }

    private void getExpendOneCateList() {
        expendOneCate = null;
        expendOneCateList.clear();
        oneCateNameList.clear();
        expendOneCateList.addAll(daoSession.loadAll(ExpendLevelOneCate.class));
        if (expendOneCateList.size() == 0) {
            tv_cate_tips.setVisibility(View.VISIBLE);
            ll_cate_main.setVisibility(View.GONE);
            return;
        }
        tv_cate_tips.setVisibility(View.GONE);
        ll_cate_main.setVisibility(View.VISIBLE);
        for (int i = 0; i < expendOneCateList.size(); i++) {
            oneCateNameList.add(expendOneCateList.get(i).getCateName());
        }
        wll_one_cate.setData(oneCateNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
        getExpendTwoCateList(-1);
    }

    private void getIncomeOneCateList() {
        incomeOneCate = null;
        incomeOneCateList.clear();
        oneCateNameList.clear();
        incomeOneCateList.addAll(daoSession.loadAll(IncomeLevelOneCate.class));
        if (incomeOneCateList.size() == 0) {
            tv_cate_tips.setVisibility(View.VISIBLE);
            ll_cate_main.setVisibility(View.GONE);
            return;
        }
        tv_cate_tips.setVisibility(View.GONE);
        ll_cate_main.setVisibility(View.VISIBLE);
        for (int i = 0; i < incomeOneCateList.size(); i++) {
            oneCateNameList.add(incomeOneCateList.get(i).getCateName());
        }
        wll_one_cate.setData(oneCateNameList, context, 13, 10, 5, 10, 5, 0, 5, 10, 5);
        getIncomeTwoCateList(-1);
    }

    private void getExpendTwoCateList(long oneCateId) {
        expendTwoCate = null;
        expendTwoCateList.clear();
        twoCateNameList.clear();
        expendTwoCateList.addAll(daoSession.queryRaw(ExpendLevelTwoCate.class, "where ONE_CATE_ID = ?", String.valueOf(oneCateId)));
        if (expendTwoCateList.size() == 0) {
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
        if (incomeTwoCateList.size() == 0) {
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
