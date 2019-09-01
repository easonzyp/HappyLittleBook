package com.zhangyp.develop.HappyLittleBook.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.ExampleApplication;
import com.zhangyp.develop.HappyLittleBook.R;
import com.zhangyp.develop.HappyLittleBook.adapter.ExpendLevelOneCateAdapter;
import com.zhangyp.develop.HappyLittleBook.adapter.ExpendLevelTwoCateAdapter;
import com.zhangyp.develop.HappyLittleBook.base.BaseActivity;
import com.zhangyp.develop.HappyLittleBook.bean.ExpendLevelOneCate;
import com.zhangyp.develop.HappyLittleBook.bean.ExpendLevelTwoCate;
import com.zhangyp.develop.HappyLittleBook.db.DaoSession;
import com.zhangyp.develop.HappyLittleBook.util.ToastUtil;
import com.zhangyp.develop.HappyLittleBook.wight.CustomDialog;

import java.util.ArrayList;
import java.util.List;

public class ExpendCateManagerActivity extends BaseActivity {

    private DaoSession daoSession;

    private int showDialogType;
    private final int ADD_CATE_TYPE_ONE = 1;
    private final int ADD_CATE_TYPE_TWO = 2;

    private final int MODIFY_CATE_TYPE_ONE = 3;
    private final int MODIFY_CATE_TYPE_TWO = 4;

    private TextView tv_back;
    private TextView tv_two_cate;
    private ImageView iv_add_two_cate;

    private ListView lv_one_cate;
    private ExpendLevelOneCateAdapter oneCateAdapter;
    private List<ExpendLevelOneCate> oneCateList;
    private long oneCateId;
    private ExpendLevelOneCate oneCate;

    private RecyclerView rv_two_cate;
    private ExpendLevelTwoCateAdapter twoCateAdapter;
    private List<ExpendLevelTwoCate> twoCateList;
    private ExpendLevelTwoCate twoCate;

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
        lv_one_cate = findViewById(R.id.lv_one_cate);
        tv_two_cate = findViewById(R.id.tv_two_cate);
        iv_add_two_cate = findViewById(R.id.iv_add_two_cate);
        rv_two_cate = findViewById(R.id.rv_two_cate);

        add_one_cate_view = LayoutInflater.from(context).inflate(R.layout.add_cate_dialog_view, null);
        tv_title = add_one_cate_view.findViewById(R.id.tv_title);
        et_cate = add_one_cate_view.findViewById(R.id.et_cate);
        tv_delete = add_one_cate_view.findViewById(R.id.tv_delete);
        tv_cancel = add_one_cate_view.findViewById(R.id.tv_cancel);
        tv_commit = add_one_cate_view.findViewById(R.id.tv_commit);

        tv_back.setText("支出分类");

        oneCateList = new ArrayList<>();
        oneCateAdapter = new ExpendLevelOneCateAdapter(context, oneCateList);
        /*rv_one_cate.setLayoutManager(new LinearLayoutManager(context));
        rv_one_cate.addItemDecoration(new MyDividerItemDecoration(context, MyDividerItemDecoration.HORIZONTAL_LIST,
                1, ContextCompat.getColor(context, R.color.line)));*/
        lv_one_cate.setAdapter(oneCateAdapter);
        lv_one_cate.addFooterView(LayoutInflater.from(context).inflate(R.layout.one_cate_add_view, null));

