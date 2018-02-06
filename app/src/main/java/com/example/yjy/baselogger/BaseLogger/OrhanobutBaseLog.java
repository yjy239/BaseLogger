package com.example.yjy.baselogger.BaseLogger;

import com.orhanobut.logger.Logger;

/**
 * Created by yjy on 2018/2/3.
 */

public class OrhanobutBaseLog implements BaseLog {

    @Override
    public void i(String s,String obj) {
        Logger.t(s).i(obj);

    }

    @Override
    public void w(String s,String obj) {
        Logger.t(s).w(obj);
    }

    @Override
    public void d(String s, String obj) {
        Logger.t(s).w(obj);
    }


    @Override
    public void e(String s,String obj) {
        Logger.t(s).e(obj);
    }
}
