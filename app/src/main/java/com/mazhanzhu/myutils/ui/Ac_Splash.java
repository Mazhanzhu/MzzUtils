package com.mazhanzhu.myutils.ui;

import android.os.Bundle;
import android.view.View;

import com.mazhanzhu.myutils.Activity_Main;
import com.mazhanzhu.myutils.R;
import com.mazhanzhu.myutils.base.BaseAc_VB;
import com.mazhanzhu.myutils.databinding.AcSplashBinding;
import com.mazhanzhu.utils.Log_Ma;
import com.mazhanzhu.utils.view.CountDownProgressView;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/11 15:47
 * Desc   : 启动页
 */
public class Ac_Splash extends BaseAc_VB<AcSplashBinding> {
    @Override
    protected AcSplashBinding getViewBinding() {
        return AcSplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        vb.spCountview.setTimeMillis(1000 * 5);
        vb.spCountview.setProgressListener(new CountDownProgressView.OnProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log_Ma.e(TAG, "------" + progress);
                if (progress == 0) {
                    startActivity(Activity_Main.class);
                }
            }
        });
        vb.spCountview.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        vb.spCountview.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        vb.spCountview.stop();
    }

    @Override
    protected void onclick(View v) {
        switch (v.getId()) {
            case R.id.sp_countview:
                vb.spCountview.stop();
                startActivity(Activity_Main.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
