package top.yumesekai.xutil.ui;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class BallView extends View {
    private Paint paint;
    private int color1=0xfff9bd37;
    private int color2=0xff8dc14b;
    Float value=0.1f;

    public BallView(Context context) {
        this(context,null);
    }

    public BallView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BallView(Context context,  AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public BallView(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint=new Paint();
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(color1);
        int radius=getHeight()/4;
        canvas.drawCircle(radius+(getWidth()-radius-radius)*value,getHeight()/2f, radius, paint);
        paint.setColor(color2);
        canvas.drawCircle(radius+(getWidth()-radius-radius)*(1-value),getHeight()/2f, radius, paint);
    }



    public void startAnimation(){
        AnimatorSet animatorSet=new AnimatorSet();
        ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        anim.setRepeatCount(ValueAnimator.INFINITE);//设置无限重复
        anim.setRepeatMode(ValueAnimator.REVERSE);//设置重复模式
        anim.setDuration(1000);
        anim.addUpdateListener(animation -> {
            value = (Float) animation.getAnimatedValue();
            postInvalidate();

        });
        anim.start();

        //颜色动画
        ValueAnimator animBall1 = ValueAnimator.ofArgb(0xfff9bd37, 0xff4183ee);
        animBall1.setRepeatCount(ValueAnimator.INFINITE);//设置无限重复
        animBall1.setRepeatMode(ValueAnimator.REVERSE);//设置重复模式
        animBall1.setDuration(1000);
        animBall1.addUpdateListener(animation -> {
            color1 = (Integer) animation.getAnimatedValue();
            postInvalidate();
        });
        //animBall1.start();


        //颜色动画
        ValueAnimator animBall2 = ValueAnimator.ofArgb(0xff8dc14b, 0xfff35959);
        animBall2.setRepeatCount(ValueAnimator.INFINITE);//设置无限重复
        animBall2.setRepeatMode(ValueAnimator.REVERSE);//设置重复模式
        animBall2.setDuration(1000);
        animBall2.addUpdateListener(animation -> {
            color2 = (Integer) animation.getAnimatedValue();
            postInvalidate();
        });
        //animBall2.start();
        animatorSet.play(animBall1).with(animBall2);
        animatorSet.start();
    }

}
