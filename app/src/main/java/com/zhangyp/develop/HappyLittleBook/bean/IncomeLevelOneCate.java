package com.zhangyp.develop.HappyLittleBook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zyp on 2019/8/27 0027.
 * class note:
 */

@Entity
public class IncomeLevelOneCate {
    @Id(autoincrement = true)
    Long id;

    String cateName;

    @Generated(hash = 1982455771)
    public IncomeLevelOneCate(Long id, String cateName) {
        this.id = id;
        this.cateName = cateName;
    }

    @Generated(hash = 1483015770)
    public IncomeLevelOneCate() {
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
