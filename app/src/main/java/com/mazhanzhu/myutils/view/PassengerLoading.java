package com.mazhanzhu.myutils.view;

import android.content.Context;

import com.mazhanzhu.myutils.R;
import com.mazhanzhu.myutils.base.BaseDialog;


public class PassengerLoading extends BaseDialog {
    public PassengerLoading(Context context) {
        super(context);
    }

    @Override
    public int bindLayout() {
        return R.layout.loading_dialog;
    }

    @Override
    public void initView() {

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
