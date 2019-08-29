package com.zhangyp.develop.HappyLittleBook.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.adapter.IncomeLevelOneCateAdapter;
import com.zhangyp.develop.HappyLittleBook.adapter.IncomeLevelTwoCateAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.IncomeLevelOneCate;
import com.zhangyp.develop.HappyLittleBook.bean.IncomeLevelTwoCate;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;
import com.zhangyp.develop.HappyLittleBook.wight.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class IncomeCateManagerActivity extends BaseActivity {

    private DaoSession daoSession;

    private int showDialogType;
    private final int ADD_CATE_TYPE_ONE = 1;
    private final int ADD_CATE_TYPE_TWO = 2;

    private final int MODIFY_CATE_TYPE_ONE = 3;
    private final int MODIFY_CATE_TYPE_TWO = 4;

    private int cateType;
    private String title;
    private TextView tv_back;
    private RelativeLayout rl_title;
    private TextView tv_two_cate;
    private ImageView iv_add_two_cate;
    private RelativeLayout rl_cate_top;

    private ListView lv_one_cate;
    private IncomeLevelOneCateAdapter oneCateAdapter;
    private List<IncomeLevelOneCate> oneCateList;
    private long oneCateId;
    private IncomeLevelOneCate oneCate;

    private RecyclerView rv_two_cate;
    private IncomeLevelTwoCateAdapter twoCateAdapter;
    private List<IncomeLevelTwoCate> twoCateList;
    private IncomeLevelTwoCate twoCate;

    private CustomDialog dialog;
    private View add_one_cate_view;
    private TextView tv_title;
    private EditText et_cate;
    private TextView tv_delete;
    private TextView tv_cancel;
    private TextView tv_commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initClick();
    }

    private void initView() {
        daoSession = ((ExampleApplication) getApplication()).getDaoSession();

        tv_back = findViewById(R.id.tv_back);
        rl_title = findViewById(R.id.rl_title);
        lv_one_cate = findViewById(R.id.lv_one_cate);
        tv_two_cate = findViewById(R.id.tv_two_cate);
        iv_add_two_cate = findViewById(R.id.iv_add_two_cate);
        rl_cate_top = findViewById(R.id.rl_cate_top);
        rv_two_cate = findViewById(R.id.rv_two_cate);

        add_one_cate_view = LayoutInflater.from(context).inflate(R.layout.add_cate_view, null);
        tv_title = add_one_cate_view.findViewById(R.id.tv_title);
        et_cate = add_one_cate_view.findViewById(R.id.et_cate);
        tv_delete = add_one_cate_view.findViewById(R.id.tv_delete);
        tv_cancel = add_one_cate_view.findViewById(R.id.tv_cancel);
        tv_commit = add_one_cate_view.findViewById(R.id.tv_commit);

        tv_back.setText("收入分类");

        oneCateList = new ArrayList<>();
        oneCateAdapter = new IncomeLevelOneCateAdapter(context, oneCateList);
        /*rv_one_cate.setLayoutManager(new LinearLayoutManager(context));
        rv_one_cate.addItemDecoration(new MyDividerItemDecoration(context, MyDividerItemDecoration.HORIZONTAL_LIST,
                1, ContextCompat.getColor(context, R.color.line)));*/
        lv_one_cate.setAdapter(oneCateAdapter);
        lv_one_cate.addFooterView(LayoutInflater.from(context).inflate(R.layout.one_cate_add_view, null));

        twoCateList = new ArrayList<>();
        twoCateAdapter = new IncomeLevelTwoCateAdapter(context, twoCateList);
        rv_two_cate.setLayoutManager(new GridLayoutManager(context, 3));
        rv_two_cate.setAdapter(twoCateAdapter);


    }

    private void initData() {

        getOneCateList();

        if (oneCateList != null && oneCateList.size() > 0) {
            oneCateId = oneCateList.get(0).getId();
            getTwoCateList(oneCateId);
        }
    }

    private void initClick() {
        lv_one_cate.setOnItemClickListener((parent, view, position, id) -> {
            if (position < oneCateAdapter.getCount()) {
                IncomeLevelOneCate oneCate = oneCateAdapter.getItem(position);
                oneCateId = oneCate.getId();
                oneCateAdapter.changeState(position);
                getTwoCateList(oneCateId);
            } else {
                showDialogType = ADD_CATE_TYPE_ONE;
                tv_title.setText("添加一级分类");
                et_cate.setText("");
                setDialogBtnIsShow();
                showDialog();
            }
        });

        lv_one_cate.setOnItemLongClickListener((parent, view, position, id) -> {
            if (position >= oneCateAdapter.getCount()) {
                return true;
            }
            showDialogType = MODIFY_CATE_TYPE_ONE;
            tv_title.setText("编辑一级分类");
            oneCate = oneCateAdapter.getItem(position);
            et_cate.setText(oneCate.getCateName());
            setDialogBtnIsShow();
            showDialog();
            return true;
        });

        iv_add_two_cate.setOnClickListener(v -> {
            showDialogType = ADD_CATE_TYPE_TWO;
            tv_title.setText("添加二级分类");
            et_cate.setText("");
            setDialogBtnIsShow();
            showDialog();
        });

        twoCateAdapter.setOnItemClickListener((obj, position) -> {
            showDialogType = MODIFY_CATE_TYPE_TWO;
            tv_title.setText("编辑二级分类");
            twoCate = obj;
            et_cate.setText(twoCate.getCateName());
            setDialogBtnIsShow();
            showDialog();
        });

        tv_cancel.setOnClickListener(v -> dialog.dismiss());

        tv_delete.setOnClickListener(v -> {
            if (showDialogType == MODIFY_CATE_TYPE_ONE) {
                deleteOneCate(oneCate);
            } else if (showDialogType == MODIFY_CATE_TYPE_TWO) {
                deleteTwoCate(twoCate);
            }
        });

        tv_commit.setOnClickListener(v -> {
            String cateName = et_cate.getText().toString().trim();
            if (TextUtils.isEmpty(cateName)) {
                ToastUtil.showShortToast(context, "请填写分类名称");
                return;
            }
            if (showDialogType == ADD_CATE_TYPE_ONE) {
                addOneCate(cateName);
            } else {
                addTwoCate(cateName);
            }

            dialog.dismiss();
        });

        tv_back.setOnClickListener(v -> finish());
    }

    private void getOneCateList() {
        this.oneCateList.clear();
        List<IncomeLevelOneCate> oneCateList = daoSession.loadAll(IncomeLevelOneCate.class);
        this.oneCateList.addAll(oneCateList);
        oneCateAdapter.notifyDataSetChanged();
    }

    private void getTwoCateList(long oneCateId) {
        this.twoCateList.clear();
        List<IncomeLevelTwoCate> twoCateList = daoSession.queryRaw(IncomeLevelTwoCate.class, "where ONE_CATE_ID = ?", String.valueOf(oneCateId));
        this.twoCateList.addAll(twoCateList);
        twoCateAdapter.notifyDataSetChanged();

        tv_two_cate.setText(String.format("%s个  二级分类", twoCateList.size()));
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new CustomDialog(context);
        }
        dialog.setContentView(add_one_cate_view);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void addOneCate(String cateName) {
        IncomeLevelOneCate oneCate = new IncomeLevelOneCate();
        oneCate.setCateName(cateName);
        daoSession.insertOrReplace(oneCate);
        et_cate.setText("");
        //重新获取一遍列表之后展示
        getOneCateList();
    }

    private void addTwoCate(String cateName) {
        IncomeLevelTwoCate oneCate = new IncomeLevelTwoCate();
        oneCate.setOneCateId(oneCateId);
        oneCate.setCateName(cateName);
        daoSession.insert(oneCate);
        et_cate.setText("");
        //重新获取一遍列表之后展示
        getTwoCateList(oneCateId);
    }

    private void deleteOneCate(IncomeLevelOneCate oneCate) {
        List<IncomeLevelTwoCate> twoCateList = daoSession.queryRaw(IncomeLevelTwoCate.class, "where ONE_CATE_ID = ?", String.valueOf(oneCate.getId()));
        if (twoCateList != null && twoCateList.size() > 0) {
            ToastUtil.showShortToast(context, "该分类下有二级分类，无法删除");
            return;
        }

        et_cate.setText("");
        daoSession.delete(oneCate);
        //重新获取一遍列表之后展示
        getOneCateList();
        dialog.dismiss();
    }

    private void deleteTwoCate(IncomeLevelTwoCate twoCate) {

        et_cate.setText("");
        daoSession.delete(twoCate);
        //重新获取一遍列表之后展示
        getTwoCateList(oneCateId);
        dialog.dismiss();
    }

    private void setDialogBtnIsShow() {
        if (showDialogType == ADD_CATE_TYPE_ONE || showDialogType == ADD_CATE_TYPE_TWO) {
            tv_delete.setVisibility(View.GONE);
        } else {
            tv_delete.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cate_manager;
    }
}
