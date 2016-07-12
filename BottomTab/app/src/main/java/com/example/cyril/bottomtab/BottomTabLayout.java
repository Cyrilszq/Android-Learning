package com.example.cyril.bottomtab;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class BottomTabLayout extends LinearLayout {

    private LinearLayout mTabLayout;
    private LayoutParams mTabParams;
    //暴露接口
    private OnTabSelectedListener mListener;
    //记录当前选中的位置
    private int mCurrentPosition = 0;

    private int unSelectColor = R.color.colorPrimary;
    private int selectColor = R.color.colorPrimaryDark;

    public BottomTabLayout(Context context) {
        this(context, null);
    }

    public BottomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    /**
     * 初始化布局容器
     *
     * @param context
     */
    private void init(Context context) {
        mTabLayout = new LinearLayout(context);
        mTabLayout.setOrientation(HORIZONTAL);
        mTabLayout.setBackgroundColor(Color.WHITE);
        addView(mTabLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mTabParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        mTabParams.weight = 1;
    }

    /**
     * 添加icon到容器中
     * @param context
     * @param id
     * @param iconText
     * @return
     */
    public BottomTabLayout addItem(Context context, int id, String iconText) {

        final BottomTab tab = new BottomTab(context, id, iconText, unSelectColor, selectColor);

        tab.setTabPosition(mTabLayout.getChildCount());
        tab.setLayoutParams(mTabParams);

        mTabLayout.addView(tab);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null)
                    return;

                int pos = tab.getTabPosition();
                if (mCurrentPosition == pos) {
                    mListener.onTabReselected(pos);
                } else {
                    mListener.onTabSelected(pos, mCurrentPosition);
                    tab.setSelected(true);

                    mTabLayout.getChildAt(mCurrentPosition).setSelected(false);
                    mCurrentPosition = pos;
                }
            }
        });
        return this;
    }

    public void setUnSelectColor(int unSelectColor) {
        this.unSelectColor = unSelectColor;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }


    /**
     * 选中监听
     */
    public interface OnTabSelectedListener {
        //选中时回调
        void onTabSelected(int position, int prePosition);

        //重复选中时回调
        void onTabReselected(int position);
    }

    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mListener = onTabSelectedListener;
    }
}
