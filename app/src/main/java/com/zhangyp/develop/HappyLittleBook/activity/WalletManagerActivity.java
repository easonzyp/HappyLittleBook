package com.zhangyp.develop.HappyLittleBook.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.adapter.WalletListAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.WalletInfo;

import java.util.ArrayList;
import java.util.List;

public class WalletManagerActivity extends BaseActivity {

    private TextView tv_back;
    private ImageView iv_add_wallet;
    private RecyclerView rv_wallet;

    private List<WalletInfo> walletInfoList;
    private WalletListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initClick();
    }

    private void initView() {
        tv_back = findViewById(R.id.tv_back);
        iv_add_wallet = findViewById(R.id.iv_add_wallet);
        rv_wallet = findViewById(R.id.rv_wallet);

        walletInfoList = new ArrayList<>();
        adapter = new WalletListAdapter(context, walletInfoList);
        rv_wallet.setLayoutManager(new LinearLayoutManager(context));

        rv_wallet.setAdapter(adapter);
    }

    private void initData() {

    }

    private void initClick() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_manager;
    }
}
