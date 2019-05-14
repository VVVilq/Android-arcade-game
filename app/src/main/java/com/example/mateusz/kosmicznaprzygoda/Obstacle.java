package com.example.mateusz.kosmicznaprzygoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Obstacle implements GameObject{
    private Rect rectangle;
    private  int color;
    private int innerCircleRadius;
    //private Point innerCircleCenter;
    private int centerOfInnerCircleX;
    private int centerOfInnerCircleY;

    private Bitmap asteroid;

    public  Rect getRectangle(){
        return  rectangle;
    }

    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom +=y;

    }

    public Obstacle(int rectHeight,int color,int startX,int startY, int recWidth){
        this.rectangle=new Rect(startX,startY,startX+recWidth,startY+rectHeight);
        this.color=color;
        asteroid=BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.asteroid);
        innerCircleRadius=(rectangle.height()/2)-10;
    }
/*
    public boolean playerColide(ShipPlayer player){
        return Rect.intersects(rectangle,player.getRectangle());
    }
*/

    public boolean playerColide(ShipPlayer player){

        if( Rect.intersects(rectangle,player.getRectangle())){
            int v1x=player.getRectangle().left+20;
            int v1y=player.getRectangle().bottom-20;
            int v2x=player.getRectangle().right-20;
            int v2y=player.getRectangle().bottom-20;
            int v3x=player.getRectangle().left+ player.getRectangle().width()/2;
            int v3y=player.getRectangle().top+20;

            //Test 1
            int c1x= v1x - rectangle.centerX();
            int c1y= v1y - rectangle.centerY();

            if (Math.sqrt(c1x*c1x + c1y*c1y) <= innerCircleRadius)
                return true;

            int c2x = v2x - rectangle.centerX();
            int c2y = v2y - rectangle.centerY();

            if (Math.sqrt(c2x*c2x + c2y*c2y) <= innerCircleRadius)
                return true;

            int c3x = v3x - rectangle.centerX();
            int c3y = v3y - rectangle.centerY();

            if (Math.sqrt(c3x*c3x + c3y*c3y) <= innerCircleRadius)
                return true;

            //Test 3
            int c1x2 = rectangle.centerX() - v1x;
            int c1y2 = rectangle.centerY() - v1y;
            int e1x = v2x - v1x;
            int e1y = v2y - v1y;
            double len;

            double k = c1x2*e1x + c1y2*e1y;

            if (k > 0)
            {
                 len = Math.sqrt(e1x*e1x + e1y*e1y);
                 k = k/len;

                if(k < len)
                {
                    if (Math.sqrt(c1x2*c1x2 + c1y2*c1y2 - k*k) <= innerCircleRadius)
                        return true;
                }
            }

            int c2x2 = rectangle.centerX() - v2x;
            int c2y2 = rectangle.centerY() - v2y;
            int e2x = v3x - v2x;
            int e2y = v3y - v2y;

             k = c2x2*e2x + c2y2*e2y;

            if (k > 0)
            {
                 len = Math.sqrt(e2x*e2x + e2y*e2y);
                k = k/len;

                if(k < len)
                {
                    if (Math.sqrt(c2x2*c2x2 + c2y2*c2y2 - k*k) <= innerCircleRadius)
                        return true;
                }
            }


            int c3x2 = rectangle.centerX() - v3x;
            int c3y2 = rectangle.centerY() - v3y;
            int e3x = v1x - v3x;
            int e3y = v1y - v3y;

            k = c3x2*e3x + c3y2*e3y;

            if (k > 0)
            {
                len = Math.sqrt(e3x*e3x + e3y*e3y);
                k = k/len;

                if(k < len)
                {
                    if (Math.sqrt(c3x2*c3x2 + c3y2*c3y2 - k*k) <= innerCircleRadius)
                        return true;
                }
            }

        }
        return false;
    }



    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = (int) Math.max(rect1.left, rect2.left);
        int top = (int) Math.max(rect1.top, rect2.top);
        int right = (int) Math.min(rect1.right, rect2.right);
        int bottom = (int) Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }
    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }

    @Override
    public void draw(Canvas canvas) {
        /*
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle,paint);
        */


        canvas.drawBitmap(asteroid,null,rectangle,null);


    }

    @Override
    public void update() {

    }
}
