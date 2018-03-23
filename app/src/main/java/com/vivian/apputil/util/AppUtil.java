package com.vivian.apputil.util;

import android.content.Context;

/**
 * Created by vivianWQ on 2018/3/22
 * Mail: wangqi_vivian@sina.com
 * desc: 一些比较常用的工具类
 * Version: 1.0
 */
public class AppUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
