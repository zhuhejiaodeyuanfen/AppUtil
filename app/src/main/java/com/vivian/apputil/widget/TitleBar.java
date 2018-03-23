package com.vivian.apputil.widget;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.vivian.apputil.R;
import com.vivian.apputil.util.AppUtil;
import com.vivian.apputil.view.BaseActivity;

/**
 * Created by vivianWQ on 2018/3/22
 * Mail: wangqi_vivian@sina.com
 * desc:  自定义标题控件
 * 适配大部分app的标题界面
 * Version: 1.0
 */
public class TitleBar extends FrameLayout {

    /**
     * 自定义标题栏的点击事件
     */
    public class IButtonClick {
        public boolean left(View view) {
            return false;
        }

        public void right(View view) {

        }
    }

    private TextView tvTitle, tvRight, tvLeft;
    private ImageView ivLeft, ivRight;
    private View leftClickView, rightClickView;
    private int titleHeight = 44;//自定义标题栏高度
    private View titleBarView;
    private IButtonClick iButtonClick;

    public TitleBar(@NonNull Context context) {
        super(context);
        initView();
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * 初始化一些初始场景
     */
    private void initView() {
        titleBarView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.widget_title_bar_layout, null);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, AppUtil.dip2px(getContext(), titleHeight));
        addView(titleBarView, layoutParams);
        leftClickView = findViewById(R.id.leftClickView);
        rightClickView = findViewById(R.id.rightClickView);
        tvLeft = findViewById(R.id.tvLeft);
        tvRight = findViewById(R.id.tvRight);
        ivLeft = findViewById(R.id.ivLeft);
        ivRight = findViewById(R.id.ivRight);
    }

    /**
     * 添加左按钮点击事件,若无处理,默认关闭当前页面
     *
     * @param view
     */
    private void addLeftClick(View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null == iButtonClick || !iButtonClick.left(view)) {
                    BaseActivity.finishActivity(getContext());
                }
            }
        });
    }

    /**
     * 自定义右按钮点击事件
     *
     * @param view
     */
    private void addRightClick(View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != iButtonClick) {
                    iButtonClick.right(view);
                }
            }
        });
    }

    /**
     * 设置标题文本
     *
     * @param text
     */
    public void setTitleText(String text) {
        if (null == tvTitle) {
            return;
        }
        tvTitle.setText(text);
    }

    public void setiButtonClick(IButtonClick iButtonClick) {
        this.iButtonClick = iButtonClick;
    }
}
