package com.example.first.service;

import android.os.Binder;
import android.util.Log;

/**
 * Created by sxiaoxia on 2018/5/3.
 */

public class MyBinder extends Binder {

    public void service_connect_Activity() {
        Log.i("BindService", "Service关联了Activity, 并在Activity执行了Service的方法");
    }
}
