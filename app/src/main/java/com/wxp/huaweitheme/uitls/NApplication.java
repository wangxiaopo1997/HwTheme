package com.wxp.huaweitheme.uitls;

import android.app.Application;
import android.content.Context;

/**
 * <pre>
 *     author : wxp
 *     time   : 2020/10/29
 *     desc   : Application
 * </pre>
 */
public class NApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtil.INSTANCE.setContext(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
