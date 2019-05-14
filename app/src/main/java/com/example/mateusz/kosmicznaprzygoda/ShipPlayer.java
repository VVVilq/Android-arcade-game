package com.example.mateusz.kosmicznaprzygoda;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class ShipPlayer implements GameObject{
    private Rect rectangle;
    private  int color;
    private Bitmap ship;

    public Rect getRectangle(){
        return rectangle;
    }

    public Bitmap getBitmap(){

        return ship;
    }

    public ShipPlayer(Rect rectangle,int color){
        this.rectangle=rectangle;
        this.color=color;
        ship=BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.ship3);


    }
    @Override
    public  void draw(Canvas canvas){
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle,paint);

/*

        Drawable ship=Constants.CURRENT_CONTEXT.getResources().getDrawable(R.drawable.ship3);
        ship.setBounds(rectangle);
        ship.draw(canvas);
*/


        canvas.drawBitmap(ship,null,rectangle,null);

    }

    @Override
    public void update() {

    }

    public void update(Point point){
        rectangle.set(point.x-rectangle.width()/2,point.y-rectangle.height()/2,point.x+rectangle.width()/2,point.y+rectangle.height()/2);
    }

}
