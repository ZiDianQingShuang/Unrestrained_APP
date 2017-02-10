package com.unrestrained.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wangxiaofei on 2016/12/14.
 */

public class DefineView extends View {
    public DefineView(Context context) {
        super(context);
        init(null, 0);
    }

    public DefineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DefineView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private Paint mTextPaint, mLinePaint;

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes

        // Set up a default TextPaint object
        mTextPaint = new Paint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        // Update TextPaint and text measurements from attributes

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int baseLineX = 0;
        int baseLineY = 200;

        //画基线
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setColor(Color.RED);

        String content = "harvic's blog";

        float contentlength = paint.measureText(content);

        baseLineX = getWidth() / 2;

        canvas.drawLine(baseLineX, baseLineY, getWidth(), baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        paint.setTextSize(50); //以px为单位
        canvas.drawText("harvic\'s blog", baseLineX, baseLineY, paint);

//      paint.setColor(Color.BLUE);
//      paint.setTextSize(1000);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();

        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, baseLineY + fontMetrics.top, getWidth(), baseLineY + fontMetrics.top, paint);

        paint.setColor(Color.CYAN);
        canvas.drawLine(baseLineX, baseLineY + fontMetrics.ascent, getWidth(), baseLineY + fontMetrics.ascent, paint);

        paint.setColor(Color.BLACK);
        canvas.drawLine(baseLineX, baseLineY + fontMetrics.descent, getWidth(), baseLineY + fontMetrics.descent, paint);

        paint.setColor(Color.DKGRAY);
        canvas.drawLine(baseLineX, baseLineY + fontMetrics.bottom, getWidth(), baseLineY + fontMetrics.bottom, paint);
    }

}
