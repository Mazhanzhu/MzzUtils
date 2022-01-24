package com.mazhanzhu.myutils.base;

import com.mazhanzhu.myutils.bean.Bean_DayTxt;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/21 14:12
 * Desc   : 每日一句功能-一言API调用
 */
public interface Impl_DayTxt {
    /**
     * 车辆核查历史
     */
    @GET
    Call<Bean_DayTxt> getDayTxt(@Url String url, @QueryMap Map<String, String> map);
}
