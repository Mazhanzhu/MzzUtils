package com.mazhanzhu.myutils.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mazhanzhu.utils.MzzActivityTool;

import androidx.fragment.app.FragmentActivity;
import androidx.viewbinding.ViewBinding;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/11 13:52
 * Desc   : Base
 */
public abstract class BaseAc_VB<Mzz extends ViewBinding> extends FragmentActivity implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * 是否沉浸状态栏【默认不设置】
     **/
    private boolean isSetStatusBar = false;
    /**
     * 是否允许全屏【默认全屏】
     **/
    private boolean mAllowFullScreen = true;
    /**
     * [true—允许旋转屏幕；false—禁止旋转屏幕]
     **/
    private boolean isAllowScreenRoate = false;
    protected Activity context;
    protected Mzz vb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParms(savedInstanceState);
        if (mAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        if (isSetStatusBar) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 透明状态栏
                getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                // 透明导航栏
                getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        }
        if (!isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        context = this;
        vb = getViewBinding();
        setContentView(vb.getRoot());
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);//禁止手机进行截图操作
//        OpenGray(true);//打开灰度模式
        initView(savedInstanceState);
        MzzActivityTool.addActivity(this);
    }

    /**
     * [初始化参数]
     * 解析bundle内容或者设置是否旋转，沉浸，全屏
     */
    public void initParms(Bundle parms) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MzzActivityTool.finishActivity(this);
        vb=null;
    }

    protected abstract Mzz getViewBinding();

    @Override
    public void onClick(View v) {
        onclick(v);
    }

    private void OpenGray(boolean isOpen) {
        if (isOpen) {
            Paint paint = new Paint();
            ColorMatrix cm = new ColorMatrix();
            //设置 0 为灰度模式
            cm.setSaturation(0);
            paint.setColorFilter(new ColorMatrixColorFilter(cm));
            getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);
        } else {
            getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
    }

    /**
     * 是否允许屏幕旋转
     * <p>
     * 默认false禁止旋转屏幕
     *
     * @param isAllowScreenRoate true—允许旋转屏幕；false—禁止旋转屏幕
     */
    protected void setScreenRoate(boolean isAllowScreenRoate) {
        this.isAllowScreenRoate = isAllowScreenRoate;
    }

    /**
     * 是否允许全屏
     * <p>
     * 默认true—全屏
     *
     * @param allowFullScreen true—全屏；false—非全屏
     */
    protected void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * 是否设置沉浸状态栏]
     *
     * @param isSetStatusBar 默认false不设置
     */
    protected void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }

    /**
     * 初始化数据
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化点击事件
     */
    protected abstract void onclick(View v);

    protected void startActivity(Class<?> clz) {
        startActivity(new Intent(context, clz));
    }
}
