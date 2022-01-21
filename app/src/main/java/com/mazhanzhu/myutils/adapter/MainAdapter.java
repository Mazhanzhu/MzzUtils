package com.mazhanzhu.myutils.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mazhanzhu.myutils.R;
import com.mazhanzhu.myutils.bean.BeanMzz;
import com.mazhanzhu.myutils.ui.Ac_Contacts;
import com.mazhanzhu.myutils.ui.Ac_GPS;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/13 10:06
 * Desc   :
 */
public class MainAdapter extends BaseQuickAdapter<BeanMzz, BaseViewHolder> {
    private Activity context;

    public MainAdapter(int layoutResId, ArrayList<BeanMzz> list, Activity context) {
        super(layoutResId, list);
        this.context = context;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BeanMzz item) {
        baseViewHolder.setText(R.id.mainitem_txt, item.getName())
                .setBackgroundResource(R.id.mainitem_img, item.getImg())
                .setVisible(R.id.mainitem_red, item.isShowRed());
        baseViewHolder.getView(R.id.mainitem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick(item.getId());
            }
        });
    }

    private void onclick(int id) {
        switch (id) {
            case 1:
                context.startActivity(new Intent(context, Ac_GPS.class));
                break;
            case 2:
                context.startActivity(new Intent(context, Ac_Contacts.class));
                break;
        }
    }
}
