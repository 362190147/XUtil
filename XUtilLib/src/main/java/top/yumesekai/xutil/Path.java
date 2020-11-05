package top.yumesekai.xutil;

import android.os.Environment;

public class Path {

    /**
     * 获得应用的cache文件夹
     * @return
     */
    public static String getDiskCacheDir() {
        String cachePath;
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cachePath = XUtil.getContext().getExternalCacheDir().getPath();
        } else {
            cachePath = XUtil.getContext().getCacheDir().getPath();
        }
        return cachePath;
    }
}
