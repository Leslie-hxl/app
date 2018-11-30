package com.cn.android_testtwo;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 开启页面：
 */
public class actionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        //开启项目后，在主线程中两秒钟后进入主页面：
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(actionActivity.this,UpLineActivity.class));
                finish();
            }
        },2000);
    }
}
