package com.vivian.apputil.widget.camera;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.vivian.apputil.R;

/**
 * Created by vivianWQ on 2018/3/24
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class CameraProgressBar extends View {

    /**
     * 内圆颜色
     */
    private int innerColor = Color.GRAY;

    /**
     * 背景颜色
     */
    private int backgroundColor = Color.WHITE;

    /**
     * 手势识别
     */
    private GestureDetectorCompat gestureDetectorCompat;

    /**
     * 外圆颜色,进度条颜色
     */
    private int outerColor, progressColor;
    /**
     * 进度宽
     */
    private int progressWidth = 10;
    /**
     * 内圆宽度
     */
    private int innerRadio = 6;
    /**
     * 进度
     */
    private int progress;
    /**
     * 最大进度
     */
    private int maxProgress = 100;

    /**
     * 记录上一次Y轴坐标点
     */
    private float mLastY;
    /**
     * paint
     */
    private Paint backgroundPaint, progressPaint, innerPaint;
    /**
     * 圆的中心坐标点, 进度百分比
     */
    private float sweepAngle;

    /**
     * 是否为长按录制
     */
    private boolean isLongClick;
    /**
     * 滑动单位
     */
    private int mTouchSlop;
    /**
     * 是否产生滑动
     */
    private boolean isBeingDrag;
    /**
     * s
     */
    private float scale;
    /**
     * 是否长按放大
     */
    private boolean isLongScale;
    private OnProgressTouchListener onProgressTouchListener;

    public void setOnProgressTouchListener(OnProgressTouchListener onProgressTouchListener) {
        this.onProgressTouchListener = onProgressTouchListener;
    }

    public CameraProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public CameraProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CameraProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        if (attributeSet != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CameraProgressBar);
            innerColor = typedArray.getColor(R.styleable.CameraProgressBar_innerColor, innerColor);
            outerColor = typedArray.getColor(R.styleable.CameraProgressBar_outerColor, ContextCompat.getColor(context, R.color.outerColor));
            progressColor = typedArray.getColor(R.styleable.CameraProgressBar_progressColor, ContextCompat.getColor(context, R.color.progressColor));
            innerRadio = typedArray.getDimensionPixelOffset(R.styleable.CameraProgressBar_innerRadio, innerRadio);
            progressWidth = typedArray.getDimensionPixelOffset(R.styleable.CameraProgressBar_progressWidth, progressWidth);
            progress = typedArray.getInt(R.styleable.CameraProgressBar_progressCamera, progress);
            scale = typedArray.getFloat(R.styleable.CameraProgressBar_scale, scale);
            isLongScale = typedArray.getBoolean(R.styleable.CameraProgressBar_isLongScale, isLongScale);
            maxProgress = typedArray.getInt(R.styleable.CameraProgressBar_maxProgress, maxProgress);
            typedArray.recycle();
        }else{
            outerColor=ContextCompat.getColor(context, R.color.outerColor);
            progressColor=ContextCompat.getColor(context, R.color.progressColor);
            isLongScale=true;

        }

        backgroundPaint = new Paint();
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setColor(backgroundColor);

        progressPaint = new Paint();
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeWidth(progressWidth);
        progressPaint.setStyle(Paint.Style.STROKE);

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStrokeWidth(innerRadio);
        innerPaint.setStyle(Paint.Style.STROKE);

        sweepAngle = ((float) progress / maxProgress) * 360;

        gestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            /**
             * 用户短按事件
             * @param e
             * @return
             */
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                isLongClick = false;
                if (CameraProgressBar.this.onProgressTouchListener != null) {
                    CameraProgressBar.this.onProgressTouchListener.onClick(CameraProgressBar.this);
                }

                return super.onSingleTapConfirmed(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                isLongClick = true;
                postInvalidate();
                if (CameraProgressBar.this.onProgressTouchListener != null)
                    CameraProgressBar.this.onProgressTouchListener.onLongClick(CameraProgressBar.this);
            }
        });
        gestureDetectorCompat.setIsLongpressEnabled(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isLongScale) {
            return super.onTouchEvent(event);
        }
        this.gestureDetectorCompat.onTouchEvent(event);
        switch(MotionEventCompat.getActionMasked(event)) {
            case MotionEvent.ACTION_DOWN:
                isLongClick = false;
                isBeingDrag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isLongClick) {
                    float y = event.getY();
                    if (isBeingDrag) {
                        boolean isUpScroll = y < mLastY;
                        mLastY = y;
                        if (this.onProgressTouchListener != null) {
                            this.onProgressTouchListener.onZoom(isUpScroll);
                        }
                    } else {
                        isBeingDrag = Math.abs(y - mLastY) > mTouchSlop;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                isBeingDrag = false;
                if (isLongClick) {
                    isLongClick = false;
                    postInvalidate();
                    if (this.onProgressTouchListener != null) {
                        this.onProgressTouchListener.onLongClickUp(this);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (isLongClick) {
                    if (this.onProgressTouchListener != null) {
                        this.onProgressTouchListener.onPointerDown(event.getRawX(), event.getRawY());
                    }
                }
                break;
        }
        return true;
    }


    /**
     * 设置进度
     * @param progress
     */
    public void setProgress(int progress) {
        if (progress <= 0) progress = 0;
        if (progress >= maxProgress) progress = maxProgress;
        if (progress == this.progress) return;
        this.progress = progress;
        this.sweepAngle = ((float) progress / maxProgress) * 360;
        postInvalidate();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width > height) {
            setMeasuredDimension(height, height);
        } else {
            setMeasuredDimension(width, width);
        }
    }

    public int getProgress() {
        return progress;
    }

    /**
     * 还原到初始状态
     */
    public void reset() {
        isLongClick = false;
        this.progress = 0;
        this.sweepAngle = 0;
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        float circle = width / 2.0f;

//        if (/*isLongScale && */!isLongClick) {
//            canvas.scale(scale, scale, circle, circle);
//        }


        //画内圆
        float backgroundRadio = circle - progressWidth - innerRadio;
        canvas.drawCircle(circle, circle, backgroundRadio, backgroundPaint);

        //画内外环
        float halfInnerWidth = innerRadio / 2.0f + progressWidth;
        RectF innerRectF = new RectF(halfInnerWidth, halfInnerWidth, width - halfInnerWidth, width - halfInnerWidth);
        canvas.drawArc(innerRectF, -90, 360, true, innerPaint);

        progressPaint.setColor(outerColor);
        float halfOuterWidth = progressWidth / 2.0f;
        RectF outerRectF = new RectF(halfOuterWidth, halfOuterWidth, getWidth() - halfOuterWidth, getWidth() - halfOuterWidth);
        canvas.drawArc(outerRectF, -90, 360, true, progressPaint);

        progressPaint.setColor(progressColor);
        canvas.drawArc(outerRectF, -90, sweepAngle, false, progressPaint);

    }

    public interface OnProgressTouchListener {

        /**
         * 单击
         *
         * @param progressBar
         */
        void onClick(CameraProgressBar progressBar);

        /**
         * 长按
         *
         * @param progressBar
         */
        void onLongClick(CameraProgressBar progressBar);

        /**
         * 移动
         *
         * @param zoom true 放大
         */
        void onZoom(boolean zoom);

        /**
         * 长按抬起
         *
         * @param progressBar
         */
        void onLongClickUp(CameraProgressBar progressBar);

        /**
         * 触摸对焦
         *
         * @param rawX
         * @param rawY
         */
        void onPointerDown(float rawX, float rawY);
    }
}
