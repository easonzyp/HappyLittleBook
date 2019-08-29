package com.zhangyp.develop.HappyLittleBook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zyp on 2019/8/29 0029.
 * class note:
 */

@Entity
public class AccountBookInfo {
    @Id(autoincrement = true)
    Long id;

    double money;
    String timeStr;
    String noteStr;
    String cateStr;
    String walletType;
    int bookType;
    @Generated(hash = 492736351)
    public AccountBookInfo(Long id, double money, String timeStr, String noteStr,
            String cateStr, String walletType, int bookType) {
        this.id = id;
        this.money = money;
        this.timeStr = timeStr;
        this.noteStr = noteStr;
        this.cateStr = cateStr;
        this.walletType = walletType;
        this.bookType = bookType;
    }
    @Generated(hash = 634079372)
    public AccountBookInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getMoney() {
        return this.money;
    }
    public void setMoney(double money) {
        this.money = money;
    }
    public String getTimeStr() {
        return this.timeStr;
    }
    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
    public String getNoteStr() {
        return this.noteStr;
    }
    public void setNoteStr(String noteStr) {
        this.noteStr = noteStr;
    }
    public String getCateStr() {
        return this.cateStr;
    }
    public void setCateStr(String cateStr) {
        this.cateStr = cateStr;
    }
    public String getWalletType() {
        return this.walletType;
    }
    public void setWalletType(String walletType) {
        this.walletType = walletType;
    }
    public int getBookType() {
        return this.bookType;
    }
    public void setBookType(int bookType) {
        this.bookType = bookType;
    }

}
