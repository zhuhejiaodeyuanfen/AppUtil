package com.vivian.apputil.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by vivianWQ on 2018/3/22
 * Mail: wangqi_vivian@sina.com
 * desc:Activity启动帮助类
 * Version: 1.0
 */
public class ActivityLauncher {

    public static void finishActivity(Context context) {
        ((Activity) context).finish();
    }


    public static void activityLauncher(Context context, Class<?> targetActivity) {
        Intent intent = new Intent(context, targetActivity);
        context.startActivity(intent);
    }

    public static void launcherExtras(Context context, Class<?> targetActivity, Bundle extras) {
        Intent intent = new Intent(context, targetActivity);
        if (null != extras) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void launcherResult(int requestCode, Context context, Class<? extends Activity> targetActivity, Bundle extras) {
        Intent intent = new Intent(context, targetActivity);
        if (null != extras) {
            intent.putExtras(extras);
        }
        ((Activity) context).startActivityForResult(intent, requestCode);
    }
}
