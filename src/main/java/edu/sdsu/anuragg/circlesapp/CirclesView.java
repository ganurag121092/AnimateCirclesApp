package edu.sdsu.anuragg.circlesapp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by AnuragG on 18-Feb-17.
 */

public class CirclesView extends View implements View.OnTouchListener{
    private float touchDownX = 0, touchDownY =0, centerX = 0, centerY = 0, radius = 0, arcX = 0, arcY = 0, touchUpX = 0, touchUpY = 0,boundedRadius = 0;
    private static boolean isDrawMode = true, isDeleteMode, isMoveMode;
    private float rightEdgeX, bottomEdgeY;
    VelocityTracker velocity = VelocityTracker.obtain();
    private boolean isFingerMoving = false;
    private Circle movingCircle;
    private ArrayList<Float> radii;
    public ArrayList<Circle> circlesToMove = new ArrayList<>();

    private float velX, velY;
    private String selectedCircleColor = "Black";
    private ArrayList<Circle> circles = new ArrayList<>();


    public CirclesView(Context context){
        super(context);
    }
    public CirclesView(Context context, AttributeSet xmlAttributes){
        super(context,xmlAttributes);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        rightEdgeX = this.getWidth();
        bottomEdgeY = this.getHeight();
        isDrawMode = CirclesActivity.isDrawMode;
        isDeleteMode=CirclesActivity.isDeleteMode;
        isMoveMode=CirclesActivity.isMoveMode;
        selectedCircleColor = CirclesActivity.selectedCircleColor;
        logTouchType(event);
        return true;
    }

