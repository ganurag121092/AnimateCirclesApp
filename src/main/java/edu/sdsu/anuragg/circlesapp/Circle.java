package edu.sdsu.anuragg.circlesapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnuragG on 18-Feb-17.
 */

public class Circle {
    public float centerX;
    public float centerY;
    public float circleRadius;
    public float circleVelX;
    public float circleVelY;
    public Paint circleColor;
    public Circle(float x, float y, float radius, String color){
        centerX = x;
        centerY = y;
        circleRadius = radius;
        circleColor = new Paint();
        circleColor.setStyle(Paint.Style.STROKE);
        circleColor.setStrokeWidth(2f);
        circleColor.setColor(Color.parseColor(color));
    }

    public void drawOn(Canvas canvas){
        canvas.drawCircle(centerX,centerY,circleRadius,circleColor);
    }
}
