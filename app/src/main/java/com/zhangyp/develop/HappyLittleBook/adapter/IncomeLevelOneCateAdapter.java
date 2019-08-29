package com.zhangyp.develop.HappyLittleBook.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.bean.IncomeLevelOneCate;

import java.util.List;

/**
 * Created by zyp on 2019/8/28 0028.
 * class note:
 */

public class IncomeLevelOneCateAdapter extends BaseAdapter {

    private Context context;
    private List<IncomeLevelOneCate> list;
    private int selectorPosition;

    public IncomeLevelOneCateAdapter(Context context, List<IncomeLevelOneCate> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public IncomeLevelOneCate getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_one_cate_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder); //绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
        }

        IncomeLevelOneCate oneCate = getItem(position);
        holder.tv_item.setText(oneCate.getCateName());

        if (selectorPosition == position) {
            holder.tv_item.setBackgroundResource(R.color.mainColor);
            holder.tv_item.setTextColor(ContextCompat.getColor(context, R.color.white));
        } else {
            //其他的恢复原来的状态
            holder.tv_item.setBackgroundResource(R.color.white);
            holder.tv_item.setTextColor(ContextCompat.getColor(context, R.color.maintextcolor));
        }


        return convertView;
    }

    public void changeState(int pos) {
        this.selectorPosition = pos;
        notifyDataSetChanged();
    }

    class ViewHolder {

        private TextView tv_item;

        ViewHolder(View itemView) {
            tv_item = itemView.findViewById(R.id.tv_item);
        }
    }
}
