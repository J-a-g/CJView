package com.example.sj.cjhorizontalview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by SJ on 2017.4.22.
 */

public class CJHorizontalView extends BaseView implements ViewInterface{

    private final int TAG_CLOSED = 0;
    private final int TAG_OPENING = 1;
    private final int TAG_CLOSING = 3;
    private final int TAG_OPENED = 4;
    private final int TAG_FINISH = 5;
    boolean intercepted = false;

    private int menuViewWidth = 0;
    private int mainViewWidth = 0;

    private int tag = 0;
    private Scroller scroller;
   // private VelocityTracker velocityTracker;

    private int nlastX = 0;
    private int nlastY = 0;

    private int nlastXintercept = 0;
    private int nlastYintercept = 0;


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

        Log.v("tag","onLayout....");
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
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
               // intercepted = false;
                if (getScrollX() == 0) {
                    tag = TAG_CLOSED;
                    nlastX = menuViewWidth + mainViewWidth;
                    intercepted = false;
                } else if (getScrollX() == -menuViewWidth) {
                    tag = TAG_OPENED;
                    intercepted = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - nlastXintercept;
                int dy = y - nlastYintercept;
                if (Math.abs(dx) > Math.abs(dy)) { //左右滑动的趋势比上下滑动的趋势明显
                    intercepted = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //intercepted = false;
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
                /*if(getScrollX() == 0){
                    nlastX = menuViewWidth;
                }*/
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - nlastX;
                dx = dx / 2;
                if (getScrollX() == 0 && dx > 0) {
                    tag = TAG_OPENING;
                    intercepted = true;
                   // dx = 0;
                }
                if (-getScrollX() == menuViewWidth && dx < 0) {
                    tag = TAG_CLOSING;
                    intercepted = true;
                }
                if (tag == TAG_OPENING) {
                    int tmp = menuViewWidth + getScrollX();
                    Log.v("tag",dx + "-open--- " + tmp);
                    if (dx > 0 ) {
                        Log.v("tag",dx + "-open2--- " + tmp);
                        dx = dx > tmp ? tmp : dx;
                        scrollBy(-dx, 0);
                    }
                } else if (tag == TAG_CLOSING) {
                    Log.v("tag",dx + "---- " + (-getScrollX()));
                    dx = dx > getScrollX() ? dx : getScrollX();
                    if (dx < 0 ) {
                        scrollBy(-dx, 0);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (tag == TAG_OPENING) {
                    int ndx = menuViewWidth + getScrollX();
                    smoothScroll(-ndx);
                } else if (tag == TAG_CLOSING) {
                    smoothScroll(-getScrollX());
                    nlastX = menuViewWidth;
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
        Log.v("tag","menuViewWidth----> " + menuViewWidth);
        if (childView.getVisibility() == View.VISIBLE) {
            childView.layout(-childView.getMeasuredWidth(), 0, 0, childView.getMeasuredHeight());
        }
    }

    private void MainView() {
        final View childView = getChildAt(1);
        mainViewWidth = childView.getMeasuredWidth();
        ((CJMainView)childView).setViewInterface(this);
        if (childView.getVisibility() == View.VISIBLE) {
            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
        }
    }

    private void smoothScroll(int dx) {
        scroller.startScroll(getScrollX(), 0, dx, 0, animationTime);
        invalidate();
    }

    @Override
    public void setAnimationTime(int time) {
        animationTime = time;
    }

    @Override
    public int getViewStatus() {
        return tag;
    }

    @Override
    public void closeMenuView() {
        /*if (tag == TAG_OPENING) {
            int ndx = menuViewWidth + getScrollX();
        Log.v("tag","closeMenuView......" + ndx);
            smoothScroll(-ndx);
        } else if (tag == TAG_CLOSING) {*/
        if(tag == TAG_OPENED) {
            smoothScroll(-getScrollX());
        }
       // }
    }
}
