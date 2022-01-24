package com.mazhanzhu.myutils.ui;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.mazhanzhu.myutils.Activity_Main;
import com.mazhanzhu.myutils.R;
import com.mazhanzhu.myutils.base.BaseAc_VB;
import com.mazhanzhu.myutils.base.Http_Net;
import com.mazhanzhu.myutils.bean.Bean_DayTxt;
import com.mazhanzhu.myutils.databinding.AcSplashBinding;
import com.mazhanzhu.utils.Log_Ma;
import com.mazhanzhu.utils.MzzAppUtils;
import com.mazhanzhu.utils.MzzDeviceTool;
import com.mazhanzhu.utils.MzzIntentTool;
import com.mazhanzhu.utils.MzzToastUtils;
import com.mazhanzhu.utils.view.CountDownProgressView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        vb.spCountview.setOnClickListener(this);
        if (MzzAppUtils.checkPermission(context, permiss)) {
            setDate();
        } else {
            ActivityCompat.requestPermissions(context, permiss, 16);
        }
    }

    private void setDate() {
        HashMap<String, String> map = new HashMap<>();
        map.put("encode", "json");
        map.put("min_length", "10");
        map.put("max_length", "30");
        Http_Net.getInstance().getDayTxt().getDayTxt("https://v1.hitokoto.cn", map).enqueue(new Callback<Bean_DayTxt>() {
            @Override
            public void onResponse(Call<Bean_DayTxt> call, Response<Bean_DayTxt> response) {
                String ss = response.body().getHitokoto() + "\n出处：" +
                        response.body().getFrom() + " 作者：" + response.body().getFrom_who();
                vb.daytxt.setText(ss);
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
                vb.spCountview.start();
                MzzDeviceTool.queryContactsInfo(context);
            }

            @Override
            public void onFailure(Call<Bean_DayTxt> call, Throwable t) {
                MzzToastUtils.showToast(context, "失败");
            }
        });
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
