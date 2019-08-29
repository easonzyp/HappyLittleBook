package com.zhangyp.develop.HappyLittleBook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.wight.CustomDialog;

public class MainActivity extends BaseActivity {

    private TextView tv_app_name;
    private ImageView iv_more;
    private TextView tv_surplus;
    private TextView tv_month;
    private TextView tv_expense_money;
    private TextView tv_expense_count;
    private View view_line;
    private TextView tv_income_money;
    private TextView tv_income_count;
    private ConstraintLayout cl_top;
    private RecyclerView rv_list;
    private ImageView iv_empty;
    private ImageView iv_add_expense;

    private CustomDialog dialog;
    private View dialogView;
    private TextView tv_expend_cate;
    private TextView tv_income_cate;
    private TextView tv_wallet_account;
    private TextView tv_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initClick();
    }

    private void initView() {
        tv_app_name = findViewById(R.id.tv_app_name);
        iv_more = findViewById(R.id.iv_more);
        tv_surplus = findViewById(R.id.tv_surplus);
        tv_month = findViewById(R.id.tv_month);
        tv_expense_money = findViewById(R.id.tv_expense_money);
        tv_expense_count = findViewById(R.id.tv_expense_count);
        view_line = findViewById(R.id.view_line);
        tv_income_money = findViewById(R.id.tv_income_money);
        tv_income_count = findViewById(R.id.tv_income_count);
        cl_top = findViewById(R.id.cl_top);
        rv_list = findViewById(R.id.rv_list);
        iv_empty = findViewById(R.id.iv_empty);
        iv_add_expense = findViewById(R.id.iv_add_expense);

        dialogView = LayoutInflater.from(context).inflate(R.layout.home_dialog_view, null);
        tv_expend_cate = dialogView.findViewById(R.id.tv_expend_cate);
        tv_income_cate = dialogView.findViewById(R.id.tv_income_cate);
        tv_wallet_account = dialogView.findViewById(R.id.tv_wallet_account);
        tv_bill = dialogView.findViewById(R.id.tv_bill);
    }

    private void initClick() {
        //更多
        iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog == null) {
                    dialog = new CustomDialog(context);
                }
                dialog.setContentView(dialogView);
                dialog.setCancelable(true);
                dialog.show();
            }
        });

        //支出分类
        tv_expend_cate.setOnClickListener(v -> {
            Intent intent = new Intent(context, ExpendCateManagerActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        //收入分类
        tv_income_cate.setOnClickListener(v -> {
            Intent intent = new Intent(context, IncomeCateManagerActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        //钱包账户
        tv_wallet_account.setOnClickListener(v -> {

        });

        //年账单
        tv_bill.setOnClickListener(v -> {

        });

        //添加
        iv_add_expense.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddExpenseActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
