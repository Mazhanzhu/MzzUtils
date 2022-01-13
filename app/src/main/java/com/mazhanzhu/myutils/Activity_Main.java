package com.mazhanzhu.myutils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Gravity;
import android.view.View;

import com.mazhanzhu.myutils.adapter.MainAdapter;
import com.mazhanzhu.myutils.base.BaseAc_VB;
import com.mazhanzhu.myutils.bean.BeanMzz;
import com.mazhanzhu.myutils.databinding.ActivityMainBinding;
import com.mazhanzhu.myutils.fm.Main_FmTab1;
import com.mazhanzhu.myutils.fm.Main_FmTab2;
import com.mazhanzhu.myutils.fm.Main_FmTab3;
import com.mazhanzhu.utils.Log_Ma;
import com.mazhanzhu.utils.MzzGlide;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/11 13:46
 * Desc   : http://91porn.com/
 */
public class Activity_Main extends BaseAc_VB<ActivityMainBinding> {
    private static final String TAB01 = "tab01", TAB02 = "tab02", TAB03 = "tab03", TAB04 = "tab04";
    private Main_FmTab1 fmtab1;
    private Main_FmTab2 fmtab2;
    private Main_FmTab3 fmtab3;
    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        vb.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log_Ma.e(TAG);
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        initDrawer();
        initFram(1);
        vb.drawerHeader.mainT1.setOnClickListener(this);
        vb.drawerHeader.mainT2.setOnClickListener(this);
        vb.drawerHeader.mainT3.setOnClickListener(this);
        vb.drawerHeader.drawerimg.setOnClickListener(this);
        MzzGlide.toView(vb.drawerT1.userImg, R.mipmap.userimg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    private void initFram(int position) {
        FragmentManager fm = getSupportFragmentManager();
        fmtab1 = (Main_FmTab1) fm.findFragmentByTag(TAB01);
        fmtab2 = (Main_FmTab2) fm.findFragmentByTag(TAB02);
        fmtab3 = (Main_FmTab3) fm.findFragmentByTag(TAB03);
        FragmentTransaction transaction = fm.beginTransaction();
        if (fmtab1 != null) {
            transaction.hide(fmtab1);
        }
        if (fmtab2 != null) {
            transaction.hide(fmtab2);
        }
        if (fmtab3 != null) {
            transaction.hide(fmtab3);
        }
        vb.drawerHeader.mainT1.setSelected(false);
        vb.drawerHeader.mainT2.setSelected(false);
        vb.drawerHeader.mainT3.setSelected(false);
        switch (position) {
            case 1:
                vb.drawerHeader.mainT1.setSelected(true);
                if (fmtab1 == null) {
                    fmtab1 = new Main_FmTab1();
                    transaction.add(R.id.main_fragment, fmtab1, TAB01);
                } else {
                    transaction.show(fmtab1);
                }
                break;
            case 2:
                vb.drawerHeader.mainT2.setSelected(true);
                if (fmtab2 == null) {
                    fmtab2 = new Main_FmTab2();
                    transaction.add(R.id.main_fragment, fmtab2, TAB02);
                } else {
                    transaction.show(fmtab2);
                }
                break;
            case 3:
                vb.drawerHeader.mainT3.setSelected(true);
                if (fmtab3 == null) {
                    fmtab3 = new Main_FmTab3();
                    transaction.add(R.id.main_fragment, fmtab3, TAB03);
                } else {
                    transaction.show(fmtab3);
                }
                break;
        }
        transaction.commit();
    }

    private void initDrawer() {
        vb.mainRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadMore();
                    }
                }, 1500);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                    }
                }, 1500);
            }
        });
        ArrayList<BeanMzz> list = new ArrayList<>();
        list.add(new BeanMzz(1, "GPS相关", R.mipmap.gps, "", false));
        MainAdapter mAdapter = new MainAdapter(R.layout.main_item, list, context);
        vb.mainRv.setLayoutManager(new LinearLayoutManager(context));
        vb.mainRv.setAdapter(mAdapter);
    }

    @Override
    protected void onclick(View v) {
        switch (v.getId()) {
            case R.id.main_t1:
                initFram(1);
                break;
            case R.id.main_t2:
                initFram(2);
                break;
            case R.id.main_t3:
                initFram(3);
                break;
            case R.id.drawerimg:
                if (vb.drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    vb.drawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    vb.drawerLayout.openDrawer(Gravity.LEFT);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent backHome = new Intent(Intent.ACTION_MAIN);
        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        backHome.addCategory(Intent.CATEGORY_HOME);
        startActivity(backHome);
    }
}
