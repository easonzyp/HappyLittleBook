package com.zhangyp.develop.HappyLittleBook.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.bean.WalletInfo;
import com.zhangyp.develop.HappyLittleBook.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by zyp on 2019/8/28 0028.
 * class note:
 */

public class WalletListAdapter extends RecyclerView.Adapter<WalletListAdapter.ViewHolder> {

    private Context context;
    private List<WalletInfo> list;
    private OnItemClickListener<WalletInfo> listener;

    public WalletListAdapter(Context context, List<WalletInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wallet_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WalletInfo walletInfo = getItem(position);

        holder.tv_type.setText(walletInfo.getWalletName().substring(0, 1));
        holder.tv_wallet_name.setText(walletInfo.getWalletName());
        holder.tv_money.setText(String.format("￥%s", walletInfo.getMoney()));
        holder.itemView.setOnClickListener(v -> {
            if (listener == null) {
                return;
            }
            listener.onClick(walletInfo, holder.getAdapterPosition());
        });
    }

    private WalletInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_type;
        private TextView tv_wallet_name;
        private TextView tv_money;

        ViewHolder(View itemView) {
            super(itemView);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_wallet_name = itemView.findViewById(R.id.tv_wallet_name);
            tv_money = itemView.findViewById(R.id.tv_money);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<WalletInfo> listener) {
        this.listener = listener;
    }
}
