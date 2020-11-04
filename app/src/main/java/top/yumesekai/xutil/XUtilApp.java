package top.yumesekai.xutil;

import android.app.Application;

public class XUtilApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XUtil.init(getApplicationContext());
    }
}
