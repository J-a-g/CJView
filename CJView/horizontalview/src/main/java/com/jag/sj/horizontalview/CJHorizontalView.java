package com.jag.sj.horizontalview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by SJ on 2017.4.22.
 */

public class CJHorizontalView extends ViewGroup {

    private final int TAG_CLOSED = 0;
    private final int TAG_OPENING = 1;
    private final int TAG_CLOSING = 3;
    private final int TAG_OPENED = 4;
    private final int TAG_FINISH = 5;

    private int menuViewWidth = 0;

    private int tag = 0;
    private Scroller scroller;
   // private VelocityTracker velocityTracker;

    private int nlastX = 0;
    private int nlastY = 0;

    private int nlastXintercept = 0;
    private int nlastYintercept = 0;

    private boolean flag = false;

    public CJHorizontalView(Context context) {
        super(context);
        init();
    }

    public CJHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CJHorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (scroller == null) {
            scroller = new Scroller(getContext());
            //velocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int r, int t, int l, int bot) {

        /*final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            Log.v("tag", "-----> " + getChildAt(i).getMeasuredWidth());
        }*/
        MenuView();
        MainView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.v("tag", "onInterceptTouchEvent....");
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
               /* if(!scroller.isFinished()){
                    scroller.abortAnimation();
                    intercepted = true;
                }*/
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - nlastXintercept;
                int dy = y - nlastYintercept;
                if (Math.abs(dx) > Math.abs(dy) && x == 0) { //左右滑动的趋势比上下滑动的趋势明显
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
            default:
                break;
        }
        nlastYintercept = y;
        nlastXintercept = x;
        Log.v("tag", "intercepted --->" + intercepted);
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
      //  velocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*if(!scroller.isFinished()){
                    scroller.abortAnimation();
                }*/
                break;
            case MotionEvent.ACTION_MOVE:
                if (getScrollX() == 0) {
                    tag = TAG_CLOSED;
                } else if (getScrollX() == -menuViewWidth) {
                    tag = TAG_OPENED;
                }
                int dx = x - nlastX;
                dx = dx / 2;
                if (getScrollX() == 0 && dx > 0) {
                    tag = TAG_OPENING;
                }
                if (-getScrollX() == menuViewWidth && dx < 0) {
                    tag = TAG_CLOSING;
                }
                if (tag == TAG_OPENING) {
                    dx = dx > menuViewWidth + getScrollX() ? menuViewWidth + getScrollX() : dx;
                    if (dx > 0 && dx > menuViewWidth /10) {
                        scrollBy(-dx, 0);
                    }
                } else if (tag == TAG_CLOSING) {
                   // dx = dx < -getScrollX() ? -getScrollX() : dx;
                    if (dx < 0 && -dx > menuViewWidth / 10) {
                        scrollBy(-dx, 0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                /*velocityTracker.computeCurrentVelocity(1000);
                float xVel = velocityTracker.getXVelocity();*/
                if (tag == TAG_OPENING) {
                    int ndx = menuViewWidth + getScrollX();
                    smoothScroll(-ndx);
                } else if (tag == TAG_CLOSING) {
                    smoothScroll(-getScrollX());
                }
                break;
            default:
                break;
        }
        nlastY = y;
        nlastX = x;
        return true;
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    private void MenuView() {
        final View childView = getChildAt(0);
        menuViewWidth = childView.getMeasuredWidth();
        if (childView.getVisibility() == View.VISIBLE) {
            childView.layout(-childView.getMeasuredWidth(), 0, 0, childView.getMeasuredHeight());
        }
    }

    private void MainView() {
        final View childView = getChildAt(1);
        if (childView.getVisibility() == View.VISIBLE) {
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
        }
    }

    private void smoothScroll(int dx) {
        scroller.startScroll(getScrollX(), 0, dx, 0, 1000);
        invalidate();
    }

    public void scrollby() {
        scrollBy(100, 0);
    }
}
