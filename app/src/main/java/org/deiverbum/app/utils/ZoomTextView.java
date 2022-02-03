package org.deiverbum.app.utils;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

// Created by cedano on 19/1/18.


public class ZoomTextView extends androidx.appcompat.widget.AppCompatTextView implements View.OnTouchListener {
    final static float STEP = 200;
    private static final String TAG = "ZoomTextView";
    float mRatio = 13.0f;
    int mBaseDist;
    float mBaseRatio;
    private float zoomLimit = 7.0f;


    public ZoomTextView(Context context) {
        super(context);
        this.setTextIsSelectable(true);
    }

    public ZoomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTextIsSelectable(true);
    }

    public ZoomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setTextIsSelectable(true);
    }

    /*
    private void initialize() {
        defaultSize = getTextSize();
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());

    }
*/

    /***
     * @param zoomLimit
     * Default value is 3, 3 means text can zoom 3 times the default size
     */

    public void setZoomLimit(float zoomLimit) {
        this.zoomLimit = zoomLimit;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (event.getPointerCount() == 2) {
            int action = event.getAction();
            int pureaction = action & MotionEvent.ACTION_MASK;
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event);
                mBaseRatio = mRatio;
            } else {
                float delta = (getDistance(event) - mBaseDist) / STEP;
                float multi = (float) Math.pow(2, delta);
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                this.setTextSize(mRatio + 13);
            }
        } else {
            return super.onTouchEvent(event);
        }
        return true;
    }

    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    public void setTextIsSelectable(boolean selectable) {
        super.setTextIsSelectable(selectable);
    }

    @Override
    public boolean isTextSelectable() {
        return super.isTextSelectable();
    }

}