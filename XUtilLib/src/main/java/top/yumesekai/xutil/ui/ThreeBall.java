package top.yumesekai.xutil.ui;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

public class ThreeBall extends View {
    private final Paint paint=new Paint();
    //private Position ball1=new Position(0,0);
    //private Position ball2=new Position(0,0);
    //private Position ball3=new Position(0,0);
    private final List<Position> ballList =new ArrayList<>();


    public ThreeBall(Context context) {
        this(context,null);
    }

    public ThreeBall(Context context,  AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThreeBall(Context context,  AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);

    }

    public ThreeBall(Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        //canvas.drawCircle(ball1.x,ball1.y,radius,paint);
        //canvas.drawCircle(ball2.x,ball2.y,radius,paint);
        //canvas.drawCircle(ball3.x,ball3.y,radius,paint);
        for(Position b: ballList){
            canvas.drawCircle(b.x,b.y,radius,paint);
        }
    }

    int radius;

    List<ValueAnimator> list=new ArrayList<>();


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        init(w,h);
        startAnimation();
    }

    void init(int w, int h){
        radius=w/7;
        //Log.d("test",""+w);
        paint.setColor(0xffffffff);
        /* 位置关系
         * p7 p8 p9
         * p4 p5 p6
         * p1 p2 p3
         */
        Position p1= new Position(radius, h - radius);
        Position p2= new Position(w / 2, h - radius);
        Position p3= new Position(w - radius, h - radius);
        Position p4= new Position(radius, h / 2);
        Position p5= new Position(w / 2, h / 2);
        Position p6= new Position(w - radius, h / 2);
        Position p7= new Position(radius, radius);
        Position p8= new Position(w / 2, radius);
        Position p9= new Position(w - radius, radius);
        //ballList.add(new Position(0,0));
        ///ballList.add(new Position(0,0));
        ///ballList.add(new Position(0,0));
        //list.add(ofInt1(1,p7,p8,p9,p6,p3,p2,p1,p4,p7));
        //list.add(ofInt1(2,p9,p1,p2,p5,p8,p7,p3,p6,p9));
        //list.add(ofInt1(0,p2,p3,p7,p4,p1,p9,p8,p5,p2));

        list.add(ofInt(new Position(0, 0),p7,p8,p9,p6,p3,p2,p1,p4,p7));
        list.add(ofInt(new Position(0, 0),p9,p1,p2,p5,p8,p7,p3,p6,p9));
        list.add(ofInt(new Position(0, 0),p2,p3,p7,p4,p1,p9,p8,p5,p2));
    }


    PositionEvaluator positionEvaluator= new PositionEvaluator();


    ValueAnimator ofInt(final Position ball0, Position... value){
        ballList.add(ball0);
        ValueAnimator va = ObjectAnimator.ofObject(positionEvaluator, (Object[]) value);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setRepeatMode(ValueAnimator.RESTART);
        va.setDuration(5000);
        va.addUpdateListener(animation -> {
            Position b=(Position) animation.getAnimatedValue();
            ball0.x=b.x;
            ball0.y=b.y;
            postInvalidate();
        });
        return  va;
    }

    public void startAnimation(){
        for(ValueAnimator va:list){
            va.start();
        }
    }

    private static class Position{
        public int x;
        public int y;
        public Position(int x,int y){
            this.x=x;
            this.y=y;
        }
    }


    static class PositionEvaluator implements TypeEvaluator<Position> {
        //IntEvaluator intEvaluator = new IntEvaluator();
        public Integer intEvaluate(float fraction, Integer startValue, Integer endValue) {
            int startInt = startValue;
            return (int)(startInt + fraction * (endValue - startInt));
        }

        @Override
        public Position evaluate(float fraction, Position startValue, Position endValue) {
            return new Position(
                    intEvaluate(fraction, startValue.x, endValue.x),
                    intEvaluate(fraction, startValue.y, endValue.y));
        }

    }
}
