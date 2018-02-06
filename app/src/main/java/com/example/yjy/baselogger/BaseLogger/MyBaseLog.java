package com.example.yjy.baselogger.BaseLogger;

import android.util.Log;

/**
 * Created by yjy on 2018/2/3.
 */

public class MyBaseLog implements BaseLog {

    @Override
    public void i(String s,String obj) {
        Log.i(s,obj);
    }



    @Override
    public void w(String s,String obj) {
        Log.w(s,obj);
    }

    @Override
    public void d(String s, String obj) {
        Log.d(s,obj);
    }


    @Override
    public void e(String s,String obj) {
        Log.e(s,obj);
    }
}
