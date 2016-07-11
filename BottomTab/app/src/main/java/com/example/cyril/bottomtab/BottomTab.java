package com.example.cyril.bottomtab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class BottomTab extends RelativeLayout {
    private ImageView mIcon;
    private TextView mIconText;
    private Context mContext;
    private int mTabPosition = -1;
    private Boolean hasText = false;
    private int unSelectColor;
    private int selectColor;


    public BottomTab(Context context, int iconId, String iconText, int unSelectColor, int selectColor) {
        this(context, null, iconId, iconText, unSelectColor, selectColor);
    }

    public BottomTab(Context context, AttributeSet attrs, int iconId, String iconText, int unSelectColor, int selectColor) {
        this(context, attrs, 0, iconId, iconText,unSelectColor,selectColor);
    }

    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr, int iconId, String iconText, int unSelectColor, int selectColor) {
        super(context, attrs, defStyleAttr);

        this.unSelectColor = unSelectColor;


        this.selectColor = selectColor;


        if (iconText != null) {
            initIconText(context, iconText);
            hasText = true;
        }
        initIcon(context, iconId);
    }

    private void initIcon(Context context, int iconId) {
        mContext = context;
        //获取默认的波纹效果
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackgroundDrawable(drawable);
        typedArray.recycle();

        mIcon = new ImageView(context);

        int size = px2dip(24);
        LayoutParams params = new LayoutParams(size, size);
        if (hasText) {
            params.topMargin = px2dip(6);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else {
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
        }

        mIcon.setImageResource(iconId);

        mIcon.setColorFilter(ContextCompat.getColor(context, unSelectColor));
        BottomTab.this.addView(mIcon, params);
    }

    /**
     * 如果设置了文字，则在这里初始化
     *
     * @param context
     * @param iconText
     */
    private void initIconText(Context context, String iconText) {
        mIconText = new TextView(context);
        int size = px2dip(16);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, size);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.bottomMargin = px2dip(10);
        mIconText.setText(iconText);
        BottomTab.this.addView(mIconText, params);
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            //滤镜
            mIcon.setColorFilter(ContextCompat.getColor(mContext, selectColor));
        } else {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, unSelectColor));
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }

    public void setUnSelectColor(int unSelectColor) {
        this.unSelectColor = unSelectColor;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }

    /**
     * px转化成dp
     *
     * @param pxValue
     * @return
     */
    public int px2dip(float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, getResources().getDisplayMetrics());
    }
}
