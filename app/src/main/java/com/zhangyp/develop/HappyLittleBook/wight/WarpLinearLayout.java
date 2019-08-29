package com.zhangyp.develop.HappyLittleBook.wight;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangyp.develop.HappyLittleBook.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyp on 2019/8/27 0027.
 * class note:
 */

public class WarpLinearLayout extends ViewGroup {

    public WarpLinearLayout(Context context) {
        super(context);
    }

    public WarpLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WarpLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置数据
     *
     * @param data     文字
     * @param context  上下文
     * @param textSize 文字大小
     * @param pl       左内边距
     * @param pt       上内边距
     * @param pr       右内边距
     * @param pb       下内边距
     * @param ml       左外边距
     * @param mt       上外边距
     * @param mr       右外边距
     * @param mb       下外边距
     */
    public void setData(String[] data, Context context, int textSize, int pl, int pt, int pr, int pb, int ml, int mt, int mr, int mb) {
        createChild(data, context, textSize, pl, pt, pr, pb, ml, mt, mr, mb);
    }

    public void setData(List<String> data, Context context, int textSize, int pl, int pt, int pr, int pb, int ml, int mt, int mr, int mb) {
        String[] tempData = null;
        if (data != null) {
            int length = data.size();
            tempData = new String[length];
            for (int i = 0; i < length; i++) {
                tempData[i] = data.get(i);
            }
        }
        setData(tempData, context, textSize, pl, pt, pr, pb, ml, mt, mr, mb);
    }

    private void createChild(final String[] data, final Context context, int textSize, int pl, int pt, int pr, int pb, int ml, int mt, int mr, int mb) {
        final int size = data.length;
        for (int i = 0; i < size; i++) {
            String text = data[i];
            //通过判断style是TextView还是Button进行不同的操作，还可以继续添加不同的view

            TextView textView = new TextView(context);
            textView.setGravity(Gravity.CENTER);
            textView.setText(text);
            textView.setTextColor(ContextCompat.getColor(context, R.color.secondtextcolor));
            textView.setTextSize(textSize);

            textView.setClickable(true);

            textView.setPadding(dip2px(context, pl), dip2px(context, pt), dip2px(context, pr), dip2px(context, pb));
            MarginLayoutParams params = new MarginLayoutParams(MarginLayoutParams.WRAP_CONTENT, MarginLayoutParams.WRAP_CONTENT);
            params.setMargins(dip2px(context, ml), dip2px(context, mt), dip2px(context, mr), dip2px(context, mb));

            textView.setLayoutParams(params);
            textView.setTag(i);

            textView.setBackgroundResource(R.drawable.unchoose_cate_corner_bg);

            //给每个view添加点击事件
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    for (int j = 0; j < data.length; j++) {
                        if (j == tag) {
                            v.setBackgroundResource(R.drawable.choose_cate_corner_bg);
                            ((TextView) v).setTextColor(ContextCompat.getColor(context, R.color.white));
                        } else {
                            WarpLinearLayout.this.getChildAt(j).setBackgroundResource(R.drawable.unchoose_cate_corner_bg);
                            ((TextView) WarpLinearLayout.this.getChildAt(j)).setTextColor(ContextCompat.getColor(context, R.color.secondtextcolor));
                        }
                    }
                    markClickListener.clickMark(data[tag], tag);
                }
            });
            this.addView(textView);
        }
    }

    private MarkClickListener markClickListener;

    public void setMarkClickListener(MarkClickListener markClickListener) {
        this.markClickListener = markClickListener;
    }

    public interface MarkClickListener {
        void clickMark(String text, int position);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        int lineWidth = 0;
        int lineHeight = 0;
        int width = 0;//warpcontet是需要记录的宽度
        int height = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            // 测量每一个child的宽和高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
//            Log.e(TAG, "onMeasure: lineHeight = "+lineHeight+" childHeight = "+childHeight );
            if (lineWidth + childWidth > widthSize) {
                width = Math.max(lineWidth, childWidth);//这种情况就是排除单个标签很长的情况
                lineWidth = childWidth;//开启新行
                height += lineHeight;//记录总行高
                lineHeight = childHeight;//因为开了新行，所以这行的高度要记录一下
            } else {
                lineWidth += childWidth;
//                lineHeight = Math.max(lineHeight, childHeight); //记录行高
                lineHeight = Math.max(height, childHeight); //记录行高
            }
            // 如果是最后一个，则将当前记录的最大宽度和当前lineWidth做比较
            if (i == childCount - 1) {
                width = Math.max(width, lineWidth);  //宽度
                height += lineHeight;  //
            }
        }

        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize
                : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize
                : height);
     /*   int width1 = (widthMode == MeasureSpec.EXACTLY)? widthSize:width;
        int height1 = (heightMode == MeasureSpec.EXACTLY)? heightSize:height;
        Log.e(TAG, "onMeasure: widthSize ="+widthSize+" heightSize = "+heightSize );
        Log.e(TAG, "onMeasure: width ="+width+" height = "+height );
        Log.e(TAG, "onMeasure: widthEnd ="+width1+" heightEnd = "+height1 );*/
    }

    //存储所有的View，按行记录
    private List<List<View>> mAllViews = new ArrayList<>();

    //记录每一行的最大高度
    private List<Integer> mLineHeight = new ArrayList<>();

    // 存储每一行所有的childView
    private List<View> lineViews = new ArrayList<>();

    //onLayout中完成对所有childView的位置以及大小的指定
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();  //清空子控件列表
        mLineHeight.clear();  //清空高度记录列表
        lineViews.clear();
        int width = getWidth();//得到当前控件的宽度（在onmeasure方法中已经测量出来了）
        int childCount = getChildCount();

        int lineWidth = 0;  //行宽
        int lineHeight = 0; //总行高
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();//得到属性参数
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();
            // 如果已经需要换行
            if (i == 3) {
                i = 3;
            }
            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > width)  //大于父布局的宽度
            {
                // 记录这一行所有的View以及最大高度
                mLineHeight.add(lineHeight);
                // 将当前行的childView保存，然后开启新的ArrayList保存下一行的childView
                mAllViews.add(lineViews);
                lineWidth = 0;// 重置行宽
                lineViews = new ArrayList<>();
            }
            /*
             * 如果不需要换行，则累加
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }
        // 记录最后一行  (因为最后一行肯定大于父布局的宽度，所以添加最后一行是必要的)
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);
        int left = 0;
        int top = 0;
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            // 每一行的所有的views
            lineViews = mAllViews.get(i);
            // 当前行的最大高度  每一行的高度都相同  所以使用（i+1）进行设置高度
            lineHeight = (i + 1) * mLineHeight.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View lineChild = lineViews.get(j);
                if (lineChild.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) lineChild.getLayoutParams();
                //开始画标签了。左边和上边的距离是要根据累计的数确定的。
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + lineChild.getMeasuredWidth();
                int bc = tc + lineChild.getMeasuredHeight();
                lineChild.layout(lc, tc, rc, bc);
                left += lineChild.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            }
            left = 0;//将left归零
            top = lineHeight;
        }
    }
}