    public boolean isInRange(float cX, float cY,float radius){
        Log.i("View measureWidthEvents",String.valueOf(rightEdgeX));
        Log.i("View measureHtEvents",String.valueOf(bottomEdgeY));
        if(radius>=cX||radius>=cY||radius>=rightEdgeX-cX||radius>=bottomEdgeY-cY) {
            return false;
        }
        else {
            return true;
        }
    }
    private void logTouchType(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                radii = new ArrayList<Float>();
                boundedRadius = 0;
                touchDownX = event.getX();
                touchDownY = event.getY();
                if(isDrawMode) {
                    centerX = touchDownX;
                    centerY = touchDownY;
                }
                if(isDeleteMode){
                    Iterator<Circle> circleIterator = circles.iterator();
                    while (circleIterator.hasNext()) {
                        Circle circle = circleIterator.next();
                        float dist = (float) Math.sqrt(Math.pow(circle.centerX - touchDownX, 2) + Math.pow(circle.centerY - touchDownY, 2));
                        if(dist <= circle.circleRadius) {
                            circleIterator.remove();
                           invalidate();
                        }
                        invalidate();
                    }
                    if(circlesToMove!=null) {
                        Iterator<Circle> movingCircleIterator = circlesToMove.iterator();
                        while (circleIterator.hasNext()) {
                            Circle circle = movingCircleIterator.next();
                            float dist = (float) Math.sqrt(Math.pow(circle.centerX - touchDownX, 2) + Math.pow(circle.centerY - touchDownY, 2));

                            if (dist <= circle.circleRadius) {


                                movingCircleIterator.remove();
                            }
                        }

                    }

                }
                if(isMoveMode){
                    velocity = VelocityTracker.obtain();
                    velocity.addMovement(event);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isFingerMoving = true;

                if(isDrawMode) {
                    arcX=event.getX();
                    arcY = event.getY();
                    radius = (float) Math.sqrt(Math.pow(centerX - arcX, 2) + Math.pow(centerY - arcY, 2));
                    radii.add(radius);
                        if(isInRange(centerX,centerY,radius)){
                            movingCircle = new Circle(centerX,centerY,radius,selectedCircleColor);
                            invalidate();

                        } else {
                            if(boundedRadius == 0) {
                                boundedRadius = radii.get(radii.size()-2);
                            }
                            break;
                        }

                }
                if(isMoveMode){
                    velocity.addMovement(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                touchUpX = event.getX();
                touchUpY = event.getY();
                if(isDrawMode) {
                    arcX=event.getX();
                    arcY = event.getY();
                    radius = (float) Math.sqrt(Math.pow(centerX - arcX, 2) + Math.pow(centerY - arcY, 2));
                    if(boundedRadius!=0){
                        circles.add(new Circle(centerX, centerY, boundedRadius, selectedCircleColor));
                    }
                    else {
                            circles.add(new Circle(centerX, centerY, radius, selectedCircleColor));
                            invalidate();
                    }
                }
                if(isDeleteMode){
                    Iterator<Circle> circleIterator = circles.iterator();
                    while (circleIterator.hasNext()) {
                        Circle circle = circleIterator.next();
                        float dist = (float) Math.sqrt(Math.pow(circle.centerX - touchDownX, 2) + Math.pow(circle.centerY - touchDownY, 2));;
                        if(dist <= circle.circleRadius) {
                            circleIterator.remove();
                        }
                    }

                    if(circlesToMove!=null) {
                        Iterator<Circle> movingCircleIterator = circlesToMove.iterator();
                        while (circleIterator.hasNext()) {
                            Circle circle = movingCircleIterator.next();
                            float dist = (float) Math.sqrt(Math.pow(circle.centerX - touchDownX, 2) + Math.pow(circle.centerY - touchDownY, 2));
                            if (dist <= circle.circleRadius) {
                                movingCircleIterator.remove();
                            }
                        }

                    }

                    invalidate();
                }
                if(isMoveMode) {
                    velocity.computeCurrentVelocity(5);
                    Log.i("rew", "X vel " + velocity.getXVelocity() + " Y vel " + velocity.getYVelocity());
                    velX = velocity.getXVelocity();
                    velY = velocity.getYVelocity();
                    for (Circle each : circles) {
                        float dist = (float) Math.sqrt(Math.pow(each.centerX - touchDownX, 2) + Math.pow(each.centerY - touchDownY, 2));
                        if (dist <= each.circleRadius) {
                            each.circleVelX = velX;
                            each.circleVelY = velY;
                            if(!circlesToMove.contains(each)) {
                                circlesToMove.add(each);
                            }
                        }
                    }
                    invalidate();
                    velocity.recycle();
                    velocity = null;

                }
                invalidate();
                break;
            default:
                Log.i("rew","other action " + event.getAction());
        }
    }

    private void detectCollision(Circle circle)
    {
        Log.i("collision", "Circle collision" );
        if (xIsOutOfBounds(circle)) circle.circleVelX = circle.circleVelX * - 1;
        if (yIsOutOfBounds(circle)) circle.circleVelY = circle.circleVelY * - 1;
        }

    private boolean xIsOutOfBounds(Circle circle)
    {   float cX = circle.centerX;
        if (cX - circle.circleRadius<0)
            return true;
        if (cX + circle.circleRadius > rightEdgeX)
            return true;
        return false;
    }

    private boolean yIsOutOfBounds(Circle circle)
    {
        float cY = circle.centerY;
        if (cY - circle.circleRadius < 0)
            return true;
        if (cY + circle.circleRadius > bottomEdgeY)
            return true;
        return false;
    }


    protected void onDraw(Canvas canvas) {
        if(isFingerMoving && isDrawMode){
            movingCircle.drawOn(canvas);
        }

        if(isMoveMode) {
           for (Circle each : circlesToMove) {
                detectCollision(each);
                each.centerX +=each.circleVelX;
                each.centerY +=each.circleVelY;
                each.drawOn(canvas);
                }
                invalidate();
            }

        if(circlesToMove!=null) {
            if(!isDeleteMode) {
                for (Circle each : circlesToMove) {
                    each.drawOn(canvas);
                }
            }

            else if(isDeleteMode)
            {
                Iterator<Circle> deleteCircleIterator = circlesToMove.iterator();
                while (deleteCircleIterator.hasNext()) {
                    Circle circle = deleteCircleIterator.next();
                    Iterator<Circle> deleteIterator = circles.iterator();
                    while (deleteIterator.hasNext()) {
                        Circle movingCircle = deleteIterator.next();
                        float dist = (float) Math.sqrt(Math.pow(circle.centerX - touchDownX, 2) + Math.pow(circle.centerY - touchDownY, 2));

                        if (dist <= circle.circleRadius) {
                            if(movingCircle.circleRadius == circle.circleRadius){
                                deleteIterator.remove();
                            }
                        }
                    }
                }
                circlesToMove.clear();
            }

        }

        for (Circle each : circles) {
            each.drawOn(canvas);
        }
    }
}
