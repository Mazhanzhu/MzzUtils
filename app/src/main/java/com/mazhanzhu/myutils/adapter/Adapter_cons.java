package com.mazhanzhu.myutils.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.mazhanzhu.myutils.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/20 14:15
 * Desc   :
 */
public class Adapter_cons extends BaseQuickAdapter<HashMap<String, String>, BaseViewHolder> {

    public Adapter_cons(int item_cons, List<HashMap<String, String>> list) {
        super(item_cons, list);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HashMap<String, String> map) {
        String str = "姓名：" + map.get("name") + " 电话：" + map.get("phone");
        baseViewHolder.setText(R.id.name, str);
    }
}
