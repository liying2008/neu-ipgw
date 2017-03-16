package com.liying.ipgw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liying.ipgw.callback.OnCalendarZoom;

/**
 * =======================================================
 * 作者：liying - liruoer2008@yeah.net
 * 日期：2016/11/4 18:01
 * 版本：1.0
 * 描述：可使用双指缩放的SimpleDraweeView
 * 备注：来自：https://gist.github.com/nbarraille/eb5d0da20bc813969b08
 * =======================================================
 */
public class PinchToZoomDraweeView extends SimpleDraweeView {
    private final ScaleGestureDetector mScaleDetector;
    private final ScaleGestureDetector.OnScaleGestureListener mScaleListener;

    private float mCurrentScale;
    private final Matrix mCurrentMatrix;
    private float mMidX;
    private float mMidY;
    private OnCalendarZoom zoomListener;
    @Nullable private OnZoomChangeListener mListener;

    public PinchToZoomDraweeView(Context context) {
        this(context, null);
    }

    public PinchToZoomDraweeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 设置缩放监听器
     * @param zoomListener
     */
    public void setCalendarZoomListener(OnCalendarZoom zoomListener) {
        this.zoomListener = zoomListener;
    }
    public PinchToZoomDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mScaleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                float newScale = mCurrentScale * scaleFactor;
                // Prevent from zooming out more than original
                if (newScale > 1.0f) {
                    // We initialize this lazily so that we don't have to register (and force the user
                    // to unregister) a global layout listener on the view.
                    if (mMidX == 0.0f) {
                        mMidX = getWidth() / 2.0f;
                    }
                    if (mMidY == 0.0f) {
                        mMidY = getHeight() / 2.0f;
                    }
                    mCurrentScale = newScale;
                    mCurrentMatrix.postScale(scaleFactor, scaleFactor, mMidX, mMidY);
                    invalidate();
                } else {
                    scaleFactor = 1.0f / mCurrentScale;
                    reset();
                }

                if (mListener != null && scaleFactor != 1.0f) {
                    mListener.onZoomChange(mCurrentScale);
                }

                // 通知CalendarActivity执行相关动作（触发彩蛋）
                if (zoomListener != null) {
                    zoomListener.zoomCalendar();
                }

                return true;
            }
        };
        mScaleDetector = new ScaleGestureDetector(getContext(), mScaleListener);
        mCurrentMatrix = new Matrix();
    }

    public void setOnZoomChangeListener(OnZoomChangeListener listener) {
        mListener = listener;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setOnZoomChangeListener(null);
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(mCurrentMatrix);
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mScaleDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }

    /**
     * Resets the zoom on that view
     */
    public void resetZoom() {
        reset();
    }

    /**
     * Resets the zoom of the attached image.
     * This has no effect if the image has been destroyed
     */
    public void reset() {
        mCurrentMatrix.reset();
        mCurrentScale = 1.0f;
        invalidate();
    }

    /**
     * A listener interface for when the zoom scale changes
     */
    public interface OnZoomChangeListener {
        /**
         * Callback method getting triggered when the zoom scale changes.
         * This is not called when the zoom is programmatically reset
         * @param scale the new scale
         */
        public void onZoomChange(float scale);
    }
}