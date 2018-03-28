package com.vivian.apputil.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vivian.apputil.R;
import com.vivian.apputil.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivianWQ on 2018/3/27
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class FromBottomDialog extends Dialog {


    private Context context;
    private OnDialogClick onDialogClick;
    private LinearLayout llContent;
    public List<Option> options= new ArrayList<>();

    public FromBottomDialog(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    private void initView() {
        //给整个window设置成透明色
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //设置dialog布局文件
        setCancelable(false);
        Display display = this.getWindow().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width, height);
        View viewDialog = LayoutInflater.from(context).inflate(R.layout.layout_bottom_dialog, null);
        setContentView(viewDialog, layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);

        llContent = findViewById(R.id.llContent);
        //取消的点击事件

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {


            }
        });


    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    /**
     * 添加点击事件
     **/
    public void addOnClick(OnDialogClick onDialogClick) {
        this.onDialogClick = onDialogClick;
    }

    //显示
    @Override
    public void show() {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);

        for (int i = 0; i < options.size(); i++) {

            final Option option = options.get(i);
            final TextView optionText = new TextView(context);
            optionText.setText(option.getName());

            optionText.setPadding(0, AppUtil.dip2px(context, 13), 0, AppUtil.dip2px(context, 13));
            optionText.setGravity(Gravity.CENTER);
            optionText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    option.getListener().onOptionClick();
                }
            });
            llContent.addView(optionText);


            //添加条目间的分割线
            if (i != options.size() - 1) {
                View divider = new View(context);
                divider.setBackgroundColor(ContextCompat.getColor(context, R.color.black_444444));
                llContent.addView(divider, params);
            }

        }

        super.show();
    }

    public FromBottomDialog addOption(String option, int color, OnOptionClickListener listener) {
        options.add(new Option(option, color, listener));
        return this;
    }

    public FromBottomDialog addOption(String option, OnOptionClickListener listener) {
        options.add(new Option(option, listener));
        return this;
    }

    //点击事件接口，具体实现在子类中
    public interface OnDialogClick {
        void onClick();

        void onSure();
    }

    public interface OnOptionClickListener {
        void onOptionClick();
    }

    class Option {
        private String name;
        private int color;
        private OnOptionClickListener listener;

        public Option() {
        }

        public Option(String name, int color, OnOptionClickListener listener) {
            this.name = name;
            this.color = color;
            this.listener = listener;
        }

        public Option(String name, OnOptionClickListener listener) {
            this.name = name;
            this.listener = listener;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public OnOptionClickListener getListener() {
            return listener;
        }

        public void setListener(OnOptionClickListener listener) {
            this.listener = listener;
        }
    }

}
