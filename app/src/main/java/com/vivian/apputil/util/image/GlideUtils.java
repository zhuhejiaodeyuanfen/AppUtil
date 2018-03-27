package com.vivian.apputil.util.image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by vivianWQ on 2018/3/26
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public abstract class GlideUtils {


    //默认加载
    public static void loadImageView(Context mContext, String path, ImageView mImageView) {
        Glide.with(mContext).load(path).into(mImageView);
    }

}