package com.vzone.tmdsdk.tool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 詹子聪 on 2016/6/2.
 */
public class StringTool {

    /**
     * 时间戳转日期
     * @param timeMillis System.currentTimeMillis()
     * @return 时间
     */
    public static String getDateFromTimeMillis(long timeMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sdf.format(new Date(timeMillis));
        return time;
    }
}
