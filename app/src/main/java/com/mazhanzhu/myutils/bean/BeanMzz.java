package com.mazhanzhu.myutils.bean;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/13 9:53
 * Desc   :
 */
public class BeanMzz {
    private String name;
    private int id;
    private int img;
    private String content;
    private boolean isShowRed = false;//是否显示红点，true显示 false隐藏

    public BeanMzz() {
    }

    public BeanMzz(int id, String name, int img, String content, boolean isShowRed) {
        this.name = name;
        this.id = id;
        this.img = img;
        this.content = content;
        this.isShowRed = isShowRed;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShowRed() {
        return isShowRed;
    }

    public void setShowRed(boolean showRed) {
        isShowRed = showRed;
    }
}
