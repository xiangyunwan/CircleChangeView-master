package com.example.asus.custom_view_demo1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by codekk on 2016/5/21.
 * Email:  645326280@qq.com
 */
public class CircleChangeView extends View {
    private Paint paint1;
    private Paint paint2;
    private int x;
    private int y;
    private int radius1;
    private int radius2;
    private boolean toBig1 = true;
    private boolean toBig2 = true;
    private int timeDelta;
    private int colorRes1;
    private int colorRes2;
    private int[] colors;
    private Thread thread;
    private boolean requestStop = false;
    private OnCircleChangeListener listener;
    private boolean isOnStart = false;


    public interface OnCircleChangeListener {
        void onStart();
        void onStop();
    }


    public CircleChangeView(Context context) {
        super(context);
        TypedArray array = context.obtainStyledAttributes(R.styleable.CircleChangeView);
        timeDelta = array.getInteger(R.styleable.CircleChangeView_timeDelta, 40);
        paint1 = new Paint();
        paint2 = new Paint();
        inits();
    }

    public CircleChangeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleChangeView);
        timeDelta = array.getInteger(R.styleable.CircleChangeView_timeDelta, 40);
        paint1 = new Paint();
        paint2 = new Paint();
        inits();
    }


    private void startMyAni() {
        if (listener!=null && !isOnStart && !requestStop) {
            listener.onStart();
            isOnStart=true;
        }

        if(listener!=null && isOnStart&&requestStop){
            listener.onStop();
            isOnStart=false;
        }

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random(System.currentTimeMillis());
                int randomIndex = random.nextInt(colors.length - 1);
                if (toBig1) {
                    toBig2 = false;
                    radius1++;
                } else {
                    toBig2 = true;
                    radius1--;
                }
                if (toBig2) {
                    toBig1 = false;
                    radius2++;
                } else {
                    toBig1 = true;
                    radius2--;
                }

                if (radius1 > Math.min(x, y)) {
                    toBig1 = false;
                } else if (radius1 <= -2) {
                    toBig1 = true;
                }

                if (radius2 > Math.min(x, y)) {
                    toBig2 = false;
                } else if (radius2 <= -2) {
                    toBig2 = true;
                }

                if (radius2 == radius1 && toBig2 && !toBig1) {
                    toBig1 = true;
                    toBig2 = false;
                    if (colors != null) {
                        colorRes1 = colors[randomIndex];
                        colorRes2 = colors[randomIndex + 1];
                    }
                }
                if (!requestStop) {
                    postInvalidate();
                } else if(requestStop){
                    if (radius2 != 0 && radius1 != 0) {
                        postInvalidate();
                    } else if (radius2 == -1 || radius1 == -1) {
                        requestStop = true;

                    }

                }
                try {
                    Thread.sleep(timeDelta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    requestStop = true;
                }
            }
        });
        thread.start();
    }

    private void inits() {
        radius2 = 0;
        colorRes1 = R.color.colorAccent;
        colorRes2 = R.color.colorPrimary;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        x = width / 2;
        y = height / 2;
        radius1 = Math.min(x, y);
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int result = 0;

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 100;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int result = 0;

        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = 100;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint1 = new Paint();
        paint1.setStyle(Paint.Style.FILL);
        paint1.setAntiAlias(true);
        paint1.setColor(getResources().getColor(colorRes1));
        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(getResources().getColor(colorRes2));
        canvas.drawCircle(x, y, radius1, paint1);
        canvas.save();
        canvas.drawCircle(x, y, radius2, paint2);
        startMyAni();
    }

    public void setColors(int[] temp) {
        colors = temp;
    }


    public void start() {
        requestStop = false;
        invalidate();
    }

    public void stop() {
        requestStop = true;
    }

    public void setOnCircleChangeListener(OnCircleChangeListener listener) {
        this.listener = listener;
    }
}
