package top.yumesekai.xutil;

import android.widget.Toast;

import top.yumesekai.xutil.XUtil;

public class ToastUtil {

    public static void toastShortMessage(String msg){
        Toast.makeText(XUtil.getContext() ,msg, Toast.LENGTH_SHORT).show();
    }

    public static void toastLongMessage(String msg){
        Toast.makeText(XUtil.getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
