package com.mazhanzhu.myutils.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.mazhanzhu.myutils.R;
import com.mazhanzhu.myutils.adapter.Adapter_cons;
import com.mazhanzhu.myutils.base.BaseAc_VB;
import com.mazhanzhu.myutils.databinding.AcContactsBinding;
import com.mazhanzhu.utils.DividerItemDecoration_Ma;
import com.mazhanzhu.utils.MzzDeviceTool;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/20 13:28
 * Desc   : 联系人
 */
public class Ac_Contacts extends BaseAc_VB<AcContactsBinding> {
    private Handler handler = new Handler(Looper.myLooper()) {
    };
    private Adapter_cons madapter;

    @Override
    protected AcContactsBinding getViewBinding() {
        return AcContactsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        vb.cons.refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                handler.postDelayed(() -> {
                    refreshLayout.finishLoadMore();
                }, 1500);
            }

            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                handler.postDelayed(() -> {
                    refreshLayout.finishRefresh();
                }, 1500);
            }
        });
        getDate();
    }

    private void getDate() {
        vb.cons.recycler.addItemDecoration(new DividerItemDecoration_Ma(context, DividerItemDecoration_Ma.HORIZONTAL_LIST));
        vb.cons.recycler.setLayoutManager(new LinearLayoutManager(context));
        madapter = new Adapter_cons(R.layout.item_cons, MzzDeviceTool.getAllContactInfo(context));
        madapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                String phone = madapter.getData().get(position).get("phone");
                MzzDeviceTool.callPhone(context, phone);
            }
        });
        vb.cons.recycler.setAdapter(madapter);
    }

    @Override
    protected void onclick(View v) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }
}
