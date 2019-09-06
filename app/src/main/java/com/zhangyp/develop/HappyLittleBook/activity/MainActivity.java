package com.zhangyp.develop.HappyLittleBook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.RelativeGuide;
import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.adapter.AccountBookInfoAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.dialog.CustomDialog;
import com.zhangyp.develop.HappyLittleBook.dialog.HomeBookDetailDialog;
import com.zhangyp.develop.HappyLittleBook.dialog.TwoButtonCenterDialog;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private DaoSession daoSession;

    private ImageView iv_more;

    private TextView tv_app_name;
    private LinearLayout ll_surplus;
    private TextView tv_surplus;
    private TextView tv_expense_money;
    private TextView tv_expense_count;
    private TextView tv_income_money;
    private TextView tv_income_count;
    private RecyclerView rv_list;
    private LinearLayout ll_empty;
    private ImageView iv_home_add;
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

    private HomeBookDetailDialog bookDetailDialog;
    private TwoButtonCenterDialog buttonCenterDialog;

    private List<AccountBookInfo> expendList;
    private List<AccountBookInfo> incomeList;

    private List<AccountBookInfo> bookInfoList;
    private AccountBookInfoAdapter adapter;
    private LinearLayoutManager layoutManager;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initClick();
        initNewbieGuide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initView() {
        daoSession = ((ExampleApplication) getApplication()).getDaoSession();

        tv_app_name = findViewById(R.id.tv_app_name);
        iv_more = findViewById(R.id.iv_more);
        View view_main_top = findViewById(R.id.view_main_top);
        ll_surplus = view_main_top.findViewById(R.id.ll_surplus);
        tv_surplus = view_main_top.findViewById(R.id.tv_surplus);
        tv_expense_money = view_main_top.findViewById(R.id.tv_expense_money);
        tv_expense_count = view_main_top.findViewById(R.id.tv_expense_count);
        tv_income_money = view_main_top.findViewById(R.id.tv_income_money);
        tv_income_count = view_main_top.findViewById(R.id.tv_income_count);

        ll_home_expend = view_main_top.findViewById(R.id.ll_home_expend);
        ll_home_income = view_main_top.findViewById(R.id.ll_home_income);

        rv_list = findViewById(R.id.rv_list);
        ll_empty = findViewById(R.id.ll_empty);
        iv_home_add = findViewById(R.id.iv_home_add);

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
        layoutManager = new LinearLayoutManager(context);
        rv_list.setLayoutManager(layoutManager);
        /*rv_list.addItemDecoration(new MyDividerItemDecoration(context,
                LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.line)));*/
        rv_list.setAdapter(adapter);
    }

    private void initData() {
        getAccountBookInfo();
        getBookBaseInfo();
    }

    private void initClick() {
        tv_app_name.setOnClickListener(v -> startActivity(new Intent(context, AppInfoActivity.class)));

        ll_surplus.setOnClickListener(v -> startActivity(new Intent(context, BillListActivity.class)));

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
            startActivity(new Intent(context, BillListActivity.class));
            dialogMore.dismiss();
        });

        //添加收支
        iv_home_add.setOnClickListener(v -> {

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
        });

        ll_home_income.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddAccountBookActivity.class);
            intent.putExtra("bookType", 1);
            startActivity(intent);
        });

        adapter.setOnItemClickListener((bean, position) -> showBookDetailDialog(bean));
    }

    private void getAccountBookInfo() {
        bookInfoList.clear();
        bookInfoList.addAll(daoSession.queryRaw(AccountBookInfo.class, "order by TIME_STR desc"));

        if (bookInfoList == null || bookInfoList.size() == 0) {
            ll_empty.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        } else {
            ll_empty.setVisibility(View.GONE);
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

    private void showBookDetailDialog(AccountBookInfo bean) {
        if (bookDetailDialog == null) {
            bookDetailDialog = new HomeBookDetailDialog(context);
            bookDetailDialog.setOnBtnClickListener(listener);
        }
        bookDetailDialog.setCancelable(true);
        bookDetailDialog.setData(bean);
        bookDetailDialog.show();
    }

    private HomeBookDetailDialog.OnBtnClickListener listener = new HomeBookDetailDialog.OnBtnClickListener() {
        @Override
        public void onLeftClick(AccountBookInfo bean) {
            showConfirmationDialog(bean);
        }

        @Override
        public void onRightClick(AccountBookInfo bean) {
            Intent intent = new Intent(context, ModifyAccountBookActivity.class);
            intent.putExtra("bookInfo", bean);
            startActivity(intent);
        }
    };

    private void showConfirmationDialog(AccountBookInfo bean) {
        String tips;
        if (bean.getBookType() == 0) {
            tips = "确定要删除该笔支出吗";
        } else {
            tips = "确定要删除该笔收入吗";
        }
        if (buttonCenterDialog == null) {
            buttonCenterDialog = new TwoButtonCenterDialog(context);
        }
        buttonCenterDialog.setTips(tips);
        buttonCenterDialog.show();
        buttonCenterDialog.setOnClickRateDialog(new TwoButtonCenterDialog.OnClickRateDialog() {
            @Override
            public void onClickLeft() {
                buttonCenterDialog.dismiss();
            }

            @Override
            public void onClickRight() {
                deleteBill(bean);
                buttonCenterDialog.dismiss();
            }
        });
    }

    private void deleteBill(AccountBookInfo bean) {
        daoSession.delete(bean);
        initData();
        ToastUtil.showSuccessToast(context, "删除成功");
    }

    private void initNewbieGuide() {
        NewbieGuide.with(this)
                .setLabel("app_info")
                .addGuidePage(GuidePage.newInstance()
                                .addHighLight(tv_app_name, new RelativeGuide(R.layout.newbie_app_info,
                                        Gravity.BOTTOM, 10))
//                        .setLayoutRes(R.layout.newbie_app_info)
                )

                .addGuidePage(GuidePage.newInstance()
                                .addHighLight(iv_more, new RelativeGuide(R.layout.newbie_more,
                                        Gravity.LEFT, 10))
//                        .setLayoutRes(R.layout.newbie_app_info)
                )

                .addGuidePage(GuidePage.newInstance()
                                .addHighLight(ll_surplus, new RelativeGuide(R.layout.newbie_bill_info,
                                        Gravity.BOTTOM, 10))
//                        .setLayoutRes(R.layout.newbie_app_info)
                )

                .addGuidePage(GuidePage.newInstance()
                                .addHighLight(iv_home_add, new RelativeGuide(R.layout.newbie_bottom_add,
                                        Gravity.TOP, 10))
//                        .setLayoutRes(R.layout.newbie_app_info)
                )
                .show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtil.showNormalToast(context, "再次按返回键退出");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
