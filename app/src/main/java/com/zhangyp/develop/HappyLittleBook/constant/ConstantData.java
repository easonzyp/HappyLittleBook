package com.zhangyp.develop.HappyLittleBook.constant;

import android.os.Environment;

public class ConstantData {

    public final static String NAME = "/yuguo/";
    public final static String PATH = Environment.getExternalStorageDirectory().toString() + NAME;
    public final static String DOWN_LOAD_PATH = Environment.getExternalStorageDirectory().toString() + "/yuguo/down/";

    //正式
    public final static String HOST = "http://v.juhe.cn/toutiao/index";
    public static final String ALIOSS_PROJECT_APP_ID = "1000000800374099";
    public static final String ALIOSS_PROJECT_APP_SECRET = "1000001906571578";
    public static final String SHARE_URL = "https://www.pbdsh.com/ygweb/#/register?parentUserId=";
    public static final String NET_WORK_PAGE = "https://www.pbdsh.com/";

    //测试
//    public final static String HOST = "https://testhgs.pcc58.com/huaguoshan_web/";
//    public static final String ALIOSS_PROJECT_APP_ID = "1000001687487045";
//    public static final String ALIOSS_PROJECT_APP_SECRET = "1000001168437517";
//    public static final String SHARE_URL = "https://testhgs .pcc58.com/ygweb/?from=singlemessage&isappinstalled=0#/register?parentUserId=";
//    public static final String VIP_EQUITY_URL=   "http://bshop.pbphkj.com/ygweb/vip_introduce.html";
//
//    public static final String NET_WORK_PAGE = "https://testhgs.pcc58.com/";

}
