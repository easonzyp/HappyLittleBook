package com.zhangyp.develop.HappyLittleBook.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by zyp on 2019/8/29 0029.
 * class note:
 */

@Entity
public class WalletInfo implements Serializable{
    private static final long serialVersionUID = 244928664927257558L;
    @Id(autoincrement = true)
    Long id;

    double money;
    String walletName;
    String addTime;
    String walletNote;
    @Generated(hash = 1331447695)
    public WalletInfo(Long id, double money, String walletName, String addTime,
            String walletNote) {
        this.id = id;
        this.money = money;
        this.walletName = walletName;
        this.addTime = addTime;
        this.walletNote = walletNote;
    }
    @Generated(hash = 1144910350)
    public WalletInfo() {
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
    public String getWalletName() {
        return this.walletName;
    }
    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }
    public String getAddTime() {
        return this.addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getWalletNote() {
        return this.walletNote;
    }
    public void setWalletNote(String walletNote) {
        this.walletNote = walletNote;
    }
}
