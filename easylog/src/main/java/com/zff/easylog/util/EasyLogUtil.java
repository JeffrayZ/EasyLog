package com.zff.easylog.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @ProjectName: EasyLog
 * @Package: com.zff.easylog.util
 * @ClassName: EasyLogUtil
 * @Description: 工具类
 * @Author: Jeffray
 * @CreateDate: 2019/6/18 10:41
 * @UpdateUser: 更新者
 * @UpdateDate: 2019/6/18 10:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class EasyLogUtil {

    /**
     * 格式化成为 年 月 日
     */
    private static SimpleDateFormat format_date_ymd = new SimpleDateFormat("yyyy-MM-dd");

    public static String getNowDate() {
        return format_date_ymd.format(Calendar.getInstance().getTime());
    }

    /**
     * 格式化成为 年 月 日 时分秒
     */
    private static SimpleDateFormat format_date_ymdhms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getNowDateAndTime() {
        return format_date_ymdhms.format(Calendar.getInstance().getTime());
    }
}
