package com.mazhanzhu.myutils.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.mazhanzhu.myutils.R;
import com.mazhanzhu.myutils.base.BaseAc_VB;
import com.mazhanzhu.myutils.databinding.AcGpsBinding;
import com.mazhanzhu.utils.MzzToastUtils;
import com.mazhanzhu.utils.bean.Bean_WLData;
import com.mazhanzhu.utils.gps.MzzGPSCheckUtils;
import com.mazhanzhu.utils.gps.MzzMapDistance;

import java.util.ArrayList;

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
        vb.gpsCs.setOnClickListener(this);
    }

    @Override
    protected void onclick(View v) {
        switch (v.getId()) {
            case R.id.gps_js:
                js();
                break;
            case R.id.gps_cs:
                cs();
                break;
        }
    }

    private void cs() {
        ArrayList<Bean_WLData> list = new ArrayList<>();
        list.add(new Bean_WLData(124.871758, 44.070251));
        list.add(new Bean_WLData(124.855793, 44.032621));
        list.add(new Bean_WLData(124.868839, 44.017316));
        list.add(new Bean_WLData(124.865921, 43.99682));
        list.add(new Bean_WLData(124.958618, 43.995585));
        list.add(new Bean_WLData(124.990032, 44.027314));
        list.add(new Bean_WLData(124.982994, 44.065564));
        MzzGPSCheckUtils.LatLon wl = MzzGPSCheckUtils.getStart_End_WL(44.032992, 124.918793, 44.007563, 124.819744, list);
        vb.gpsCs.setText(wl == null ? "无相交" : "有相交 坐标：" + wl.getLon() + "/" + wl.getLat());
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
            double shortDistance = MzzMapDistance.getInstance().getShortDistance(
                    Double.parseDouble(splita[0]),
                    Double.parseDouble(splita[1]),
                    Double.parseDouble(splitb[0]),
                    Double.parseDouble(splitb[1]));
            vb.gpsJs.setText("点击计算  " + shortDistance + "米");
        } catch (Exception e) {
            MzzToastUtils.showToast(context, "信息有误，重新输入");
        }
    }
}
