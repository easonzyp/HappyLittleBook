package com.zhangyp.develop.HappyLittleBook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by zyp on 2019/8/27 0027.
 * class note:
 */

@Entity
public class ExpendLevelOneCate {
    @Id(autoincrement = true)
    Long id;

    @Unique
    String cateName;

    @Generated(hash = 1204412956)
    public ExpendLevelOneCate(Long id, String cateName) {
        this.id = id;
        this.cateName = cateName;
    }

    @Generated(hash = 922049486)
    public ExpendLevelOneCate() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCateName() {
        return this.cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}
