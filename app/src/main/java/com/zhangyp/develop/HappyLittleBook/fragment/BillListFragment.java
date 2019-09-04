package com.zhangyp.develop.HappyLittleBook.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.activity.ModifyAccountBookActivity;
import com.zhangyp.develop.HappyLittleBook.adapter.AccountBookInfoAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseFragment;
import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.dialog.HomeBookDetailDialog;
import com.zhangyp.develop.HappyLittleBook.dialog.TwoButtonCenterDialog;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class BillListFragment extends BaseFragment {
    private DaoSession daoSession;
    private RecyclerView rv_list;
    private View view_empty;
    private List<AccountBookInfo> bookInfoList;
    ;
    private AccountBookInfoAdapter adapter;
    private HomeBookDetailDialog bookDetailDialog;
    private TwoButtonCenterDialog buttonCenterDialog;
    private boolean isFirst = true;

    private int bookType;

    @Override
    protected void initParams() {
        super.initParams();
        bookType = getArguments().getInt("bookType", 2);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        daoSession = ((ExampleApplication) getActivity().getApplication()).getDaoSession();
        rv_list = view.findViewById(R.id.rv_list);
        view_empty = view.findViewById(R.id.view_empty);
        bookInfoList = new ArrayList<>();
        adapter = new AccountBookInfoAdapter(getActivity(), bookInfoList);
        rv_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_list.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getAccountBookInfo();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 相当于onResume()方法--获取焦点
            if (isFirst) {
                isFirst = false;
            } else {
                getAccountBookInfo();
            }
        }
    }

    @Override
    protected void initClick() {
        adapter.setOnItemClickListener((bean, position) -> showBookDetailDialog(bean));
    }

    private void showBookDetailDialog(AccountBookInfo bean) {
        if (bookDetailDialog == null) {
            bookDetailDialog = new HomeBookDetailDialog(getActivity());
            bookDetailDialog.setOnBtnClickListener(listener);
        }
        bookDetailDialog.setCancelable(true);
        bookDetailDialog.setData(bean);
        bookDetailDialog.show();
    }

    private void getAccountBookInfo() {
        bookInfoList.clear();
        String where;
        if (bookType == 0) {
            where = "where BOOK_TYPE = 0 order by TIME_STR desc";
        } else if (bookType == 1) {
            where = "where BOOK_TYPE = 1 order by TIME_STR desc";
        } else {
            where = "order by TIME_STR desc";
        }
        bookInfoList.addAll(daoSession.queryRaw(AccountBookInfo.class, where));

        if (bookInfoList == null || bookInfoList.size() == 0) {
            view_empty.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.GONE);
        } else {
            view_empty.setVisibility(View.GONE);
            rv_list.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }

    private HomeBookDetailDialog.OnBtnClickListener listener = new HomeBookDetailDialog.OnBtnClickListener() {
        @Override
        public void onLeftClick(AccountBookInfo bean) {
            showConfirmationDialog(bean);
        }

        @Override
        public void onRightClick(AccountBookInfo bean) {
            Intent intent = new Intent(getActivity(), ModifyAccountBookActivity.class);
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
            buttonCenterDialog = new TwoButtonCenterDialog(getActivity());
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
        ToastUtil.showShortToast(getActivity(), "删除成功");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bill_list;
    }
}
