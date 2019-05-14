package com.example.mateusz.kosmicznaprzygoda;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class ObstacleManager {
private ArrayList<Obstacle> obstacles;
private int obstacleWidth; //Asteroida
private int obstacleGap;
private int obstacleHeigth;
private int color;


private long startTime;
private long initTime;

private int score=0;

    public ObstacleManager(int obstacleWidth,int obstacleGap,int obstacleHeigth,int color) {
        this.obstacleWidth = obstacleWidth;
        this.obstacleGap=obstacleGap;
        this.obstacleHeigth=obstacleHeigth;
        this.color=color;
        Constants.SCORCES=score;

        obstacles = new ArrayList<>();

        startTime=initTime=System.currentTimeMillis();

        populateObstacles();
    }

    private void  populateObstacles(){
        int currY = -5*Constants.SCREEN_HEIGTH/4;
        while(currY<0){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH-obstacleWidth));
            obstacles.add(new Obstacle(obstacleHeigth,color,xStart,currY,obstacleWidth) );
            currY+= obstacleHeigth+obstacleGap;


        }
    }

    public boolean playerColide(ShipPlayer player){
        for(Obstacle ob: obstacles){
            if (ob.playerColide(player)){
                return true;
            }
        }
        return false;
    }

    public void update(){
        if(startTime<Constants.INIT_TIME)
            startTime=Constants.INIT_TIME;
        int elapsedTime=(int)(System.currentTimeMillis()-startTime);
        startTime=System.currentTimeMillis();

        float speed=(float) (Math.sqrt(1 + (startTime-initTime)/2000.0))*Constants.SCREEN_HEIGTH/10000.0f;
        for(Obstacle ob:obstacles){
            ob.incrementY(speed*elapsedTime);
        }
        if(obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGTH){
            int xStart = (int)(Math.random()*(Constants.SCREEN_WIDTH-obstacleWidth));
            obstacles.add(0,new Obstacle(obstacleHeigth,color,xStart,obstacles.get(0).getRectangle().top-obstacleHeigth-obstacleGap,obstacleWidth));
            obstacles.remove(obstacles.size()-1);
            score++;
            Constants.SCORCES=score;
        }
    }

    public void draw(Canvas canvas){
        for(Obstacle ob:obstacles)
            ob.draw(canvas);
        Paint paint= new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText(""+score,50,50 + paint.descent()-paint.ascent(),paint);
    }
}
