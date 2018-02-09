package org.deiverbum.liturgiacatolica.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.TextView;

// Created by cedano on 19/1/18.


public class ZoomTextView extends android.support.v7.widget.AppCompatTextView implements View.OnTouchListener {
    final static float STEP = 200;
    private static final String TAG = "ZoomTextView";
    TextView mTextView, mtxtRatio2, mtxtRatio3, mtxtRatio4;
    float mRatio = 13.0f;
    int mBaseDist;
    float mBaseRatio;
    float fontsize = 13;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;
    private float defaultSize;
    private float zoomLimit = 7.0f;


    public ZoomTextView(Context context) {
        super(context);
        //    initialize();
    }

    public ZoomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //     initialize();
    }

    public ZoomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //       initialize();
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
//

    //@Override

    //public boolean onTouchEvent(@NonNull MotionEvent ev) {

    //super.onTouchEvent(ev);

    //mScaleDetector.onTouchEvent(ev);

    //return true;

    //}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        //Log.d(TAG,"----------ok: "+this.getTextSize());
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
        }
        return true;

    }

    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return false;
    }


}