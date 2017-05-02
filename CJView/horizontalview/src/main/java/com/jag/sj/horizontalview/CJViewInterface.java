package com.jag.sj.horizontalview;

/**
 * Created by Jag on 2017.4.23.
 */

public interface CJViewInterface {

    void setCallBack2View(CJHorizontalView.CallBack2View callBack2View);

    int getViewStatus();

    void closeMenuView();

    void openMenuView();
}
