package com.agrawalsuneet.dotsloader.dialog.helper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Suneet on 13/01/17.
 */

public class LoaderController implements Parcelable {

    public float textSize;
    public int textColor;
    public String message;
    public int noOfDots;
    public int dotsRadius, dotsDist, dotsSelectedRadius;
    public int dotsDefaultColor, dotsSelectedColor;
    public boolean isLoadingSingleDir, isExpandOnSelect;
    public int animDur;
    public int background;

    public LoaderController() {
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setNoOfDots(int noOfDots) {
        this.noOfDots = noOfDots;
    }

    public void setDotsRadius(int dotsRadius) {
        this.dotsRadius = dotsRadius;
    }


    public void setDotsDist(int dotsDist) {
        this.dotsDist = dotsDist;
    }

    public void setDotsSelectedRadius(int dotsSelectedRadius) {
        this.dotsSelectedRadius = dotsSelectedRadius;
    }

    public void setDotsDefaultColor(int dotsDefaultColor) {
        this.dotsDefaultColor = dotsDefaultColor;
    }

    public void setDotsSelectedColor(int dotsSelectedColor) {
        this.dotsSelectedColor = dotsSelectedColor;
    }

    public void setLoadingSingleDir(boolean loadingSingleDir) {
        isLoadingSingleDir = loadingSingleDir;
    }

    public void setExpandOnSelect(boolean expandOnSelect) {
        isExpandOnSelect = expandOnSelect;
    }

    public void setAnimDur(int animDur) {
        this.animDur = animDur;
    }


    public void setBackground(int background) {
        this.background = background;
    }

    protected LoaderController(Parcel in) {
        textSize = in.readFloat();
        textColor = in.readInt();
        message = in.readString();
        noOfDots = in.readInt();
        dotsRadius = in.readInt();
        dotsDist = in.readInt();
        dotsSelectedRadius = in.readInt();
        dotsDefaultColor = in.readInt();
        dotsSelectedColor = in.readInt();
        isLoadingSingleDir = in.readByte() != 0;
        isExpandOnSelect = in.readByte() != 0;
        animDur = in.readInt();
        background = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(textSize);
        dest.writeInt(textColor);
        dest.writeString(message);
        dest.writeInt(noOfDots);
        dest.writeInt(dotsRadius);
        dest.writeInt(dotsDist);
        dest.writeInt(dotsSelectedRadius);
        dest.writeInt(dotsDefaultColor);
        dest.writeInt(dotsSelectedColor);
        dest.writeByte((byte) (isLoadingSingleDir ? 1 : 0));
        dest.writeByte((byte) (isExpandOnSelect ? 1 : 0));
        dest.writeInt(animDur);
        dest.writeInt(background);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LoaderController> CREATOR = new Creator<LoaderController>() {
        @Override
        public LoaderController createFromParcel(Parcel in) {
            return new LoaderController(in);
        }

        @Override
        public LoaderController[] newArray(int size) {
            return new LoaderController[size];
        }
    };
}
