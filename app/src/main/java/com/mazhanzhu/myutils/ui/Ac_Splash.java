package com.mazhanzhu.myutils.ui;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.mazhanzhu.myutils.Activity_Main;
import com.mazhanzhu.myutils.R;
import com.mazhanzhu.myutils.base.BaseAc_VB;
import com.mazhanzhu.myutils.databinding.AcSplashBinding;
import com.mazhanzhu.utils.Log_Ma;
import com.mazhanzhu.utils.MzzAppUtils;
import com.mazhanzhu.utils.MzzDeviceTool;
import com.mazhanzhu.utils.MzzIntentTool;
import com.mazhanzhu.utils.MzzToastUtils;
import com.mazhanzhu.utils.view.CountDownProgressView;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/11 15:47
 * Desc   : 启动页
 */
public class Ac_Splash extends BaseAc_VB<AcSplashBinding> {
    private String[] permiss = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
    };
    private boolean isHavaPerMiss;

    @Override
    protected AcSplashBinding getViewBinding() {
        return AcSplashBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (MzzAppUtils.checkPermission(context, permiss)) {
            setDate();
        } else {
            ActivityCompat.requestPermissions(context, permiss, 16);
        }
    }

    private void setDate() {
        isHavaPerMiss = true;
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
        MzzDeviceTool.queryContactsInfo(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isHavaPerMiss)
            vb.spCountview.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isHavaPerMiss)
            vb.spCountview.stop();
    }

    @Override
    protected void onclick(View v) {
        switch (v.getId()) {
            case R.id.sp_countview:
                if (isHavaPerMiss) {
                    vb.spCountview.stop();
                    startActivity(Activity_Main.class);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 16) {
            if (MzzAppUtils.checkPermission(context, permissions)) {
                setDate();
            } else {
                MzzToastUtils.showToast(context, "请授予相应权限");
                context.startActivity(MzzIntentTool.getAppDetailsSettingsIntent(context));
            }
        }
    }
}
