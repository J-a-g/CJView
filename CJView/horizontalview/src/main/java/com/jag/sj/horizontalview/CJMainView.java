package com.jag.sj.horizontalview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by SJ on 2017.4.23.
 */

public class CJMainView extends LinearLayout {
    public CJMainView(Context context) {
        super(context);
    }

    public CJMainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CJMainView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
