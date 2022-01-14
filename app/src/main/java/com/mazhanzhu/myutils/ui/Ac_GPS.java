package com.mazhanzhu.myutils.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.mazhanzhu.myutils.R;
import com.mazhanzhu.myutils.base.BaseAc_VB;
import com.mazhanzhu.myutils.databinding.AcGpsBinding;
import com.mazhanzhu.utils.MzzToastUtils;
import com.mazhanzhu.utils.gps.MzzDistanceUtils;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/13 11:31
 * Desc   : GPS 555555
 */
public class Ac_GPS extends BaseAc_VB<AcGpsBinding> {
    @Override
    protected AcGpsBinding getViewBinding() {
        return AcGpsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        vb.gpsJs.setOnClickListener(this);
    }

    @Override
    protected void onclick(View v) {
        switch (v.getId()) {
            case R.id.gps_js:
                js();
                break;
        }
    }

    private void js() {
        String a = vb.gpsA.getText().toString().trim();
        String b = vb.gpsB.getText().toString().trim();
        if (TextUtils.isEmpty(a) || TextUtils.isEmpty(b)) {
            return;
        }
        String[] splita = a.split(",");
        String[] splitb = b.split(",");
        if (splita.length < 1 || splitb.length < 1) {
            return;
        }
        try {
            double shortDistance = MzzDistanceUtils.getInstance().getShortDistance(
                    Double.parseDouble(splita[0]),
                    Double.parseDouble(splita[1]),
                    Double.parseDouble(splitb[0]),
                    Double.parseDouble(splitb[1]));
            vb.gpsJs.setText("点击计算  " + shortDistance + "米");
        } catch (Exception e) {
            MzzToastUtils.showToast(context,"信息有误，重新输入");
        }
    }
}
