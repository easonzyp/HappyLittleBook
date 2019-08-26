package com.zhangyp.develop.HappyLittleBook.util;

import android.widget.ImageView;

import com.zhangyp.develop.HappyLittleBook.R;

/**
 * Created by zyp on 2019/8/22 0022.
 * class note:
 */

public class WeatherUtil {
    /*"wid": "00",
        "weather": "晴"
    "wid": "01",
        "weather": "多云"
    "wid": "02",
        "weather": "阴"
    "wid": "03",
        "weather": "阵雨"
    "wid": "04",
        "weather": "雷阵雨"
    "wid": "05",
        "weather": "雷阵雨伴有冰雹"
    "wid": "06",
        "weather": "雨夹雪"
    "wid": "07",
        "weather": "小雨"
    "wid": "08",
        "weather": "中雨"
    "wid": "09",
        "weather": "大雨"
    "wid": "10",
        "weather": "暴雨"
    "wid": "11",
        "weather": "大暴雨"
    "wid": "12",
        "weather": "特大暴雨"
    "wid": "13",
        "weather": "阵雪"
    "wid": "14",
        "weather": "小雪"
    "wid": "15",
        "weather": "中雪"
    "wid": "16",
        "weather": "大雪"
    "wid": "17",
        "weather": "暴雪"
    "wid": "18",
        "weather": "雾"
    "wid": "19",
        "weather": "冻雨"
    "wid": "20",
        "weather": "沙尘暴"
    "wid": "21",
        "weather": "小到中雨"
    "wid": "22",
        "weather": "中到大雨"
    "wid": "23",
        "weather": "大到暴雨"
    "wid": "24",
        "weather": "暴雨到大暴雨"
    "wid": "25",
        "weather": "大暴雨到特大暴雨"
    "wid": "26",
        "weather": "小到中雪"
    "wid": "27",
        "weather": "中到大雪"
    "wid": "28",
        "weather": "大到暴雪"
    "wid": "29",
        "weather": "浮尘"
    "wid": "30",
        "weather": "扬沙"
    "wid": "31",
        "weather": "强沙尘暴"
    "wid": "53",
        "weather": "霾"
*/
    public static void setWeatherIcon(String wid, ImageView imageView) {
        switch (wid) {
            case "00":
                //晴
                imageView.setImageResource(R.mipmap.sunny);
                break;
            case "01":
                //多云
                imageView.setImageResource(R.mipmap.cloudy_sunny);
                break;
            case "02":
                //阴
                imageView.setImageResource(R.mipmap.cloudy);
                break;
            case "03":
                //阵雨
                imageView.setImageResource(R.mipmap.rain_shower);
                break;
            case "04":
                //雷阵雨
                imageView.setImageResource(R.mipmap.thunder_shower);
                break;
            case "05":
                //雷阵雨伴有冰雹
                imageView.setImageResource(R.mipmap.thunder_shower_hail);
                break;
            case "06":
                //雨夹雪
                imageView.setImageResource(R.mipmap.sleet);
                break;
            case "07":
            case "21":
                //小雨
                imageView.setImageResource(R.mipmap.rain_light);
                break;
            case "08":
            case "22":
                //中雨
                imageView.setImageResource(R.mipmap.rain);
                break;
            case "09":
            case "23":
                //大雨
                imageView.setImageResource(R.mipmap.rain_heavy);
                break;
            case "10":
            case "11":
            case "12":
            case "24":
            case "25":
                //暴雨
                imageView.setImageResource(R.mipmap.rain_storm);
                break;
            case "13":
                //阵雪
                imageView.setImageResource(R.mipmap.snow_shower);
                break;
            case "14":
            case "26":
                //小雪
                imageView.setImageResource(R.mipmap.snow_light);
                break;
            case "15":
            case "27":
                //中雪
                imageView.setImageResource(R.mipmap.snow);
                break;
            case "16":
            case "28":
                //大雪
                imageView.setImageResource(R.mipmap.snow_heavy);
                break;
            case "17":
                //暴雪
                imageView.setImageResource(R.mipmap.blizzard);
                break;
            case "18":
                //雾
                imageView.setImageResource(R.mipmap.fog);
                break;
            case "19":
                //冻雨
                imageView.setImageResource(R.mipmap.sleet);
                break;
            case "20":
            case "30":
                //沙尘暴
                imageView.setImageResource(R.mipmap.dust_storm);
                break;
            case "29":
                //浮尘
                imageView.setImageResource(R.mipmap.dust);
                break;
            case "31":
                //沙尘暴
                imageView.setImageResource(R.mipmap.sandstorm);
                break;
            case "53":
                //雾霾
                imageView.setImageResource(R.mipmap.haze);
                break;
        }
    }
}
