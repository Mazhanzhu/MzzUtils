package com.mazhanzhu.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/13 9:46
 * Desc   : JsonUtils解析工具类
 */
public class MzzJsonUtils {
    public static final String TAG = "MzzJsonUtils";

    /**
     * 将Json数据解析成相应的集合形式
     */
    public static <T> List<T> jsonToList(String json, Class<T> t) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonarray = parser.parse(json).getAsJsonArray();
            for (JsonElement element : jsonarray) {
                list.add(gson.fromJson(element, t));
            }
        } catch (Exception e) {
            Log_Ma.e(TAG, "解析异常" + t.getSimpleName() + "/" + e.toString());
        }
        return list;
    }

    /**
     * 将Json数据解析成相应的映射对象
     */
    public static <T> T jsonToBean(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result;
        try {
            result = gson.fromJson(jsonData, type);
        } catch (Exception e) {
            Log_Ma.e(TAG, "解析异常" + type.getSimpleName() + "/" + e.toString());
            result = null;
        }
        return result;
    }
}
