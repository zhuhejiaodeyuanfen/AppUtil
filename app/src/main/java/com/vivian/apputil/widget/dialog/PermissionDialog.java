package com.vivian.apputil.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.vivian.apputil.R;

/**
 * Created by vivianWQ on 2018/3/27
 * Mail: wangqi_vivian@sina.com
 * desc:
 * Version: 1.0
 */
public class PermissionDialog extends Dialog {

    public static final int DIALOG_NO_CANCEL = 2008;
    public static final int DIALOG_NORAML = 2009;

    public static boolean isShow;
    /*上下文*/

    private Context context;
    private int dialogStyle;
    private TextView tvContent;
    private TextView tvSure, tvCancel;
    private OnDialogClick onDialogClick;
    private View viewVerticalLine;

    public PermissionDialog(Context context) {
        super(context);
        this.context = context;
        initView();
    }


    private void initView() {
        //给整个window设置成透明色
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //设置dialog布局文件
        setCancelable(false);
        setContentView(R.layout.layout_app_dialog);
        /*找到空间*/
        tvContent = findViewById(R.id.tvContent);
        tvSure = findViewById(R.id.tvSure);
        tvCancel = findViewById(R.id.tvCancel);
        viewVerticalLine=findViewById(R.id.viewVerticalLine);
        //取消的点击事件
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDialogClick != null) {
                    //onClick是一个接口，调用接口中的抽象方法cancel，cancel中的具体实现在调用这个dialog的地方
                    onDialogClick.onCancel();
                }
                dismiss();
            }
        });
        //确定的点击事件
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDialogClick != null) {
                    onDialogClick.onSure();
                }
                dismiss();
            }
        });
        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {


            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        isShow = false;
    }

    /*设置dialog内容文本*/
    public PermissionDialog setDialogContent(String content) {
        tvContent.setText(content);
        return this;
    }

    /*设置取消String内容*/
    public PermissionDialog setCancel(String text) {
        tvCancel.setText(text);
        return this;
    }


    /*设置确定String内容*/
    public PermissionDialog setSure(String text) {
        tvSure.setText(text);
        return this;
    }


    /**
     * 设置样式
     * @param style
     * @return
     */
    public PermissionDialog setStyle(int style) {
        if (style == DIALOG_NO_CANCEL) {
            viewVerticalLine.setVisibility(View.GONE);
            tvCancel.setVisibility(View.GONE);
        } else {
            viewVerticalLine.setVisibility(View.VISIBLE);
            tvCancel.setVisibility(View.VISIBLE);
        }
        return this;
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
        if (!isShow) {
            isShow = true;
            super.show();
        }
    }


    //点击事件接口，具体实现在子类中
    public interface OnDialogClick {
        void onCancel();

        void onSure();
    }

}
