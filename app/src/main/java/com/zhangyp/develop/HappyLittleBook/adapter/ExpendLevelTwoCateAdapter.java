package com.zhangyp.develop.HappyLittleBook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.bean.ExpendLevelOneCate;
import com.zhangyp.develop.HappyLittleBook.bean.ExpendLevelTwoCate;
import com.zhangyp.develop.HappyLittleBook.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by zyp on 2019/8/28 0028.
 * class note:
 */

public class ExpendLevelTwoCateAdapter extends RecyclerView.Adapter<ExpendLevelTwoCateAdapter.ViewHolder> {

    private Context context;
    private List<ExpendLevelTwoCate> list;
    private OnItemClickListener<ExpendLevelTwoCate> listener;

    public ExpendLevelTwoCateAdapter(Context context, List<ExpendLevelTwoCate> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_two_cate_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ExpendLevelTwoCate twoCate = getItem(position);
        holder.tv_top.setText(twoCate.getCateName().substring(0, 1));
        holder.tv_item.setText(twoCate.getCateName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(twoCate,holder.getAdapterPosition());
            }
        });
    }

    private ExpendLevelTwoCate getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_top;
        private TextView tv_item;

        ViewHolder(View itemView) {
            super(itemView);
            tv_top = itemView.findViewById(R.id.tv_top);
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }

    public void setOnItemClickListener(OnItemClickListener<ExpendLevelTwoCate> listener) {
        this.listener = listener;
    }
}
