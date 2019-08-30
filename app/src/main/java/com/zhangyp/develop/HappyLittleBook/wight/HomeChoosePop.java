package com.zhangyp.develop.HappyLittleBook.wight;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.util.PublicViewUtil;

/**
 * Created by zyp on 2019/3/7 0007.
 * class note:
 */

public class HomeChoosePop extends PopupWindow {

    private Context context;

    private TextView tv_cancel;
    private TextView tv_ok;
    private TextView tv_volume_describe;
    private GridView gv_volume;
    private TextView tv_weight_describe;
    private GridView gv_weight;
    private OnChooseListener onChooseListener;


    public HomeChoosePop(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        setAnimationStyle(R.style.pop_win_anim_style);
        setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        setFocusable(true);
        setOutsideTouchable(true);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View view = View.inflate(context, R.layout.pop_home_choose, null);
        setContentView(view);
        initView(view);
    }

    private void initView(View view) {
        initClick();
    }

    private void initClick() {

    }

    public void show(View view) {
        PublicViewUtil.backgroundAlpha((Activity) context, 0.6f);
        setOnDismissListener(() -> PublicViewUtil.backgroundAlpha((Activity) context, 1f));
        showAtLocation(view, Gravity.BOTTOM, 0, 0);
        update();
    }

    public interface OnChooseListener {
        void onChoose(int type);
    }

    public void setOnChooseListener(OnChooseListener listener) {
        this.onChooseListener = listener;
    }
}