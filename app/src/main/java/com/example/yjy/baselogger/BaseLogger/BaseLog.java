package com.example.yjy.baselogger.BaseLogger;

import java.util.Objects;

/**
 * Created by yjy on 2018/2/3.
 */

public interface BaseLog {

//    void i(String s);
    void i(String s,String obj);
//    void w(String s);
    void w(String s,String obj);
//    void e(String s);
    void d(String s,String obj);
    void e(String s,String obj);
}
