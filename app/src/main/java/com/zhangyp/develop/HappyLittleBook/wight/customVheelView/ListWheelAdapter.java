package com.zhangyp.develop.HappyLittleBook.wight.customVheelView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListWheelAdapter extends AbstractWheelTextAdapter {

    // items
    private List<ExchangeListResponse.ResultBean.ListBean> items;

    /**
     * Constructor
     *
     * @param context the current context
     * @param items   the items
     */
    public ListWheelAdapter(Context context, List<ExchangeListResponse.ResultBean.ListBean> items) {
        super(context);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            String item = items.get(index).getName();
            if (item != null) {
                return item;
            }
            return null;
        }
        return null;
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        return super.getItem(index, convertView, parent);
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }
}
