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

    //mendeteksi sentuhan dari user
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
