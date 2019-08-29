package com.zhangyp.develop.HappyLittleBook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zyp on 2019/8/27 0027.
 * class note:
 */

@Entity
public class IncomeLevelTwoCate {
    @Id(autoincrement = true)
    Long id;

    Long oneCateId;
    String cateName;
    @Generated(hash = 1631020527)
    public IncomeLevelTwoCate(Long id, Long oneCateId, String cateName) {
        this.id = id;
        this.oneCateId = oneCateId;
        this.cateName = cateName;
    }
    @Generated(hash = 1824654765)
    public IncomeLevelTwoCate() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getOneCateId() {
        return this.oneCateId;
    }
    public void setOneCateId(Long oneCateId) {
        this.oneCateId = oneCateId;
    }
    public String getCateName() {
        return this.cateName;
    }
    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
