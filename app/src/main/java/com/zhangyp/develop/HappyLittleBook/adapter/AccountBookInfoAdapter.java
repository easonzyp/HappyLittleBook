package com.zhangyp.develop.HappyLittleBook.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;
import com.zhangyp.develop.HappyLittleBook.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by zyp on 2019/8/28 0028.
 * class note:
 */

public class AccountBookInfoAdapter extends RecyclerView.Adapter<AccountBookInfoAdapter.ViewHolder> {

    private Context context;
    private List<AccountBookInfo> list;
    private OnItemClickListener<AccountBookInfo> listener;

    public AccountBookInfoAdapter(Context context, List<AccountBookInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_account_book_info_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccountBookInfo bookInfo = getItem(position);
        holder.tv_type.setText(bookInfo.getWalletType().substring(0, 1));
        holder.tv_cate.setText(bookInfo.getCateStr());
        holder.tv_time.setText(bookInfo.getTimeStr());
        if (bookInfo.getBookType() == 0) {
            holder.tv_money.setText(String.format("-%s", bookInfo.getMoney()));
            holder.tv_money.setTextColor(ContextCompat.getColor(context, R.color.firebrick));
//            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.home_expend_corner_bg));
        } else {
            holder.tv_money.setText(String.format("+%s", bookInfo.getMoney()));
            holder.tv_money.setTextColor(ContextCompat.getColor(context, R.color.darkseagreen));
//            holder.itemView.setBackground(ContextCompat.getDrawable(context, R.drawable.home_income_corner_bg));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null) {
                    return;
                }
                listener.onClick(bookInfo, holder.getAdapterPosition());
            }
        });
    }

    private AccountBookInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_type;
        private TextView tv_cate;
        private TextView tv_time;
        private TextView tv_money;

        ViewHolder(View itemView) {
            super(itemView);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_cate = itemView.findViewById(R.id.tv_cate);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_money = itemView.findViewById(R.id.tv_money);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<AccountBookInfo> listener) {
        this.listener = listener;
    }
}
