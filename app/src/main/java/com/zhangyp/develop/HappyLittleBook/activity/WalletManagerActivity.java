package com.zhangyp.develop.HappyLittleBook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.adapter.WalletListAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.WalletInfo;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class WalletManagerActivity extends BaseActivity {

    private DaoSession daoSession;

    private TextView tv_back;
    private ImageView iv_add_wallet;
    private RecyclerView rv_wallet;

    private List<WalletInfo> walletInfoList;
    private WalletListAdapter adapter;

    private View view_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initClick();
    }

    private void initView() {
        daoSession = ((ExampleApplication) getApplication()).getDaoSession();

        tv_back = findViewById(R.id.tv_back);
        iv_add_wallet = findViewById(R.id.iv_add_wallet);
        rv_wallet = findViewById(R.id.rv_wallet);
        view_empty = findViewById(R.id.view_empty);
        TextView tv_tips = view_empty.findViewById(R.id.tv_tips);
        tv_tips.setText("还没有添加过钱包哦~");

        walletInfoList = new ArrayList<>();
        adapter = new WalletListAdapter(context, walletInfoList);
        rv_wallet.setLayoutManager(new LinearLayoutManager(context));
        rv_wallet.setAdapter(adapter);
    }

    private void initData() {
        getWalletList();
    }

    private void initClick() {
        tv_back.setOnClickListener(v -> finish());

        adapter.setOnItemClickListener((obj, position) -> {
            Intent intent = new Intent(context, AddWalletActivity.class);
            intent.putExtra("wallet", obj);
            startActivity(intent);
        });

        iv_add_wallet.setOnClickListener(v -> startActivity(new Intent(context, AddWalletActivity.class)));
    }

    private void getWalletList() {
        walletInfoList.clear();
        walletInfoList.addAll(daoSession.loadAll(WalletInfo.class));

        if (walletInfoList.size() == 0) {
            rv_wallet.setVisibility(View.GONE);
            view_empty.setVisibility(View.VISIBLE);
        } else {
            rv_wallet.setVisibility(View.VISIBLE);
            view_empty.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_wallet_manager;
    }
}
