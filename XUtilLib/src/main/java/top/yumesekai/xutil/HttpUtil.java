package top.yumesekai.xutil;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpUtil {
    static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient() {
        if(okHttpClient == null) okHttpClient = new OkHttpClient();
        return okHttpClient;
    }

    public static String download(String url) throws IOException {
        //获得文件名

        String fileName=Math.abs(url.hashCode())+parseSuffix(url);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {

            response.body().bytes();

            return response.body().string();
        }
    }



    final static Pattern pattern = Pattern.compile("\\S*[?]\\S*");

    /**
     * 获取链接的后缀名
     * @return
     */
    private static String parseSuffix(String url) {

        Matcher matcher = pattern.matcher(url);

        String[] spUrl = url.toString().split("/");
        int len = spUrl.length;
        String endUrl = spUrl[len - 1];

        if(matcher.find()) {
            String[] spEndUrl = endUrl.split("\\?");
            return spEndUrl[0].split("\\.")[1];
        }
        return endUrl.split("\\.")[1];
    }
}
