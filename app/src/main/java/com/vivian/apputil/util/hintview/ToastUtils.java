package com.vivian.apputil.util.hintview;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vivian.apputil.R;

/**
 * Created by vivianWQ on 2018/3/23
 * Mail: wangqi_vivian@sina.com
 * desc: toast帮助类 解决关闭悬浮窗权限后无法展示原生toast的问题
 * Version: 1.0
 */
public class ToastUtils {


    // 动画时间
    private final int ANIMATION_DURATION = 600;
    private TextView tvToast;
    private View mView;
    // 默认展示时间
    private int HIDE_DELAY = 2000;
    private RelativeLayout mContainer;
    private AlphaAnimation mFadeOutAnimation;
    private AlphaAnimation mFadeInAnimation;
    private ViewGroup container;
    private boolean isShow = false;
    private Handler mHandler = new Handler();
    private ToastDismiss toastDismiss;

    private ToastUtils(Context context, String message) {
        mView = View.inflate(context, R.layout.dialog_toast, null);
        container =  ((Activity) context).findViewById(android.R.id.content);
        mContainer = mView.findViewById(R.id.mbContainer);
        tvToast = mView.findViewById(R.id.tvToast);
        tvToast.setText(message);
    }

    private ToastUtils(Context context, String message, ToastDismiss toastDismiss) {
        this.toastDismiss = toastDismiss;
        container = ((Activity) context).findViewById(android.R.id.content);
        mView = View.inflate(context, R.layout.dialog_toast, null);
        mContainer = mView.findViewById(R.id.mbContainer);
        tvToast = mView.findViewById(R.id.tvToast);
        tvToast.setText(message);
    }

    public static ToastUtils makeText(Context context, String message) {
        ToastUtils mInstance = new ToastUtils(context, message);
        return mInstance;
    }

    public static ToastUtils makeText(Context context, String message, ToastDismiss toastDismiss) {
        ToastUtils mInstance = new ToastUtils(context, message, toastDismiss);
        return mInstance;
    }


    public interface ToastDismiss {
        void dismissToast();
    }

    public void show() {
        if (isShow) {
            // 若已经显示，则不再次显示
            return;
        }
        isShow = true;
        // 显示动画
        mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        // 消失动画
        mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
        mFadeOutAnimation.setDuration(ANIMATION_DURATION);
        mFadeOutAnimation
                .setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // 隐藏布局，不使用remove方法为防止多次创建多个布局
                        mContainer.setVisibility(View.GONE);
                        mContainer.removeView(mView);
                        // 消失动画后更改状态为 未显示
                        isShow = false;
                        if (toastDismiss != null) {
                            toastDismiss.dismissToast();
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
        container.addView(mView);
        mFadeInAnimation.setDuration(ANIMATION_DURATION);
        mContainer.startAnimation(mFadeInAnimation);
        mHandler.postDelayed(mHideRunnable, HIDE_DELAY);
    }

    private Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mContainer.startAnimation(mFadeOutAnimation);
        }
    };
}

