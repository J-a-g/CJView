package com.jag.sj.horizontalview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Jag on 2017.4.23.
 */

public class CJMainView extends LinearLayout{

    private final int TAG_CLOSED = 0;
    private final int TAG_OPENING = 1;
    private final int TAG_CLOSING = 3;
    private final int TAG_OPENED = 4;
    private final int TAG_FINISH = 5;

    private CJViewInterface viewInterface;

    public void setViewInterface(CJViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

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
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v("tag","CJMainView onInterceptTouchEvent...");
        boolean intercepted = false;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(viewInterface.getViewStatus() == TAG_OPENED){
                    intercepted = true;
                }else{
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        Log.v("tag", "CJMainView  intercepted --->" + intercepted);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("tag","CJMainView onTouchEvent...");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if(viewInterface.getViewStatus() == TAG_OPENED ||viewInterface.getViewStatus() == TAG_OPENING || viewInterface.getViewStatus() == TAG_CLOSING ){
                    viewInterface.closeMenuView();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
