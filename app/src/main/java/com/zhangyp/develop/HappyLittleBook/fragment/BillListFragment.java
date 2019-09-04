package com.zhangyp.develop.HappyLittleBook.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.adapter.AccountBookInfoAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseFragment;
import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.dialog.HomeBookDetailDialog;

import java.util.ArrayList;
import java.util.List;

public class BillListFragment extends BaseFragment {
    private DaoSession daoSession;
    private RecyclerView rv_list;
    private View view_empty;
    private List<AccountBookInfo> bookInfoList;
    private AccountBookInfoAdapter adapter;
    private HomeBookDetailDialog bookDetailDialog;

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
    protected void initClick() {
        adapter.setOnItemClickListener((bean, position) -> showBookDetailDialog(bean));
    }

    private void showBookDetailDialog(AccountBookInfo bean) {
        if (bookDetailDialog == null) {
            bookDetailDialog = new HomeBookDetailDialog(getActivity());
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
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bill_list;
    }
}
