package top.yumesekai.xutil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import top.yumesekai.xutil.databinding.ActivityMainBinding;
import top.yumesekai.xutil.media.AudioPlayer;

public class MainActivity extends AppCompatActivity {

    private static final int GET_RECODE_AUDIO = 1;
    private static String[] PERMISSION_AUDIO = {
            Manifest.permission.RECORD_AUDIO
    };

    /*
     * 申请录音权限*/
    public static void verifyAudioPermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.RECORD_AUDIO);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSION_AUDIO,
                    GET_RECODE_AUDIO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case GET_RECODE_AUDIO:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）

                } else {
                    // 没有获取到权限，做特殊处理
                    ToastUtil.toastLongMessage("获取位置权限失败，请手动开启");
                }
                break;
            default:
                break;
        }
    }

    //视图绑定
    ActivityMainBinding binding;
    String TAG=this.getClass().getSimpleName();

    float startX=0;
    float startY=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.waveview.startAnim();
        binding.tvHello.setText(PathUtil.getDiskCacheDir());

        binding.btnRecord.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    startX=event.getX();
                    startY=event.getY();
                    Log.d(TAG,"MotionEvent.ACTION_DOWN"+event.getX()+","+event.getY());
                    AudioPlayer.getInstance().startRecord(success -> {
                        //AudioPlayer.getInstance().getPath();

                        if(success){
                            ToastUtil.toastLongMessage(AudioPlayer.getInstance().getPath());
                        }else {
                            ToastUtil.toastLongMessage("录制失败");
                        }
                    });
                    updateMicStatus();
                    break;
                case MotionEvent.ACTION_UP:
                    //Log.d(TAG,"MotionEvent.ACTION_UP"+event.getX()+","+event.getY());
                    if(Math.abs( event.getX()-startX)>v.getWidth()/2 ||Math.abs( event.getY()-startY)>v.getHeight()){
                        binding.waveview.setVisibility(View.VISIBLE);
                    }else {
                       binding.waveview.setVisibility(View.GONE);
                    }
                    AudioPlayer.getInstance().stopRecord();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.d(TAG,"MotionEvent.ACTION_MOVE"+event.getX()+","+event.getY());
                    if(Math.abs( event.getX()-startX)>v.getWidth()/2 ||Math.abs( event.getY()-startY)>v.getHeight()){
                        binding.tvHello.setText("取消发送");
                    }else {
                        binding.tvHello.setText("上滑取消");
                    }

                    break;
            }

            return false;
        });
        verifyAudioPermissions(this);
    }

    Handler mHandler=new Handler();
    private Runnable mUpdateMicStatusTimer = new Runnable() {
        public void run() {
            updateMicStatus();
        }
    };

    /**
     * 更新话筒状态
     *
     */
    private int BASE = 1;
    private int SPACE = 100;// 间隔取样时间

    private void updateMicStatus() {
        int Amp=AudioPlayer.getInstance().getMaxAmplitude();
        if (Amp>-1) {
            double ratio = (double)Amp /BASE;
            double db = 0;// 分贝
            if (ratio > 1)
                db = 20 * Math.log10(ratio);
            //Log.d(TAG,"分贝值："+db);
            binding.db.setText("声量"+AudioPlayer.getInstance().getMaxAmplitude());
            //binding.waveview.setVolume(AudioPlayer.getInstance().getMaxAmplitude());
            mHandler.postDelayed(mUpdateMicStatusTimer, SPACE);
        }
    }
}