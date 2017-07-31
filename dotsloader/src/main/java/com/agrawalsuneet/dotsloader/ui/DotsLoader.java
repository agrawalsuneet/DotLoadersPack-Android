package com.agrawalsuneet.dotsloader.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.agrawalsuneet.dotsloader.R;

/**
 * Created by Suneet on 13/01/17.
 */
public abstract class DotsLoader extends View {

    protected int mDefaultColor = getResources().getColor(R.color.loader_defalut),
            mSelectedColor = getResources().getColor(R.color.loader_selected);

    protected int mRadius = 30;

    protected int mAnimDur = 500;

    protected float[] dotsXCorArr;

    protected Paint defaultCirclePaint, selectedCirclePaint;

    protected int width, height;

    protected boolean shouldAnimate = true;

    protected int selectedDotPos = 1;

    protected long logTime;

    public DotsLoader(Context context) {
        super(context);
    }

    public DotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DotsLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void initAttributes(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DotsLoader, 0, 0);

        this.mDefaultColor = typedArray.getColor(R.styleable.DotsLoader_loader_defaultColor,
                getResources().getColor(R.color.loader_defalut));
        this.mSelectedColor = typedArray.getColor(R.styleable.DotsLoader_loader_selectedColor,
                getResources().getColor(R.color.loader_selected));

        this.mRadius = typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_circleRadius, 30);

        this.mAnimDur = typedArray.getInt(R.styleable.DotsLoader_loader_animDur, 500);

        typedArray.recycle();
    }

    protected abstract void initCordinates();

    protected abstract void initPaints();

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

    public void setAnimDur(int animDur) {
        this.mAnimDur = animDur;
    }

    public int getDefaultColor() {
        return mDefaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.mDefaultColor = defaultColor;
        defaultCirclePaint.setColor(defaultColor);
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = selectedColor;
        selectedCirclePaint.setColor(selectedColor);
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
        initCordinates();
    }
}
