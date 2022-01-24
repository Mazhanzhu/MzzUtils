package com.mazhanzhu.myutils.base;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.mazhanzhu.myutils.R;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2020/9/6 11:59
 * Desc   : 自定义Dialog
 */
public abstract class BaseDialog extends Dialog {
    protected View rootView;
    protected DisplayMetrics displayMetrics;
    protected float maxHeight;//最大高度
    protected float scaleWidth;//  宽度比例
    protected float scaleHeight;//高度比例
    protected Context context;
    protected int gravity = Gravity.CENTER;//默认居中

    public BaseDialog(Context context) {
        super(context, R.style.base_dialog_style);
        this.context = context;
        initDialog();
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initDialog();
    }

    private void initDialog() {
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        displayMetrics = context.getResources().getDisplayMetrics();
        maxHeight = displayMetrics.heightPixels - getHeight(context);
        rootView = View.inflate(context, bindLayout(), null);
        setContentView(rootView);
        setCancelable(true);//dialog弹出后会点击屏幕或物理返回键，dialog不消失
        setCanceledOnTouchOutside(false);// dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
        initView();
    }

    /**
     * 设置dialog全屏
     */
    public void setDialogFullWindow() {
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        int width;
        if (scaleWidth == 0) {
            width = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (scaleWidth == 1) {
            width = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            width = (int) (displayMetrics.widthPixels * scaleWidth);
        }
        int height;
        if (scaleHeight == 0) {
            height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else if (scaleHeight == 1) {
            height = ViewGroup.LayoutParams.MATCH_PARENT;
        } else {
            height = (int) (maxHeight * scaleHeight);
        }
        rootView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
    }

    public abstract int bindLayout();

    public abstract void initView();

    //设置 对话框 高度比 (0, 1]
    public void setScaleHeight(float scaleHeight) {
        this.scaleHeight = scaleHeight;
    }

    //设置 对话框 宽度比 (0, 1]
    public void setScaleWidth(float scaleWidth) {
        this.scaleWidth = scaleWidth;

    }

    //设置对话框的显示位置
    public void setGravity(int gravity) {
        this.gravity = gravity;
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(gravity);
    }

    //设置对话框的显示位置，以及Y轴的向下偏移量（单位 dp）
    public void setGravity(int gravity, int yDP) {
        this.gravity = gravity;
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = (int) (yDP * context.getResources().getDisplayMetrics().density);
        dialogWindow.setGravity(gravity);

    }

    //销毁
    public void destroy() {
        super.dismiss();
        if (context != null) {
            context = null;
        }
        if (rootView != null) {
            rootView.destroyDrawingCache();
            rootView = null;
        }
        if (displayMetrics != null) {
            displayMetrics = null;
        }
    }

    public static int getHeight(Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        if (isFlymeOs4x()) {
            return 2 * statusBarHeight;
        }

        return statusBarHeight;
    }

    public static boolean isFlymeOs4x() {
        String sysVersion = android.os.Build.VERSION.RELEASE;
        if ("4.4.4".equals(sysVersion)) {
            String sysIncrement = android.os.Build.VERSION.INCREMENTAL;
            String displayId = android.os.Build.DISPLAY;
            if (!TextUtils.isEmpty(sysIncrement)) {
                return sysIncrement.contains("Flyme_OS_4");
            } else {
                return displayId.contains("Flyme OS 4");
            }
        }
        return false;
    }
}
