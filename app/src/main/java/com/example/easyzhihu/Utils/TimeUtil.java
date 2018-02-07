package com.example.easyzhihu.Utils;

import com.example.easyzhihu.Activities.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by My Computer on 2018/1/30.
 */

public class TimeUtil {

    public static String getDateToString(long time) {
        Date d = new Date(time*1000);
        SimpleDateFormat sf=new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
        return sf.format(d);
    }

    public static Date getDateBefore(Date date){  //用于更新时间标签以便于申请之前的新闻

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        return calendar.getTime();
    }

    public static String getDayInWeek(Date date){   //用于获取具体日期显示在获取更多里
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        String day="";
        switch (calendar.get(Calendar.DAY_OF_WEEK)){
            case 1:
                day="星期日";
                break;
            case 2:
                day="星期一";
                break;
            case 3:
                day="星期二";
                break;
            case 4:
                day="星期三";
                break;
            case 5:
                day="星期四";
                break;
            case 6:
                day="星期五";
                break;
            case 7:
                day="星期六";
                break;
            default:
                break;
        }
        return day;
    }

    public static int getYear(String date){
        return Integer.valueOf(date.substring(0,4));
    }

    public  static int getMonth(String date){
        return Integer.valueOf(date.substring(4,6))-1;
    }

    public static int getDay(String date){
        return Integer.valueOf(date.substring(6,8));
    }

}
