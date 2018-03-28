package com.vivian.apputil;

import android.app.Application;

/**
 * Created by vivianWQ on 2018/3/28
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class AppApplication extends Application {

    private static AppApplication appApplication;

    public static AppApplication getInstance() {
        return appApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;

    }
}
