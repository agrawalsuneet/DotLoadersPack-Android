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
public class DotsLoader extends View {

    private int mDefaultColor = ContextCompat.getColor(getContext(), R.color.loader_defalut),
            mSelectedColor = ContextCompat.getColor(getContext(), R.color.loader_selected);
    private int mRadius = 30;
    private int mDotsDist = 15;
    private boolean mIsSingleDir = true;
    private int mAnimDur = 500;
    private int mNoOfDots = 3;
    private int mSelRadius = 38;
    private boolean mExpandOnSelect = false;

    private float[] dotsXCorArr;

    private Paint defaultCirclePaint, selectedCirclePaint;

    int width, height;

    private boolean shouldAnimate = true;
    private boolean isFwdDir = true;

    private int selectedDotPos = 1;

    private int diffRadius;

    private long logTime;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mExpandOnSelect) {
            width = (2 * mNoOfDots * mRadius) + ((mNoOfDots - 1) * mDotsDist) + (2 * diffRadius);
            height = (2 * mSelRadius);
        } else {
            height = (2 * mRadius);
            width = (2 * mNoOfDots * mRadius) + ((mNoOfDots - 1) * mDotsDist);
        }
        setMeasuredDimension(width, height);
    }

    private void initAttributes(AttributeSet attrs) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DotsLoader, 0, 0);

        this.mNoOfDots = typedArray.getInt(R.styleable.DotsLoader_loader_noOfDots, 3);

        this.mDefaultColor = typedArray.getColor(R.styleable.DotsLoader_loader_defaultColor,
                ContextCompat.getColor(getContext(), R.color.loader_defalut));
        this.mSelectedColor = typedArray.getColor(R.styleable.DotsLoader_loader_selectedColor,
                ContextCompat.getColor(getContext(), R.color.loader_selected));

        this.mRadius = dpToPx(getContext(),
                typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_circleRadius, 30));

        this.mSelRadius = dpToPx(getContext(),
                typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_selectedRadius, mRadius + 10));

        this.mDotsDist = dpToPx(getContext(),
                typedArray.getDimensionPixelSize(R.styleable.DotsLoader_loader_dotsDist, 15));

        this.mIsSingleDir = typedArray.getBoolean(R.styleable.DotsLoader_loader_isSingleDir, false);
        this.mAnimDur = typedArray.getInt(R.styleable.DotsLoader_loader_animDur, 500);
        this.mExpandOnSelect = typedArray.getBoolean(R.styleable.DotsLoader_loader_expandOnSelect, false);
        typedArray.recycle();

        initValues();
    }

    private void initValues() {

        if (mNoOfDots < 1) {
            mNoOfDots = 3;
        } else if (mNoOfDots == 1) {
            shouldAnimate = false;
        }

        diffRadius = mSelRadius - mRadius;

        dotsXCorArr = new float[mNoOfDots];

        //init X cordinates for all dots
        for (int i = 0; i < mNoOfDots; i++) {
            dotsXCorArr[i] = (i * mDotsDist) + (((i * 2) + 1) * mRadius);
        }

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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircle(canvas);

        if (shouldAnimate) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    if ((System.currentTimeMillis() - logTime) >= mAnimDur) {

                        if (mIsSingleDir && selectedDotPos == mNoOfDots) {
                            selectedDotPos = 1;
                        } else if (mIsSingleDir) {
                            selectedDotPos++;
                        } else if (!mIsSingleDir && isFwdDir) {
                            selectedDotPos++;
                            if (selectedDotPos == mNoOfDots) {
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
        for (int i = 0; i < mNoOfDots; i++) {
            /*canvas.drawCircle(dotsXCorArr[i], mRadius, mRadius,
                    i + 1 == selectedDotPos ? selectedCirclePaint : defaultCirclePaint);*/

            boolean isSelected = (i + 1 == selectedDotPos);

            float xCor = dotsXCorArr[i];
            if (mExpandOnSelect) {
                if (i + 1 == selectedDotPos) {
                    xCor += diffRadius;
                } else if (i + 1 > selectedDotPos) {
                    xCor += 2 * diffRadius;
                }
            }

            canvas.drawCircle(
                    xCor,
                    mExpandOnSelect ? mSelRadius : mRadius,
                    mExpandOnSelect && isSelected ? mSelRadius : mRadius,
                    isSelected ? selectedCirclePaint : defaultCirclePaint);
        }
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

    public int getNoOfDots() {
        return mNoOfDots;
    }

    public void setNoOfDots(int noOfDots) {
        this.mNoOfDots = mNoOfDots;
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

    private int dpToPx(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
