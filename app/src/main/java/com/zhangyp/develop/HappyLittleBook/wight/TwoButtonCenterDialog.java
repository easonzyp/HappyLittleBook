package com.zhangyp.develop.HappyLittleBook.wight;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;


/**
 * Created by zyp on 2018/3/12.
 * note:双按钮dialog
 */

public class TwoButtonCenterDialog extends Dialog {

    private String tv_tips;
    private OnClickRateDialog onClickRateListener;

    public TwoButtonCenterDialog(Context context, String tv_tips, boolean b) {
        super(context);

        this.tv_tips = tv_tips;
        this.setCancelable(b);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_two_button_center, null);
        TextView textView = mView.findViewById(R.id.tv_tips);
        textView.setText(tv_tips);
        Button positiveButton = mView.findViewById(R.id.button2);
        Button negativeButton = mView.findViewById(R.id.button1);
        if (positiveButton != null) positiveButton.setOnClickListener(v -> {
            if (onClickRateListener != null)
                onClickRateListener.onClickRight();
            dismiss();
        });
        if (negativeButton != null) negativeButton.setOnClickListener(v -> {
            if (onClickRateListener != null)
                onClickRateListener.onClickLeft();
            dismiss();
        });

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.setContentView(mView);
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
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
    }

    public interface OnClickRateDialog {
        void onClickLeft();
        void onClickRight();
    }

    public void setOnClickRateDialog(OnClickRateDialog onClickRateListener) {
        this.onClickRateListener = onClickRateListener;
    }
}
