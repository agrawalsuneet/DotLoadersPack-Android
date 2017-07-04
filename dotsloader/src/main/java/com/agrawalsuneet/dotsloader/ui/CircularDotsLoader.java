package com.agrawalsuneet.dotsloader.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.agrawalsuneet.dotsloader.R;

/**
 * Created by ballu on 04/07/17.
 */

public class CircularDotsLoader extends DotsLoader {

    private final int mNoOfDots = 8;
    private final float SIN_45 = 0.7071f;

    private int mBigCircleRadius = 60;

    protected float[] dotsYCorArr;

    public CircularDotsLoader(Context context) {
        super(context);
        initValues();
    }

    public CircularDotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
        initValues();
    }

    public CircularDotsLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        initValues();
    }

    @Override
    protected void initAttributes(AttributeSet attrs) {
        super.initAttributes(attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DotsLoader, 0, 0);

        this.mBigCircleRadius = typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_bigCircleRadius, 60);

        typedArray.recycle();

        initValues();
    }

    @Override
    protected void initValues() {

        float sin45Radius = SIN_45 * mBigCircleRadius;

        dotsXCorArr = new float[mNoOfDots];
        dotsYCorArr = new float[mNoOfDots];

        for (int i = 0; i < mNoOfDots; i++) {
            dotsXCorArr[i] = dotsYCorArr[i] = mBigCircleRadius + mRadius;
        }

        dotsXCorArr[1] = dotsXCorArr[1] + sin45Radius;
        dotsXCorArr[2] = dotsXCorArr[2] + mBigCircleRadius;
        dotsXCorArr[3] = dotsXCorArr[3] + sin45Radius;

        dotsXCorArr[5] = dotsXCorArr[5] - sin45Radius;
        dotsXCorArr[6] = dotsXCorArr[6] - mBigCircleRadius;
        dotsXCorArr[7] = dotsXCorArr[7] - sin45Radius;

        dotsYCorArr[0] = dotsYCorArr[0] + mBigCircleRadius;
        dotsYCorArr[1] = dotsYCorArr[1] + sin45Radius;
        dotsYCorArr[3] = dotsYCorArr[3] - sin45Radius;

        dotsYCorArr[4] = dotsYCorArr[4] - mBigCircleRadius;
        dotsYCorArr[5] = dotsYCorArr[5] - sin45Radius;
        dotsYCorArr[7] = dotsYCorArr[7] + sin45Radius;

        //init paints for drawing dots
        defaultCirclePaint = new Paint();
        defaultCirclePaint.setAntiAlias(true);
        defaultCirclePaint.setStyle(Paint.Style.FILL);
        defaultCirclePaint.setColor(mDefaultColor);

        selectedCirclePaint = new Paint();
        selectedCirclePaint.setAntiAlias(true);
        selectedCirclePaint.setStyle(Paint.Style.FILL);
        selectedCirclePaint.setColor(mSelectedColor);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mExpandOnSelect) {
            width = (2 * mBigCircleRadius) + (2 * mSelRadius);
            height = width;
        } else {
            width = (2 * mBigCircleRadius) + (2 * mRadius);
            height = width;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mBigCircleRadius + mRadius, mBigCircleRadius + mRadius, mBigCircleRadius, defaultCirclePaint);

        for (int i = 0; i < mNoOfDots; i++) {
            canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, selectedCirclePaint);
        }
    }
}
