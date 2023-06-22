package com.example.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.skydoves.colorpickerview.ColorEnvelope;

import java.util.ArrayList;
import java.util.List;

public class SimplePaint extends View {

    List<Paint> listaPaint;
    List<Path> listaPatch;

    Paint paint;
    Path path;

    public SimplePaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        listaPaint = new ArrayList<Paint>();
        listaPatch = new ArrayList<Path>();

        inicia();

    }

    public void inicia(){
        paint = new Paint();
        path = new Path();

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
    }

    public void onDraw(Canvas canvas){
        super.onDraw(canvas);

        for (int i = 0; i<listaPatch.size(); i++){
            canvas.drawPath(listaPatch.get(i),listaPaint.get(i));
        }

        canvas.drawPath(path,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x,y;

        x = event.getX();
        y = event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x,y);
                path.lineTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x,y);
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(x,y);
                listaPatch.add(path);
                listaPaint.add(paint);
                inicia();
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    public void setColor(int color) {
        paint.setColor(color);
    }
}
