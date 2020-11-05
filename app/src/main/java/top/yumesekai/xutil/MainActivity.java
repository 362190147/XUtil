package top.yumesekai.xutil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import top.yumesekai.xutil.media.AudioPlayer;
import top.yumesekai.xutil.ui.WaveView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_hello=findViewById(R.id.tv_hello);
        WaveView waveView=findViewById(R.id.waveview);
        waveView.startAnim();
        tv_hello.setText(Path.getDiskCacheDir());

        tv_hello.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        waveView.setVolume(99.0f);
                        //AudioPlayer.getInstance().startRecord(success -> { });
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }

                return true;
            }
        });
    }
}