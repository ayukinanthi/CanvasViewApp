package com.ayukinanthi.canvasviewapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;



import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

public class MyCanvasView extends View {
    //paint
    private Paint mPaint;
    //pergerakan jari
    private Path mPath;
    //warna
    private int mDrawColor;
    private int mBackgroundColor;

    private Canvas mCanvas;
    private Bitmap mBitmap;
    //frame
    private Rect mFrame;

    private int currentColor;
    private int strokeWidth;

    MyCanvasView(Context context){
        this(context, null);
    }

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBackgroundColor= ResourcesCompat.getColor(getResources(),
                R.color.purple_pastel, null);

        mDrawColor=ResourcesCompat.getColor(getResources(), R.color.green_pastel, null);

        mPath=new Path();
        mPaint=new Paint();
        mPaint.setColor(mDrawColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        //style paint
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //mengubah ketebalan path
        mPaint.setStrokeWidth(25);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(mBitmap);
        mCanvas.drawColor(mBackgroundColor);
        //set ukuran frame
        //inset = padding
        int inset=40;
        mFrame=new Rect(inset,inset, w-inset, h-inset);
    }

    //setiap perubahan yang direfresh maka di onDraw ditambah method untuk gambar di canvas
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawRect(mFrame, mPaint);
    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE=4;
    private void touchStart(float x, float y){
        mPath.moveTo(x,y);
        mX=x;
        mY=y;
    }

    private void touchMove(float x, float y){
        float dx=Math.abs(x-mX);
        float dy=Math.abs(y-mY);
        if(dx>=TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
            mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
            mX=x;
            mY=y;
            mCanvas.drawPath(mPath,mPaint);
        }
    }

    private void touchUp(){
        mPath.reset();
    }

    //mendeteksi sentuhan dari user
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
        float y=event.getY();
        switch(event.getAction()){
            //user baru meletakkan jari diatas layar
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                break;
            //user menggerakan jari
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;
                //mereset path menjadi baru
            case MotionEvent.ACTION_UP:
                touchUp();
                break;
            default:
        }
        return true;
    }
    public void setColor (int color) {
        currentColor = color;

    }

    public void setStrokeWidth (int width) {
        strokeWidth = width;

    }
}
