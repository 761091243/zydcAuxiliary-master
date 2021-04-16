package com.wangpeng.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Dunn
 * @date 2021年04月16日 15:30
 */
public class DateUtil {

    /**
     * 返回一个-+（time）时间
     * @param today
     * @return
     */
    public static Date toDate(Date today,int time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + time);
        return calendar.getTime();
    }



}
