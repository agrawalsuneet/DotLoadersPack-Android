package com.agrawalsuneet.dotsloader.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.agrawalsuneet.dotsloader.R;

/**
 * Created by Suneet on 13/01/17.
 */
public class ThreeDotsLoader extends View {

    private int mDefaultColor, mSelectedColor;
    private int mRadius, mDotsDist;
    private boolean mIsSingleDir;
    private int mAnimDur;

    private float firstDotX, secondDotX, thirdDotX;

    private Paint mDefaultCirclePaint, mSelectedCirclePaint;

    private boolean shouldAnimate = true;

    int width, height;

    private int selectedDotPos = 1;
    private boolean isFwdDir = true;

    private long logTime;

    public ThreeDotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
    }

    public ThreeDotsLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = (6 * mRadius) + (2 * mDotsDist);
        height = (2 * mRadius);
        setMeasuredDimension(width, height);
    }

    private void initAttributes(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ThreeDotsLoader, 0, 0);
        this.mDefaultColor = typedArray.getColor(R.styleable.ThreeDotsLoader_loader_defaultColor,
                ContextCompat.getColor(getContext(), R.color.loader_defalut));
        this.mSelectedColor = typedArray.getColor(R.styleable.ThreeDotsLoader_loader_defaultColor,
                ContextCompat.getColor(getContext(), R.color.loader_selected));

        this.mRadius = dpToPx(getContext(),
                typedArray.getDimensionPixelSize(R.styleable.ThreeDotsLoader_loader_circleRadius, 30));
        this.mDotsDist = dpToPx(getContext(),
                typedArray.getDimensionPixelSize(R.styleable.ThreeDotsLoader_loader_dotsDist, 15));

        this.mIsSingleDir = typedArray.getBoolean(R.styleable.ThreeDotsLoader_loader_isSingleDir, false);
        this.mAnimDur = typedArray.getInt(R.styleable.ThreeDotsLoader_loader_animDur, 500);
        typedArray.recycle();

        initValues();
    }

    private void initValues() {
        firstDotX = mRadius;
        secondDotX = mDotsDist + (3 * mRadius);
        thirdDotX = (2 * mDotsDist) + (5 * mRadius);

        mDefaultCirclePaint = new Paint();
        mDefaultCirclePaint.setAntiAlias(true);
        mDefaultCirclePaint.setStyle(Paint.Style.FILL);
        mDefaultCirclePaint.setColor(mDefaultColor);

        mSelectedCirclePaint = new Paint();
        mSelectedCirclePaint.setAntiAlias(true);
        mSelectedCirclePaint.setStyle(Paint.Style.FILL);
        mSelectedCirclePaint.setColor(mSelectedColor);
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

                        if (mIsSingleDir && selectedDotPos == 3) {
                            selectedDotPos = 1;
                        } else if (mIsSingleDir) {
                            selectedDotPos++;
                        } else if (!mIsSingleDir && isFwdDir) {
                            selectedDotPos++;
                            if (selectedDotPos == 3) {
                                isFwdDir = !isFwdDir;
                            }
                        } else if (!mIsSingleDir && !isFwdDir) {
                            selectedDotPos--;
                            if (selectedDotPos == 1) {
                                isFwdDir = !isFwdDir;
                            }
                        }

                        invalidate();
                        logTime = System.currentTimeMillis();
                    }
                }
            }, mAnimDur);
        }
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(firstDotX, mRadius, mRadius, selectedDotPos == 1 ? mSelectedCirclePaint : mDefaultCirclePaint);
        canvas.drawCircle(secondDotX, mRadius, mRadius, selectedDotPos == 2 ? mSelectedCirclePaint : mDefaultCirclePaint);
        canvas.drawCircle(thirdDotX, mRadius, mRadius, selectedDotPos == 3 ? mSelectedCirclePaint : mDefaultCirclePaint);
    }

    public void startAnimation() {
        shouldAnimate = true;
        invalidate();
    }

    public void stopAnimation() {
        shouldAnimate = false;
        invalidate();
    }

    public int getAnimDur() {
        return mAnimDur;
    }

    public void setAnimDur(int mAnimDur) {
        this.mAnimDur = mAnimDur;
        initValues();
        invalidate();
    }

    public int getDefaultColor() {
        return mDefaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.mDefaultColor = ContextCompat.getColor(getContext(),defaultColor);
        initValues();
        invalidate();
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = ContextCompat.getColor(getContext(),selectedColor);
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int mRadius) {
        this.mRadius = mRadius;
        initValues();
        invalidate();
    }

    public int getDotsDist() {
        return mDotsDist;
    }

    public void setDotsDist(int mDotsDist) {
        this.mDotsDist = mDotsDist;
        initValues();
        invalidate();
    }

    public boolean isSingleDir() {
        return mIsSingleDir;
    }

    public void setIsSingleDir(boolean mIsSingleDir) {
        this.mIsSingleDir = mIsSingleDir;
        initValues();
        invalidate();
    }

    private int dpToPx(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
