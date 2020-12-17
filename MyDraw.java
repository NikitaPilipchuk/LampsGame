package com.example.lampsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class MyDraw extends View {

    //Переменные
    boolean lampsIndicator[][];
    int N = 4;
    int M = 4;
    float x, y, touchX, touchY;
    int r, ro;

    public MyDraw(Context context) {
        super(context);
        Random random = new Random();
        lampsIndicator = new boolean[N][M];

        for (int i = 0; i < lampsIndicator.length; i++) {
            for (int j = 0; j < lampsIndicator.length; j++){
                lampsIndicator[i][j] = random.nextBoolean();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        r = canvas.getWidth()/(M*4);
        ro = r;

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);

        for (int i = 0; i < lampsIndicator.length; i++ ) {
            for (int j = 0; j < lampsIndicator.length; j++) {
                if (lampsIndicator[i][j] == true) {
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                } else {
                    paint.setStyle(Paint.Style.STROKE);
                }

                x = (j + 1) * (2 * r + ro);
                y = (i + 1) * (2 * r + ro);
                canvas.drawCircle(x, y, r, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
           touchX = event.getX();
           touchY = event.getY();

           changeIndicators();
        }

        return super.onTouchEvent(event);
    }

    void changeIndicators() {
        int i = Math.round(touchY / (2 * r + ro));
        int j = Math.round(touchX / (2 * r + ro));

        if (i <= 4 && j <= 4) {
            x = j * (2 * r + ro);
            y = i * (2 * r + ro);

            if (Math.pow(touchX - x, 2) + Math.pow(touchY - y, 2) <= Math.pow(r,2)) {
                System.out.println("x: " + i + " y: " + j);

                for (int ii = 0; ii < lampsIndicator.length; ii++) {
                    for (int jj = 0; jj < lampsIndicator.length; jj++) {

                        if (ii == i-1) {
                            lampsIndicator[ii][jj] = !lampsIndicator[ii][jj];

                        }

                        if (jj == j-1) {
                            lampsIndicator[ii][jj] = !lampsIndicator[ii][jj];
                        }

                    }
                }

                lampsIndicator[i-1][j-1] = !lampsIndicator[i-1][j-1];
            }
            invalidate();
        }
    }
}
