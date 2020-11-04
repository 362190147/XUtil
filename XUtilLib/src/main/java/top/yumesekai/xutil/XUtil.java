package top.yumesekai.xutil;

import android.content.Context;

public class XUtil {

    private static Context context;

    public static void init(Context context){
        XUtil.context=context;
    }

    public static Context getContext(){
        return context;
    }
}
