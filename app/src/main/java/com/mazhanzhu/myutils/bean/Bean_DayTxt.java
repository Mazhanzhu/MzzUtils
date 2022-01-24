package com.mazhanzhu.myutils.bean;

/**
 * Author : 马占柱
 * E-mail : mazhanzhu_3351@163.com
 * Time   : 2022/1/21 16:07
 * Desc   :
 */
public class Bean_DayTxt {

    private Integer id;
    private String uuid;
    private String hitokoto;
    private String type;
    private String from;
    private String from_who;
    private String creator;
    private Integer creator_uid;
    private Integer reviewer;
    private String commit_from;
    private String created_at;
    private Integer length;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid == null ? "" : uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getHitokoto() {
        return hitokoto == null ? "" : hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from == null ? "" : from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFrom_who() {
        return from_who == null ? "" : from_who;
    }

    public void setFrom_who(String from_who) {
        this.from_who = from_who;
    }

    public String getCreator() {
        return creator == null ? "" : creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getCreator_uid() {
        return creator_uid;
    }

    public void setCreator_uid(Integer creator_uid) {
        this.creator_uid = creator_uid;
    }

    public Integer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Integer reviewer) {
        this.reviewer = reviewer;
    }

    public String getCommit_from() {
        return commit_from == null ? "" : commit_from;
    }

    public void setCommit_from(String commit_from) {
        this.commit_from = commit_from;
    }

    public String getCreated_at() {
        return created_at == null ? "" : created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