        twoCateList = new ArrayList<>();
        twoCateAdapter = new ExpendLevelTwoCateAdapter(context, twoCateList);
        rv_two_cate.setLayoutManager(new GridLayoutManager(context, 3));
        rv_two_cate.setAdapter(twoCateAdapter);

    }

    private void initData() {

        getOneCateList();

        if (oneCateList != null && oneCateList.size() > 0) {
            chooseOneCateItem(0);
        }
    }

    private void initClick() {
        lv_one_cate.setOnItemClickListener((parent, view, position, id) -> {
            if (position < oneCateAdapter.getCount()) {
                chooseOneCateItem(position);
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
            chooseOneCateItem(position);
            et_cate.setText(oneCate.getCateName());
            setDialogBtnIsShow();
            showDialog();
            return true;
        });

        iv_add_two_cate.setOnClickListener(v -> {
            if (oneCateList == null || oneCateList.size() == 0) {
                ToastUtil.showShortToast(context, "请先添加一级分类");
                return;
            }
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
                //添加一级分类
                addOneCate(cateName);
            } else if (showDialogType == ADD_CATE_TYPE_TWO) {
                //添加二级分类
                addTwoCate(cateName);
            } else if (showDialogType == MODIFY_CATE_TYPE_ONE) {
                //编辑一级分类
                modifyOneCate(oneCate, cateName);
            } else if (showDialogType == MODIFY_CATE_TYPE_TWO) {
                //编辑二级分类
                modifyTwoCate(twoCate,cateName);
            }

            dialog.dismiss();
        });

        tv_back.setOnClickListener(v -> finish());
    }

    private void chooseOneCateItem(int position) {
        oneCate = oneCateAdapter.getItem(position);
        oneCateId = oneCate.getId();
        oneCateAdapter.changeState(position);
        getTwoCateList(oneCateId);
    }

    private void getOneCateList() {
        this.oneCateList.clear();
        List<ExpendLevelOneCate> oneCateList = daoSession.loadAll(ExpendLevelOneCate.class);
        this.oneCateList.addAll(oneCateList);
        oneCateAdapter.notifyDataSetChanged();
    }

    private void getTwoCateList(long oneCateId) {
        this.twoCateList.clear();
        List<ExpendLevelTwoCate> twoCateList = daoSession.queryRaw(ExpendLevelTwoCate.class, "where ONE_CATE_ID = ?", String.valueOf(oneCateId));
        this.twoCateList.addAll(twoCateList);
        twoCateAdapter.notifyDataSetChanged();

        tv_two_cate.setText(String.format("%s个  二级分类", twoCateList.size()));
    }

    private void showDialog() {
        if (dialog == null) {
            dialog = new CustomDialog(context, 0, Gravity.CENTER);
        }
        dialog.setContentView(add_one_cate_view);
        dialog.setCancelable(true);
        dialog.show();
    }

    private void addOneCate(String cateName) {
        ExpendLevelOneCate oneCate = new ExpendLevelOneCate();
        oneCate.setCateName(cateName);
        daoSession.insert(oneCate);
        et_cate.setText("");

        //重新获取一遍列表之后展示
        getOneCateList();

        int position = oneCateList.size() - 1;
        oneCateAdapter.changeState(position);
        oneCateId = oneCateList.get(position).getId();
        getTwoCateList(oneCateId);
    }

    private void modifyOneCate(ExpendLevelOneCate oneCate, String cateName) {
        oneCate.setCateName(cateName);
        daoSession.update(oneCate);
        et_cate.setText("");
        //重新获取一遍列表之后展示
        getOneCateList();

        oneCateId = oneCate.getId();
        getTwoCateList(oneCateId);
    }

    private void addTwoCate(String cateName) {
        ExpendLevelTwoCate oneCate = new ExpendLevelTwoCate();
        oneCate.setOneCateId(oneCateId);
        oneCate.setCateName(cateName);
        daoSession.insert(oneCate);
        et_cate.setText("");
        //重新获取一遍列表之后展示
        getTwoCateList(oneCateId);
    }

    private void modifyTwoCate(ExpendLevelTwoCate twoCate,String cateName) {

        twoCate.setOneCateId(oneCateId);
        twoCate.setCateName(cateName);
        daoSession.update(twoCate);
        et_cate.setText("");
        //重新获取一遍列表之后展示
        getTwoCateList(oneCateId);
    }

    private void deleteOneCate(ExpendLevelOneCate oneCate) {
        List<ExpendLevelTwoCate> twoCateList = daoSession.queryRaw(ExpendLevelTwoCate.class, "where ONE_CATE_ID = ?", String.valueOf(oneCate.getId()));
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

    private void deleteTwoCate(ExpendLevelTwoCate twoCate) {
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
