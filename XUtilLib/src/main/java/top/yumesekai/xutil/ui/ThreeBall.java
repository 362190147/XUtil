package top.yumesekai.xutil.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class ThreeBall extends View {
    private Paint paint=new Paint();
    private Position ball1=new Position(0,0);
    private Position ball2=new Position(0,0);
    private Position ball3=new Position(0,0);

    public ThreeBall(Context context) {
        super(context);
    }

    public ThreeBall(Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    public ThreeBall(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public ThreeBall(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        canvas.drawCircle(ball1.x,ball1.y,radius,paint);
        canvas.drawCircle(ball2.x,ball2.y,radius,paint);
        canvas.drawCircle(ball3.x,ball3.y,radius,paint);
    }

    int radius;

    List<ValueAnimator> list=new ArrayList<>();


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        init(w,h);
        startAnimation();
    }

    void init(int w, int h){
        //int w=150;
        //int h=150;
        radius=w/7;
        Log.d("test",""+w);
        paint.setColor(0xffffffff);
        /**
         * p7 p8 p9
         * p4 p5 p6
         * p1 p2 p3
         */
        Position p1=new Position(radius, h-radius);
        Position p2=new Position(w/2, h-radius);
        Position p3=new Position(w-radius,h-radius);
        Position p4=new Position(radius,h/2);
        Position p5=new Position(w/2,h/2);
        Position p6=new Position(w-radius,h/2);
        Position p7=new Position(radius,radius);
        Position p8=new Position(w/2,radius);
        Position p9=new Position(w-radius,radius);

        //vax=ValueAnimator.ofInt(p7.x,p8.x,p9.x,p6.x,p3.x,p2.x,p1.x,p4.x,p7.x);
        //vay=ValueAnimator.ofInt(p7.y,p8.y,p9.y,p6.y,p3.y,p2.y,p1.y,p4.y,p7.y);

        ofInt(ball1,p7,p8,p9,p6,p3,p2,p1,p4,p7);
        ofInt(ball2,p9,p1,p2,p5,p8,p7,p3,p6,p9);
        ofInt(ball3,p2,p3,p7,p4,p1,p9,p8,p5,p2);

    }

     void ofInt(final Position position, Position... value){
        int x[]=new int[value.length];
        int y[]=new int[value.length];
        for(int i=0;i< value.length;++i){
            x[i]=value[i].x;
            y[i]=value[i].y;
        }
        list.add(ofIntX(position,x));
        list.add(ofIntY(position,y));
    }

    ValueAnimator ofIntX(final Position position, int... value){
        ValueAnimator va=ValueAnimator.ofInt(value);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setRepeatMode(ValueAnimator.RESTART);
        va.setDuration(5000);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                position.x=(int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        return va;
    }

    ValueAnimator ofIntY(final Position position, int... value){
        ValueAnimator va=ValueAnimator.ofInt(value);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setRepeatMode(ValueAnimator.RESTART);
        va.setDuration(5000);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                position.y=(int) animation.getAnimatedValue();
                //Log.d("test",""+position.y);
                postInvalidate();
            }
        });
        return va;
    }

    public void startAnimation(){
        for(ValueAnimator va:list){
            va.start();
        }
    }

    class Position{
        public int x;
        public int y;
        Position(int x,int y){
            this.x=x;
            this.y=y;
        }
    }
}
