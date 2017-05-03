package com.jag.sj.cjview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jag.sj.horizontalview.CJHorizontalView;
import com.jag.sj.horizontalview.CJViewInterface;

public class MainActivity extends AppCompatActivity implements CJHorizontalView.CallBack2View{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CJViewInterface cjViewInterface = (CJViewInterface) findViewById(R.id.cjhorizontalview);
        cjViewInterface.setCallBack2View(this);
        ///cjViewInterface.setIsScroll(false);
        Button button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("tag","---> btn1");
            }
        });
        Button button1 = (Button) findViewById(R.id.btn2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("tag","--->btn2");
            }
        });
        Button btn_open = (Button) findViewById(R.id.btn_open);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cjViewInterface.openMenuView();
            }
        });

        Button btn_true = (Button)findViewById(R.id.btn_true);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cjViewInterface.setIsScroll(true);
            }
        });
        Button btn_false = (Button)findViewById(R.id.btn_false);
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cjViewInterface.setIsScroll(false);
            }
        });
    }

    @Override
    public void opening() {
        Log.v("tag","opening.......");
    }

    @Override
    public void opened() {
        Log.v("tag","opened.......");
    }

    @Override
    public void closing() {
        Log.v("tag","closing.......");
    }

    @Override
    public void closed() {
        Log.v("tag","closed.......");
    }
}
