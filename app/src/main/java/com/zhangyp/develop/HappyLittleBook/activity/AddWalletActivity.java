package com.zhangyp.develop.HappyLittleBook.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.WalletInfo;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.util.BasisTimesUtils;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;
import com.zhangyp.develop.HappyLittleBook.wight.TwoButtonCenterDialog;

public class AddWalletActivity extends BaseActivity {
    private DaoSession daoSession;

    private TextView tv_back;
    private EditText et_name;
    private EditText et_money;
    private EditText et_note;
    private TextView tv_save;
    private ImageView iv_delete;

    private WalletInfo wallet;
    private int type;
    private TwoButtonCenterDialog buttonCenterDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initIntentData();
        initView();
        initData();
        initClick();
    }

    private void initIntentData() {
        wallet = (WalletInfo) getIntent().getSerializableExtra("wallet");
    }

    private void initView() {
        daoSession = ((ExampleApplication) getApplication()).getDaoSession();
        tv_back = findViewById(R.id.tv_back);
        et_name = findViewById(R.id.et_name);
        et_money = findViewById(R.id.et_money);
        et_note = findViewById(R.id.et_note);
        tv_save = findViewById(R.id.tv_save);
        iv_delete = findViewById(R.id.iv_delete);

        if (wallet == null) {
            type = 0;
            tv_back.setText("添加钱包");
            iv_delete.setVisibility(View.GONE);
        } else {
            type = 1;
            tv_back.setText("编辑钱包");
            iv_delete.setVisibility(View.VISIBLE);
            et_name.setText(wallet.getWalletName());
            et_money.setText(String.valueOf(wallet.getMoney()));
            et_note.setText(wallet.getWalletNote());
        }
    }

    private void initData() {

    }

    private void initClick() {
        tv_back.setOnClickListener(v -> finish());

        tv_save.setOnClickListener(v -> submit());

        iv_delete.setOnClickListener(v -> {
            if (buttonCenterDialog == null) {
                buttonCenterDialog = new TwoButtonCenterDialog(context, "确定要删除该钱包吗?", true);
            }
            buttonCenterDialog.show();
            buttonCenterDialog.setOnClickRateDialog(new TwoButtonCenterDialog.OnClickRateDialog() {
                @Override
                public void onClickLeft() {
                    buttonCenterDialog.dismiss();
                }

                @Override
                public void onClickRight() {
                    deleteWallet();
                }
            });
        });
    }

    private void deleteWallet() {
        daoSession.delete(wallet);
        ToastUtil.showShortToast(context, "钱包删除成功");
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_add_wallet;
    }

    private void submit() {

        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showShortToast(context, "请填写钱包名称");
            return;
        }

        String money = et_money.getText().toString().trim();
        if (TextUtils.isEmpty(money)) {
            ToastUtil.showShortToast(context, "请填写钱包金额");
            return;
        }

        String note = et_note.getText().toString().trim();
        if (TextUtils.isEmpty(note)) {
            note = "空";
        }
        if (type == 0) {
            addWallet(name, money, note);
        } else {
            modifyWallet(name, money, note);
        }
    }

    private void addWallet(String name, String money, String note) {
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setWalletName(name);
        walletInfo.setMoney(Double.valueOf(money));
        walletInfo.setAddTime(BasisTimesUtils.getDeviceTime());
        walletInfo.setWalletNote(note);

        daoSession.insert(walletInfo);
        finish();
        ToastUtil.showShortToast(context, "钱包添加成功");
    }

    private void modifyWallet(String name, String money, String note) {
        wallet.setWalletName(name);
        wallet.setMoney(Double.valueOf(money));
        wallet.setAddTime(BasisTimesUtils.getDeviceTime());
        wallet.setWalletNote(note);

        daoSession.update(wallet);
        finish();
        ToastUtil.showShortToast(context, "钱包修改成功");
    }
}
