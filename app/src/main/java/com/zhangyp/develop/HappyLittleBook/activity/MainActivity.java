package com.zhangyp.develop.HappyLittleBook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.adapter.AccountBookInfoAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.util.MyDividerItemDecoration;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;
import com.zhangyp.develop.HappyLittleBook.wight.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DaoSession daoSession;

    private ImageView iv_more;
    private TextView tv_surplus;
    private TextView tv_expense_money;
    private TextView tv_expense_count;
    private TextView tv_income_money;
    private TextView tv_income_count;
    private RecyclerView rv_list;
    private ImageView iv_add_expense;
    private LinearLayout ll_home_expend;
    private LinearLayout ll_home_income;

    private CustomDialog dialogMore;
    private View dialogViewMore;

    private CustomDialog dialogChoose;
    private View dialogViewChoose;
    private LinearLayout ll_add_expend;
    private LinearLayout ll_add_income;

    private TextView tv_expend_cate;
    private TextView tv_income_cate;
    private TextView tv_wallet_account;
    private TextView tv_bill;

    private List<AccountBookInfo> expendList;
    private List<AccountBookInfo> incomeList;

    private List<AccountBookInfo> bookInfoList;
    private AccountBookInfoAdapter adapter;
    private View view_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        daoSession = ((ExampleApplication) getApplication()).getDaoSession();

        iv_more = findViewById(R.id.iv_more);
        tv_surplus = findViewById(R.id.tv_surplus);
        tv_expense_money = findViewById(R.id.tv_expense_money);
        tv_expense_count = findViewById(R.id.tv_expense_count);
        tv_income_money = findViewById(R.id.tv_income_money);
        tv_income_count = findViewById(R.id.tv_income_count);
        rv_list = findViewById(R.id.rv_list);
        iv_add_expense = findViewById(R.id.iv_add_expense);
        ll_home_expend = findViewById(R.id.ll_home_expend);
        ll_home_income = findViewById(R.id.ll_home_income);
        view_empty = findViewById(R.id.view_empty);

        dialogViewMore = LayoutInflater.from(context).inflate(R.layout.home_dialog_view, null);
        dialogViewChoose = LayoutInflater.from(context).inflate(R.layout.home_bottom_dialog_view, null);
        ll_add_expend = dialogViewChoose.findViewById(R.id.ll_add_expend);
        ll_add_income = dialogViewChoose.findViewById(R.id.ll_add_income);

        tv_expend_cate = dialogViewMore.findViewById(R.id.tv_expend_cate);
        tv_income_cate = dialogViewMore.findViewById(R.id.tv_income_cate);
        tv_wallet_account = dialogViewMore.findViewById(R.id.tv_wallet_account);
        tv_bill = dialogViewMore.findViewById(R.id.tv_bill);

        expendList = new ArrayList<>();
        incomeList = new ArrayList<>();

        bookInfoList = new ArrayList<>();
        adapter = new AccountBookInfoAdapter(context, bookInfoList);
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        rv_list.addItemDecoration(new MyDividerItemDecoration(context,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.line)));
        rv_list.setAdapter(adapter);
    }

    private void initData() {
        getAccountBookInfo();
        getBookBaseInfo();
    }

    private void initClick() {
        //更多
        iv_more.setOnClickListener(v -> {
            if (dialogMore == null) {
                dialogMore = new CustomDialog(context, 0, Gravity.CENTER);
            }
            dialogMore.setContentView(dialogViewMore);
            dialogMore.setCancelable(true);
            dialogMore.show();
        });

        //支出分类
        tv_expend_cate.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExpendCateManagerActivity.class);
            startActivity(intent);
            dialogMore.dismiss();
        });

        //收入分类
        tv_income_cate.setOnClickListener(v -> {
            Intent intent = new Intent(context, IncomeCateManagerActivity.class);
            startActivity(intent);
            dialogMore.dismiss();
        });

        //钱包账户
        tv_wallet_account.setOnClickListener(v -> {
            Intent intent = new Intent(context, WalletManagerActivity.class);
            startActivity(intent);
            dialogMore.dismiss();
        });

        //年账单
        tv_bill.setOnClickListener(v -> {
            ToastUtil.showShortToast(context, "账单功能研发中...");
        });

        //添加收支
        iv_add_expense.setOnClickListener(v -> {

            if (dialogChoose == null) {
                dialogChoose = new CustomDialog(context, R.style.pop_win_anim_style, Gravity.BOTTOM);
            }
            dialogChoose.setContentView(dialogViewChoose);
            dialogChoose.setCancelable(true);
            dialogChoose.show();
        });

        ll_add_expend.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddAccountBookActivity.class);
            intent.putExtra("bookType", 0);
            startActivity(intent);
            dialogChoose.dismiss();
        });

        ll_add_income.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddAccountBookActivity.class);
            intent.putExtra("bookType", 1);
            startActivity(intent);
            dialogChoose.dismiss();
        });

        ll_home_expend.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddAccountBookActivity.class);
            intent.putExtra("bookType", 0);
            startActivity(intent);
            dialogChoose.dismiss();
        });

        ll_home_income.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddAccountBookActivity.class);
            intent.putExtra("bookType", 1);
            startActivity(intent);
            dialogChoose.dismiss();
        });
    }

    private void getAccountBookInfo() {
        bookInfoList.clear();
        bookInfoList.addAll(daoSession.queryRaw(AccountBookInfo.class, "order by TIME_STR desc"));

        if (bookInfoList == null || bookInfoList.size() == 0) {
            view_empty.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        } else {
            view_empty.setVisibility(View.GONE);
            rv_list.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    private void getBookBaseInfo() {
        expendList.clear();
        incomeList.clear();
        expendList.addAll(daoSession.queryRaw(AccountBookInfo.class, "where BOOK_TYPE = ?", "0"));
        incomeList.addAll(daoSession.queryRaw(AccountBookInfo.class, "where BOOK_TYPE = ?", "1"));

        double expendMoneyTotal = 0;
        tv_expense_count.setText(String.format("%s笔支出", expendList.size()));
        for (int i = 0; i < expendList.size(); i++) {
            expendMoneyTotal += expendList.get(i).getMoney();
        }
        tv_expense_money.setText(String.format("%s元", String.valueOf(expendMoneyTotal)));

        double incomeMoneyTotal = 0;
        tv_income_count.setText(String.format("%s笔收入", incomeList.size()));
        for (int i = 0; i < incomeList.size(); i++) {
            incomeMoneyTotal += incomeList.get(i).getMoney();
        }
        tv_income_money.setText(String.format("%s元", incomeMoneyTotal));

        double surplus = incomeMoneyTotal - expendMoneyTotal;
        tv_surplus.setText(String.format("%s元", surplus));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
