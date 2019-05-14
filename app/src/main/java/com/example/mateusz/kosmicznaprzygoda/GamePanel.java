package com.example.mateusz.kosmicznaprzygoda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private  Rect r =new Rect();
    private Rect backgrountSrc;
    private Rect backgrountDest;


    private MainThread thread;
    private ShipPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlater =false;
    private boolean gameOver=false;
    private Bitmap background;
    private Paint backgroundPaint;
    private  long gameOvetTime;

    private OrientationData orientationData;
    private long frameTime;


    public  GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        Constants.CURRENT_CONTEXT=context;

        thread= new MainThread(getHolder(),this);

        player = new ShipPlayer(new Rect(100,100,300,300),Color.rgb(255,0,0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGTH/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200,350,200,Color.BLACK);

        background=BitmapFactory.decodeResource(getResources(),R.drawable.spacebackground);
        backgroundPaint=new Paint();
        backgroundPaint.setColor(Color.RED);
        backgrountSrc=new Rect(0,0,background.getWidth()-1,background.getHeight()-1);
        backgrountDest=new Rect(0,0,Constants.SCREEN_WIDTH-1,Constants.SCREEN_HEIGTH-1);
        orientationData=new OrientationData();
        orientationData.register();
        frameTime=System.currentTimeMillis();
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){

    }

    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGTH/4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(200,350,200,Color.BLACK);
        movingPlater=false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        thread=new MainThread(getHolder(),this);
        Constants.INIT_TIME=System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void  surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;

        while (retry){
            try {
                thread.setRunning(false);
                thread.join();
            }catch (Exception e){e.printStackTrace();}
            retry=false;
        }
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver&&player.getRectangle().contains((int)event.getX(),(int)event.getY()))
                    movingPlater=true;
                if (gameOver && System.currentTimeMillis()-gameOvetTime>=2000){
                    /*
                    reset();
                    gameOver=false;
                    orientationData.newGame();
                    */

                    openSaveResultsActivity();

                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlater)
                    playerPoint.set((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:


                    movingPlater=false;

                break;

        }

        return true;
        //return super.onTouchEvent(event);
    }
    public void openSaveResultsActivity(){
        Intent intent = new Intent(Constants.CONTEXT_ACTIVITY,SaveResultActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Constants.CONTEXT_ACTIVITY.startActivity(intent);
        ((Activity)Constants.CONTEXT_ACTIVITY).finish();


    }

    public void update(){
        if(!gameOver){
            if(frameTime<Constants.INIT_TIME)
                frameTime=Constants.INIT_TIME;
            int elapsedTime=(int)(System.currentTimeMillis()-frameTime);
            frameTime=System.currentTimeMillis();
            if(orientationData.getOrientation() != null && orientationData.getStartOrientation() != null){
                float pitch = orientationData.getOrientation()[1]-orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2]-orientationData.getStartOrientation()[2];

                float xSpeed = 2*roll*Constants.SCREEN_WIDTH/1000f;
                float ySpeed = 2*pitch*Constants.SCREEN_HEIGTH/1000f;

                playerPoint.x+=Math.abs(xSpeed*elapsedTime)>5 ? xSpeed*elapsedTime:0;
                playerPoint.y -=Math.abs(ySpeed*elapsedTime)>5 ? ySpeed*elapsedTime:0;
            }

            if (playerPoint.x<0)
                playerPoint.x=0;
            else if (playerPoint.x > Constants.SCREEN_WIDTH)
                playerPoint.x=Constants.SCREEN_WIDTH;
            if (playerPoint.y<0)
                playerPoint.y=0;
            else if (playerPoint.y > Constants.SCREEN_HEIGTH)
                playerPoint.y=Constants.SCREEN_HEIGTH;

            player.update(playerPoint);
            obstacleManager.update();
            if(obstacleManager.playerColide(player)){
                gameOver=true;
                gameOvetTime=System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        /*
        Drawable background = Constants.CURRENT_CONTEXT.getResources().getDrawable(R.drawable.spacebackground);
        background.setBounds(0,0,Constants.SCREEN_WIDTH,Constants.SCREEN_HEIGTH);
        canvas.drawColor(Color.WHITE);
        background.draw(canvas);
        */

        //canvas.drawBitmap(background,0,0,backgroundPaint);
        canvas.drawBitmap(background,backgrountSrc,backgrountDest,null);
        //canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        obstacleManager.draw(canvas);

        if(gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenteredText(canvas,paint,"Game Over");
        }
    }
    private void drawCenteredText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);

    }
}
