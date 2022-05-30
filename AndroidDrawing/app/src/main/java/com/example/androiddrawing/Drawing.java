package com.example.androiddrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.view.View;

public class Drawing extends View {

    private Paint brush1;
    private Paint brush2;
    private Paint brush3;
    private RectF rectf;
    private LinearGradient linearGradient;
    private RadialGradient radialGradient;
    private SweepGradient sweepGradient;

    public Drawing(Context context) {
        super(context);
        init();
    }

    private void init() {
        linearGradient = new LinearGradient(0,0,200,200,Color.RED,Color.BLACK, Shader.TileMode.MIRROR);
        radialGradient = new RadialGradient(0,0,200,Color.GREEN,Color.BLUE, Shader.TileMode.MIRROR);

        int[] colors = {Color.GREEN, Color.RED};
        float[] positions = {0, 1};
        sweepGradient = new SweepGradient(200,200,colors,positions);

        brush1 = new Paint();
        brush1.setAntiAlias(true);
        brush1.setColor(Color.BLUE);
        brush1.setShader(sweepGradient);

        brush2 = new Paint();
        brush2.setAntiAlias(true);
        brush2.setColor(Color.GREEN);
        brush2.setShader(radialGradient);

        rectf = new RectF();
        rectf.set(500,500,1000,1000);

        brush3 = new Paint();
        brush3.setAntiAlias(true);
        brush3.setColor(Color.RED);
        brush3.setStrokeWidth(200f);
        brush3.setShader(linearGradient);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawCircle(200,200,200,brush1);
        canvas.drawRect(rectf,brush2);
        canvas.drawLine(300,1200,800,1200,brush3);

        super.onDraw(canvas);
    }
}
