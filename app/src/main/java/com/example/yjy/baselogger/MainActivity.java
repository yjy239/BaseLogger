package com.example.yjy.baselogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yjy.baselogger.BaseLogger.BaseLog;
import com.example.yjy.baselogger.BaseLogger.BaseLogger;
import com.example.yjy.baselogger.BaseLogger.MyBaseLog;
import com.example.yjy.baselogger.BaseLogger.OrhanobutBaseLog;
import com.orhanobut.logger.Logger;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseLogger.getInstance().init(this,new MyBaseLog());
        BaseLogger.openDebug();
        BaseLogger.i("aa","1111");
        BaseLogger.e("aaa","aaaa");

    }
}
