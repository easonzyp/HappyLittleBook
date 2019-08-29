package com.zhangyp.develop.HappyLittleBook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
    private ImageView iv_btn1;
    private ImageView iv_btn2;
    private ConstraintLayout cl_function;
    private View view_mask;

    private boolean isBtnShow = false;
    private AlphaAnimation showAnimation;
    private AlphaAnimation hideAnimation;
    private final int DURATION_TIME = 250;

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
        iv_btn1 = findViewById(R.id.iv_btn1);
        iv_btn2 = findViewById(R.id.iv_btn2);
        cl_function = findViewById(R.id.cl_function);
        view_mask = findViewById(R.id.view_mask);

        cl_function.setVisibility(View.INVISIBLE);

    }

    private void initClick() {
        //更多
        iv_more.setOnClickListener(v -> {
            if (dialog == null) {
                dialog = new CustomDialog(context);
            }
            dialog.setContentView(dialogView);
            dialog.setCancelable(true);
            dialog.show();
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

            if (isBtnShow) {
                //隐藏
                setHideAnimation(cl_function, DURATION_TIME);

            } else {
                //显示
                setShowAnimation(cl_function, DURATION_TIME);

            }
        });

        view_mask.setOnClickListener(v -> setHideAnimation(cl_function, DURATION_TIME));

        iv_btn1.setOnClickListener(v -> {
            setHideAnimation(cl_function, DURATION_TIME);
            Intent intent = new Intent(context, AddAccountBookActivity.class);
            intent.putExtra("bookType", 0);
            startActivity(intent);
        });

        iv_btn2.setOnClickListener(v -> {
            setHideAnimation(cl_function, DURATION_TIME);
            Intent intent = new Intent(context, AddAccountBookActivity.class);
            intent.putExtra("bookType", 1);
            startActivity(intent);
        });
    }

    public void setHideAnimation(final View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }

        if (null != hideAnimation) {
            hideAnimation.cancel();
        }
        view_mask.setVisibility(View.GONE);
        isBtnShow = false;
        // 监听动画结束的操作
        hideAnimation = new AlphaAnimation(1.0f, 0.0f);
        hideAnimation.setDuration(duration);
        hideAnimation.setFillAfter(true);
        hideAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {

            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                view.setVisibility(View.GONE);
            }
        });
        view.startAnimation(hideAnimation);
    }

    public void setShowAnimation(final View view, int duration) {
        if (null == view || duration < 0) {
            return;
        }
        if (null != showAnimation) {
            showAnimation.cancel();
        }
        view_mask.setVisibility(View.VISIBLE);
        isBtnShow = true;
        showAnimation = new AlphaAnimation(0.0f, 1.0f);
        showAnimation.setDuration(duration);
        showAnimation.setFillAfter(true);
        showAnimation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {

            }
        });
        view.startAnimation(showAnimation);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
