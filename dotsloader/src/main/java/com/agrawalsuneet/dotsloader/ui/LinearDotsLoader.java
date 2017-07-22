package com.agrawalsuneet.dotsloader.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;

import com.agrawalsuneet.dotsloader.R;

/**
 * Created by ballu on 04/07/17.
 */

public class LinearDotsLoader extends DotsLoader {


    private int mNoOfDots = 3;
    private int mDotsDist = 15;

    protected int mSelRadius = 38;
    protected int diffRadius;

    private boolean mIsSingleDir = true;

    private boolean isFwdDir = true;

    protected boolean mExpandOnSelect = false;

    public LinearDotsLoader(Context context) {
        super(context);
        initValues();
    }

    public LinearDotsLoader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
    }

    public LinearDotsLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
    }

    @Override
    protected void initAttributes(AttributeSet attrs) {
        super.initAttributes(attrs);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LinearDotsLoader, 0, 0);
        this.mNoOfDots = typedArray.getInt(R.styleable.LinearDotsLoader_loader_noOfDots, 3);

        this.mSelRadius = typedArray.getDimensionPixelSize(R.styleable.LinearDotsLoader_loader_selectedRadius, mRadius + 10);

        this.mDotsDist = typedArray.getDimensionPixelSize(R.styleable.LinearDotsLoader_loader_dotsDist, 15);

        this.mIsSingleDir = typedArray.getBoolean(R.styleable.LinearDotsLoader_loader_isSingleDir, false);
        this.mExpandOnSelect = typedArray.getBoolean(R.styleable.LinearDotsLoader_loader_expandOnSelect, false);

        typedArray.recycle();

        initValues();
    }

    @Override
    protected void initValues() {
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

    public void setDotsDist(int dotsDist) {
        this.mDotsDist = dotsDist;
        initValues();
        invalidate();
    }

    public int getDotsDist() {
        return mDotsDist;
    }

    public void setNoOfDots(int noOfDots) {
        this.mNoOfDots = noOfDots;
        initValues();
        invalidate();
    }

    public int getNoOfDots() {
        return mNoOfDots;
    }

    public int getSelRadius() {
        return mSelRadius;
    }

    public void setSelRadius(int selRadius) {
        this.mSelRadius = selRadius;
        initValues();
        invalidate();
    }

    public boolean isSingleDir() {
        return mIsSingleDir;
    }

    public void setIsSingleDir(boolean isSingleDir) {
        this.mIsSingleDir = isSingleDir;
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

}
