package com.mazhanzhu.myutils.fm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mazhanzhu.myutils.base.BaseFm_VB;
import com.mazhanzhu.myutils.databinding.MainFmtab2Binding;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/12 13:48
 * Desc   :
 */
public class Main_FmTab2 extends BaseFm_VB<MainFmtab2Binding> {
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @NotNull
    @Override
    public MainFmtab2Binding getViewBinding(@NotNull LayoutInflater layoutInflater, @Nullable ViewGroup container, boolean b) {
        return MainFmtab2Binding.inflate(layoutInflater, container, false);
    }

    @Override
    public void click(@Nullable View v) {

    }
}
