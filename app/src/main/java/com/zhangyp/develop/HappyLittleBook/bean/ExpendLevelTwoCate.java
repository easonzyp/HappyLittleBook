package com.zhangyp.develop.HappyLittleBook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zyp on 2019/8/27 0027.
 * class note:
 */

@Entity
public class ExpendLevelTwoCate {
    @Id(autoincrement = true)
    Long id;

    Long oneCateId;
    String cateName;
    @Generated(hash = 654179949)
    public ExpendLevelTwoCate(Long id, Long oneCateId, String cateName) {
        this.id = id;
        this.oneCateId = oneCateId;
        this.cateName = cateName;
    }
    @Generated(hash = 1859201203)
    public ExpendLevelTwoCate() {
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
