package com.zhangyp.develop.HappyLittleBook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.bean.IncomeLevelTwoCate;
import com.zhangyp.develop.HappyLittleBook.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by zyp on 2019/8/28 0028.
 * class note:
 */

public class IncomeLevelTwoCateAdapter extends RecyclerView.Adapter<IncomeLevelTwoCateAdapter.ViewHolder> {

    private Context context;
    private List<IncomeLevelTwoCate> list;
    private OnItemClickListener<IncomeLevelTwoCate> listener;

    public IncomeLevelTwoCateAdapter(Context context, List<IncomeLevelTwoCate> list) {
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
        IncomeLevelTwoCate twoCate = getItem(position);
        holder.tv_top.setText(twoCate.getCateName().substring(0, 1));
        holder.tv_item.setText(twoCate.getCateName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(twoCate,holder.getAdapterPosition());
            }
        });
    }

    private IncomeLevelTwoCate getItem(int position) {
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

    public void setOnItemClickListener(OnItemClickListener<IncomeLevelTwoCate> listener) {
        this.listener = listener;
    }
}
