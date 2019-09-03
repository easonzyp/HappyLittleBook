package com.zhangyp.develop.HappyLittleBook.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.activity.AddAccountBookActivity;
import com.zhangyp.develop.HappyLittleBook.bean.AccountBookInfo;


/**
 * Created by zyp on 2018/3/12.
 * note:首页详情弹窗
 */

public class HomeBookDetailDialog extends Dialog {

    private Context context;

    private TextView tv_title;
    private TextView tv_dialog_cate;
    private TextView tv_dialog_wallet;
    private TextView tv_dialog_time;
    private TextView tv_dialog_note;
    private TextView tv_dialog_money;
    private TextView tv_dialog_book_type;
    private TextView tv_dialog_ok;
    private AccountBookInfo bean;

    public HomeBookDetailDialog(Context context) {
        super(context, R.style.DialogTheme);
        this.context = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.book_detail_dialog_view, null);
        tv_title = view.findViewById(R.id.tv_title);
        tv_dialog_cate = view.findViewById(R.id.tv_dialog_cate);
        tv_dialog_wallet = view.findViewById(R.id.tv_dialog_wallet);
        tv_dialog_time = view.findViewById(R.id.tv_dialog_time);
        tv_dialog_note = view.findViewById(R.id.tv_dialog_note);
        tv_dialog_book_type = view.findViewById(R.id.tv_dialog_book_type);
        tv_dialog_money = view.findViewById(R.id.tv_dialog_money);
        tv_dialog_ok = view.findViewById(R.id.tv_dialog_ok);

        setContentView(view);

        initClick();
    }

    public void setData(AccountBookInfo bean) {
        this.bean = bean;
        int type = bean.getBookType();
        if (type == 0) {
            //支出详情
            tv_title.setText("支出详情");
            tv_dialog_book_type.setText("支出金额");
            tv_dialog_money.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else {
            //收入详情
            tv_title.setText("收入详情");
            tv_dialog_book_type.setText("收入金额");
            tv_dialog_money.setTextColor(ContextCompat.getColor(context, R.color.mainColor));
        }

        tv_dialog_cate.setText(bean.getCateStr());
        tv_dialog_wallet.setText(bean.getWalletType());
        tv_dialog_time.setText(bean.getTimeStr());
        tv_dialog_note.setText(bean.getNoteStr());
        tv_dialog_money.setText(String.valueOf(bean.getMoney()));
    }

    private void initClick() {
        tv_dialog_ok.setOnClickListener(v -> dismiss());
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window == null) {
            return;
        }

        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }
}
