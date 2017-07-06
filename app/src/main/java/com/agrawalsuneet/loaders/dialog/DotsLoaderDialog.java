package com.agrawalsuneet.loaders.dialog;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agrawalsuneet.loaders.R;
import com.agrawalsuneet.loaders.dialog.helper.LoaderController;
import com.agrawalsuneet.dotsloader.ui.LinearDotsLoader;

/**
 * Created by Suneet on 13/01/17.
 */

public class DotsLoaderDialog extends DialogFragment {

    private View mView;
    private TextView mMessageTextView;
    private LoaderController mController;
    private LinearLayout mContainerLL;
    private LinearDotsLoader mLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != savedInstanceState) {
            mController = savedInstanceState.getParcelable("controller");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_loader, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(mView);

        AlertDialog dialog = builder.create();
        initViews(dialog);
        return dialog;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("controller", mController);
    }

    public void setValues(LoaderController controller) {
        this.mController = controller;
    }

    private void initViews(AlertDialog dialog) {
        mContainerLL = (LinearLayout) mView.findViewById(R.id.dialog_container);
        mMessageTextView = (TextView) mView.findViewById(R.id.dialog_message_tv);
        mLoader = (LinearDotsLoader) mView.findViewById(R.id.dialog_loader);

        if (mController.background != 0) {
            mContainerLL.setBackgroundColor(ContextCompat.getColor(getContext(), mController.background));
        } else {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        if (!TextUtils.isEmpty(mController.message)) {
            mMessageTextView.setText(mController.message);
        }

        if (mController.textSize != 0) {
            mMessageTextView.setTextSize(mController.textSize);
        }

        if (mController.textColor != 0) {
            mMessageTextView.setTextColor(ContextCompat.getColor(getContext(), mController.textColor));
        }

        if (mController.animDur != 0) {
            mLoader.setAnimDur(mController.animDur);
        }

        if (mController.noOfDots != 0){
            mLoader.setNoOfDots(mController.noOfDots);
        }

        if (mController.dotsDefaultColor != 0) {
            mLoader.setDefaultColor(mController.dotsDefaultColor);
        }

        if (mController.dotsSelectedColor != 0) {
            mLoader.setSelectedColor(mController.dotsSelectedColor);
        }

        if (mController.dotsDist != 0) {
            mLoader.setDotsDist(mController.dotsDist);
        }

        if (mController.dotsRadius != 0) {
            mLoader.setRadius(mController.dotsRadius);
        }

        if (mController.dotsSelectedRadius != 0) {
            mLoader.setSelRadius(mController.dotsSelectedRadius);
        }

        mLoader.setIsSingleDir(mController.isLoadingSingleDir);

        mLoader.setExpandOnSelect(mController.isExpandOnSelect);
    }

    public static class Builder {

        private AppCompatActivity mActivity;
        private LoaderController mController;


        public Builder(AppCompatActivity activity) {
            this.mActivity = activity;
            mController = new LoaderController();
        }

        public Builder setTextSize(float size) {
            this.mController.setTextSize(size);
            return this;
        }

        public Builder setTextColor(int color) {
            this.mController.setTextColor(color);
            return this;
        }

        public Builder setMessage(String message) {
            this.mController.setMessage(message);
            return this;
        }

        public Builder setNoOfDots(int noOfDots){
            this.mController.setNoOfDots(noOfDots);
            return this;
        }

        public Builder setDotsRadius(int radius) {
            this.mController.setDotsRadius(radius);
            return this;
        }

        public Builder setDotsDistance(int distance) {
            this.mController.setDotsDist(distance);
            return this;
        }

        public Builder setDotsSelectedRadius(int selectedRadius) {
            this.mController.setDotsSelectedRadius(selectedRadius);
            return this;
        }

        public Builder setDotsDefaultColor(int color) {
            this.mController.setDotsDefaultColor(color);
            return this;
        }

        public Builder setDotsSelectedColor(int color) {
            this.mController.setDotsSelectedColor(color);
            return this;
        }

        public Builder setIsLoadingSingleDirection(boolean isSingleDir) {
            this.mController.setLoadingSingleDir(isSingleDir);
            return this;
        }

        public Builder setExpandOnSelect(boolean expandOnSelect) {
            this.mController.setExpandOnSelect(expandOnSelect);
            return this;
        }

        public Builder setAnimDuration(int milliSecs) {
            this.mController.setAnimDur(milliSecs);
            return this;
        }

        public Builder setBackground(int background) {
            this.mController.setBackground(background);
            return this;
        }


        public DotsLoaderDialog create() {
            DotsLoaderDialog dialog = new DotsLoaderDialog();
            dialog.setValues(this.mController);
            return dialog;
        }

        public DotsLoaderDialog show() {
            DotsLoaderDialog dialog = create();
            dialog.show(mActivity.getSupportFragmentManager(), "");
            return dialog;
        }
    }
}
