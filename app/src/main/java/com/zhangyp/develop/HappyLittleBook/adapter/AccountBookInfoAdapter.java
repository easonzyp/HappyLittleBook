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
import com.zhangyp.develop.HappyLittleBook.util.BasisTimesUtils;

import java.util.List;

/**
 * Created by zyp on 2019/8/28 0028.
 * class note:
 */

public class AccountBookInfoAdapter extends RecyclerView.Adapter<AccountBookInfoAdapter.ViewHolder> {

    private Context context;
    private List<AccountBookInfo> list;
    private OnItemClickListener<AccountBookInfo> listener;
    private AccountBookInfo preBookInfo;

    public AccountBookInfoAdapter(Context context, List<AccountBookInfo> list) {
        this.context = context;
        this.list = list;
        preBookInfo = new AccountBookInfo();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_account_book_info_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        AccountBookInfo bookInfo = getItem(position);
        String currentTime = bookInfo.getTimeStr();
        String currentTimeTemp = BasisTimesUtils.getMonthAndDay(currentTime);

        holder.tv_cate.setText(bookInfo.getCateStr());
        holder.tv_time.setText(currentTime);
        if (bookInfo.getBookType() == 0) {
            holder.tv_type.setText("支");
            holder.tv_type.setBackgroundResource(R.drawable.home_logo_type_corner_bg);
            holder.tv_money.setText(String.format("-%s", bookInfo.getMoney()));
            holder.tv_money.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            holder.tv_type.setText("收");
            holder.tv_type.setBackgroundResource(R.drawable.two_cate_top_corner_bg);
            holder.tv_money.setText(String.format("+%s", bookInfo.getMoney()));
            holder.tv_money.setTextColor(ContextCompat.getColor(context, R.color.mainColor));
        }

        if (isItemHeader(position)) {
            holder.tv_title_time.setVisibility(View.VISIBLE);
            holder.tv_title_time.setText(currentTimeTemp);
        } else {
            holder.tv_title_time.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener == null) {
                return;
            }
            listener.onClick(bookInfo, holder.getAdapterPosition());
        });
    }

    private boolean isItemHeader(int position) {
        if (position == 0) {
            return true;
        }
        AccountBookInfo preBookInfo = getItem(position - 1);
        AccountBookInfo bookInfo = getItem(position);

        String preTime = BasisTimesUtils.getMonthAndDay(preBookInfo.getTimeStr());
        String currentTime = BasisTimesUtils.getMonthAndDay(bookInfo.getTimeStr());
        return !preTime.equals(currentTime);
    }

    private AccountBookInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_title_time;
        private TextView tv_type;
        private TextView tv_cate;
        private TextView tv_time;
        private TextView tv_money;

        ViewHolder(View itemView) {
            super(itemView);
            tv_title_time = itemView.findViewById(R.id.tv_title_time);
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
