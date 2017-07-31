package com.agrawalsuneet.dotsloader.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;

import com.agrawalsuneet.dotsloader.R;
import com.agrawalsuneet.dotsloader.utils.Helper;

/**
 * Created by ballu on 04/07/17.
 */

public class CircularDotsLoader extends DotsLoader {

    private final int mNoOfDots = 8;
    private final float SIN_45 = 0.7071f;

    private int mBigCircleRadius = 60;

    private boolean showRunningShadow = true;
    protected float[] dotsYCorArr;

    private Paint firstShadowPaint, secondShadowPaint;
    private int firstShadowColor = 0,
            secondShadowColor = 0;

    private boolean isShadowColorSet = false;

    public CircularDotsLoader(Context context) {
        super(context);
        initValues();
    }

    public CircularDotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
        initValues();
        setShadowProperty();
    }

    public CircularDotsLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        initValues();
        setShadowProperty();
    }

    @Override
    protected void initAttributes(AttributeSet attrs) {
        super.initAttributes(attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircularDotsLoader, 0, 0);

        this.mBigCircleRadius = typedArray.getDimensionPixelSize(R.styleable.CircularDotsLoader_loader_bigCircleRadius, 60);
        this.showRunningShadow = typedArray.getBoolean(R.styleable.CircularDotsLoader_loader_showRunningShadow, true);

        this.firstShadowColor = typedArray.getColor(R.styleable.CircularDotsLoader_loader_firstShadowColor, 0);
        this.secondShadowColor = typedArray.getColor(R.styleable.CircularDotsLoader_loader_secondShadowColor, 0);

        isShadowColorSet = true;

        typedArray.recycle();
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

        dotsYCorArr[0] = dotsYCorArr[0] - mBigCircleRadius;
        dotsYCorArr[1] = dotsYCorArr[1] - sin45Radius;
        dotsYCorArr[3] = dotsYCorArr[3] + sin45Radius;

        dotsYCorArr[4] = dotsYCorArr[4] + mBigCircleRadius;
        dotsYCorArr[5] = dotsYCorArr[5] + sin45Radius;
        dotsYCorArr[7] = dotsYCorArr[7] - sin45Radius;

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

    private void setShadowProperty() {
        if (showRunningShadow) {
            if (!isShadowColorSet) {
                firstShadowColor = Helper.adjustAlpha(mSelectedColor, 0.7f);
                secondShadowColor = Helper.adjustAlpha(mSelectedColor, 0.5f);
                isShadowColorSet = true;
            }

            firstShadowPaint = new Paint();
            firstShadowPaint.setAntiAlias(true);
            firstShadowPaint.setStyle(Paint.Style.FILL);
            firstShadowPaint.setColor(firstShadowColor);

            secondShadowPaint = new Paint();
            secondShadowPaint.setAntiAlias(true);
            secondShadowPaint.setStyle(Paint.Style.FILL);
            secondShadowPaint.setColor(secondShadowColor);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = (2 * mBigCircleRadius) + (2 * mRadius);
        height = width;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);

        if (shouldAnimate) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if ((System.currentTimeMillis() - logTime) >= mAnimDur) {

                        selectedDotPos++;

                        if (selectedDotPos > mNoOfDots) {
                            selectedDotPos = 1;
                        }

                        invalidate();
                        logTime = System.currentTimeMillis();
                    }
                }
            }, mAnimDur);
        }
    }

    private void drawCircle(Canvas canvas) {
        int firstShadowPos = selectedDotPos == 1 ? 8 : selectedDotPos - 1;
        int secondShadowPos = firstShadowPos == 1 ? 8 : firstShadowPos - 1;

        for (int i = 0; i < mNoOfDots; i++) {
            //boolean isSelected = (i + 1 == selectedDotPos);
            //canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, isSelected ? selectedCirclePaint : defaultCirclePaint);

            if (i + 1 == selectedDotPos) {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, selectedCirclePaint);
            } else if (showRunningShadow && i + 1 == firstShadowPos) {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, firstShadowPaint);
            } else if (showRunningShadow && i + 1 == secondShadowPos) {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, secondShadowPaint);
            } else {
                canvas.drawCircle(dotsXCorArr[i], dotsYCorArr[i], mRadius, defaultCirclePaint);
            }

        }
    }

    public int getBigCircleRadius() {
        return mBigCircleRadius;
    }

    public void setBigCircleRadius(int bigCircleRadius) {
        this.mBigCircleRadius = bigCircleRadius;
        initValues();
        invalidate();
    }

    @Override
    public void setSelectedColor(int selectedColor) {
        super.setSelectedColor(selectedColor);
        setShadowProperty();
    }

    public boolean isShowRunningShadow() {
        return showRunningShadow;
    }

    public void setShowRunningShadow(boolean showRunningShadow) {
        this.showRunningShadow = showRunningShadow;
        initValues();
    }

    public int getFirstShadowColor() {
        return firstShadowColor;
    }

    public void setFirstShadowColor(int firstShadowColor) {
        this.firstShadowColor = firstShadowColor;
        isShadowColorSet = true;
        setShadowProperty();
    }

    public int getSecondShadowColor() {
        return secondShadowColor;
    }

    public void setSecondShadowColor(int secondShadowColor) {
        this.secondShadowColor = secondShadowColor;
        isShadowColorSet = true;
        setShadowProperty();
    }
}
