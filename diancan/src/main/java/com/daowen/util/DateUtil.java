package com.daowen.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil  {

    public static String getWeekDay(Date date) {

        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();

        int idx = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (idx < 0)
            idx = 0;
        return weekDays[idx];
    }

}
