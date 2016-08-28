package com.example.vikash.myscreenrecorder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MyCanvas extends View {

    Path mPath;
    Canvas canvas;
    Paint paint;
    float pointX;
    float pointY;
    int height;

    MyCanvas(Context context, AttributeSet attrs){
        super(context,attrs);
        //Bitmap bg = Bitmap.createBitmap(400, 800, Bitmap.Config.ARGB_8888);
        canvas=new Canvas();
        canvas.drawColor(Color.CYAN);
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        mPath=new Path();
        canvas.drawColor(Color.CYAN);
        canvas.drawPath(mPath,paint);
        //canvas.drawBitmap(bg,0,0,paint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        pointX = event.getX();
        pointY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(pointX,pointY);
                break;
            case MotionEvent.ACTION_MOVE:
                 mPath.lineTo(pointX,pointY);
                //canvas.drawPath(mPath,paint);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate();
        return true;
    }
}
