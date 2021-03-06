package com.xue.yado.memorandum.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/11/12.
 */

public class TimerUtil {

    private static SimpleDateFormat simpleDateFormat ;

    //将Date类转化为"xxxx年xxy月xx日 xx时:xx分"
    public static String getYear_Month_Day_Hour_Minute(Date date){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    //将Date类转化为"xxxx年xxy月xx日"
    public static String getYear_Month_Day(Date date){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    //将字符串"xxxx年xxy月xx日"转化为Date类型
    public static Date getDateWithHourAndMinute(String s){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //将字符串"xxxx年xxy月xx日"转化为Date类型
    public static Date getDateWithoutHourAndMinute(String s){
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


}
