package com.agrawalsuneet.dotsloader.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.agrawalsuneet.dotsloader.R;

/**
 * Created by Suneet on 13/01/17.
 */
public abstract class DotsLoader extends View {

    protected int mDefaultColor = ContextCompat.getColor(getContext(), R.color.loader_defalut),
            mSelectedColor = ContextCompat.getColor(getContext(), R.color.loader_selected);

    protected int mRadius = 30;
    protected int mSelRadius = 38;

    protected int mAnimDur = 500;

    protected boolean mExpandOnSelect = false;

    protected float[] dotsXCorArr;

    protected Paint defaultCirclePaint, selectedCirclePaint;

    protected int width, height;

    protected boolean shouldAnimate = true;

    protected int selectedDotPos = 1;

    protected int diffRadius;

    protected long logTime;

    public DotsLoader(Context context) {
        super(context);
        initValues();
    }

    public DotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
    }

    public DotsLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
    }



    protected void initAttributes(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DotsLoader, 0, 0);



        this.mDefaultColor = typedArray.getColor(R.styleable.DotsLoader_loader_defaultColor,
                ContextCompat.getColor(getContext(), R.color.loader_defalut));
        this.mSelectedColor = typedArray.getColor(R.styleable.DotsLoader_loader_selectedColor,
                ContextCompat.getColor(getContext(), R.color.loader_selected));

        this.mRadius = typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_circleRadius, 30);

        this.mSelRadius = typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_selectedRadius, mRadius + 10);

        this.mAnimDur = typedArray.getInt(R.styleable.DotsLoader_loader_animDur, 500);
        this.mExpandOnSelect = typedArray.getBoolean(R.styleable.DotsLoader_loader_expandOnSelect, false);
        typedArray.recycle();
    }

    protected abstract void initValues();

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
        initValues();
        invalidate();
    }

    public int getDefaultColor() {
        return mDefaultColor;
    }

    public void setDefaultColor(int defaultColor) {
        this.mDefaultColor = ContextCompat.getColor(getContext(), defaultColor);
        initValues();
        invalidate();
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        this.mSelectedColor = ContextCompat.getColor(getContext(), selectedColor);
        initValues();
        invalidate();
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        this.mRadius = radius;
        initValues();
        invalidate();
    }

    public boolean isExpandOnSelect() {
        return mExpandOnSelect;
    }

    public void setExpandOnSelect(boolean expandOnSelect) {
        this.mExpandOnSelect = expandOnSelect;
        initValues();
        invalidate();
    }

    public int getSelRadius() {
        return mSelRadius;
    }

    public void setSelRadius(int selRadius) {
        this.mSelRadius = selRadius;
        initValues();
        invalidate();
    }
}
