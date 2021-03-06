package com.xue.yado.memorandum.entity;

import com.xue.yado.memorandum.utils.TimerUtil;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2018/11/7.
 */

public class Memoire extends DataSupport implements Serializable{

    private Integer tag;

    private String title;

    private String content;//内容

    private Date createDate;//创建时间

    private Date lastModifyDate;//最后修改时间

    private String type;// 类型 | 标签

    public Memoire() {
        this.tag = new Random().nextInt(10000);
    }

    public Memoire(String title, String content, Date createDate, Date lastModifyDate, String type) {
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.lastModifyDate = lastModifyDate;
        this.type = type;
        this.tag = new Random().nextInt(10000);
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateDate() {
        return TimerUtil.getYear_Month_Day(createDate);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getLastModifyDate() {
        return TimerUtil.getYear_Month_Day(lastModifyDate);
    }

    public void setLastModifyDate(Date lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Memoire{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", lastModifyDate=" + lastModifyDate +
                ", type='" + type + '\'' +
                '}';
    }
}
