package com.twosnail.frame.commin.util.kit.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间格式转换
 */
public class DateKit {

    /**
     * @description 获得当前的时间，yyyy-MM-dd HH:mm:ss
     * @return string类型的当前时间，
     *         pattern yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentStringDate() {
        return date2String(new Date());
    }

    /**
     * @description 将date转换成yyyy-MM-dd HH:mm:ss时间
     * @param date
     * @return 将date转化为yyyy-MM-dd HH:mm:ss形式的时间
     */
    public static String date2String(Date date) {
        return date2String(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * @description 将Date格式的日期按照 format格式进行转换
     * @param date
     * @param format
     * @return
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * @description 将Date格式的日期按照 format格式进行转换
     * @param date
     * @param format
     * @return
     */
    public static String date2String(int time, String format) {
        long l = Long.valueOf(time) * 1000;
        Date date = new Date(l);

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    /**
     * 将yyyyMMddhhmmss类型时间转换成 10位时间（距1970-1-1的秒级数据）
     * 
     * @param dateString
     *        默认pattern yyyyMMddhhmmss
     * @return
     * @throws ParseException
     */
    public static int stringDate2Int(String dateString) throws ParseException {
        return stringDate2Int(dateString, "yyyyMMddHHmmss");
    }

    /**
     * 将String类型时间转换成 10位时间（距1970-1-1的秒级数据）
     * 
     * @param dateString
     * @param pattern - 时间字符串格式
     * @return 10位时间，单位秒
     * @throws ParseException
     */
    public static int stringDate2Int(String dateString, String datePattern) throws ParseException {
        if (dateString == null || dateString.equals("")) {
            return 0;
        }

        Date date = string2Date(dateString, datePattern);
        return getTime(date);
    }

    /**
     * 将date形的转换成long型,即距离1970-1-1的时间数，精确至秒
     * 
     * @param date
     * @return
     */
    public static int getTime(Date date) {
        if (date == null) {
            return 0;
        }

        long time = date.getTime();
        //return (int) Arith.round(time / 1000, 0);
        return (int) ( time / 1000 );
    }

    /**
     * 将long型时间转换成int型时间，取消毫秒级
     * 
     * @param date
     * @return
     */
    public static int getTime(long date) {
        long time = date;
        //return (int) Arith.round(time / 1000, 0);
        return (int) ( time / 1000 );
    }

    /**
     * 获得当前10位的时间
     * 
     * @description 获取系统时间距1970-1-1秒级数据
     */
    public static int getTimeMillis() {
        long time = System.currentTimeMillis();
        //return (int) Arith.round(time / 1000, 0);
        return (int) ( time / 1000 );
    }
    
    /**
     * @description 将long形的转换成String型
     * @param dateTime
     * @param format
     */
    public static String long2String(long dateTime) {
        
        if (Long.valueOf(dateTime).toString().length() <= 10) {
            dateTime *= 1000;
        }
        return date2String( new Date(dateTime) );
    }
    
    /**
     * @description 将long形的转换成String型
     * @param dateTime
     * @param format
     * @return
     */
    public static String long2String(long dateTime, String format ) {
        
        if (Long.valueOf(dateTime).toString().length() <= 10) {
            dateTime *= 1000;
        }
        return date2String(new Date(dateTime) , format );
    }
    /**
     * @description 将long形的转换成date型
     * @param dateTime
     * @return Date
     */
    public static Date long2Date(long dateTime) {
        if (Long.valueOf(dateTime).toString().length() <= 10) {
            dateTime *= 1000;
        }
        return new Date(dateTime);
    }
    
    /**
     * @description 将date形的转换成long型
     * @param date 时间
     * @param length 返回long型的长度
     * @return long
     */
    public static long date2Long(Date date, int length) {
        long dateTime = date.getTime();
        if (length == 10) {
            //dateTime = (long) Arith.round(dateTime / 1000, 0);
            dateTime = dateTime / 1000;
        }
        return dateTime;
    }

    /**
     * @description 将date形的转换成int型
     * @param date 时间
     * @param length 返回long型的长度
     * @return long
     */
    public static int date2Int(Date date) {
        long dateTime = date.getTime();
        //dateTime = (long) Arith.round(dateTime / 1000, 0);
        //return (int) dateTime;
        return (int) ( dateTime / 1000 );
    }

    /**
     * @description 将字符串时间格式的时间转换成Date类型 转换失败返回 null
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date string2Date(String date, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(date);
    }

    /**
     * @description 将字符串格式的日期按照 yyyy-MM-dd 格式进行转换，成为Date类型
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date string2Date(String date) throws ParseException {
        return string2Date(date, "yyyy-MM-dd");
    }

    /**
     * 将yyyy-MM-dd日期转换为yyyy年MM月dd日
     * 
     * @param date
     * @return
     */
    public static String date2CStr(String dateStr) {
        return dateStr.substring(0, 4) + "年" + dateStr.substring(5, 7) + "月" + dateStr.substring(8, 10) + "日";
    }

    /**
     * 通过日期判断是星期几
     * 
     * @param data
     * @author 李军
     * @throws ParseException
     */
    public static String weekDay(Date date) throws ParseException {
        SimpleDateFormat formatD = new SimpleDateFormat("E");
        // "E"表示"day in week"
        String week = formatD.format(date);
        // 将日期中的day of week打印
        return week;
    }

    /**
     * @param 13位毫秒数据
     *        将时间转换成 刚刚、..秒前 等<br/>
     *        5秒内：刚刚
     *        5-60秒：秒前
     *        60-3600秒：分钟前
     *        3600-86400秒：小时前
     *        <431999秒：天前
     *        >431999秒：yyyy-MM-dd
     */
    public static String getCreateTime(int time) {
        int diff = getTimeMillis() - time;
        if (diff <= 5) {
            return "刚刚";
        } else if (diff > 5 && diff < 60) {
            return diff + "秒前";
        } else if (diff >= 60 && diff < 3600) {
            return (diff / 60) + "分钟前";
        } else if (diff >= 3600 && diff < 86400) {
            return (diff / 3600) + "小时前";
        } else if (diff < 431999) {
            return (diff / 86400) + "天前";
        } else {
            long l = Long.valueOf(time) * 1000;
            return date2String(new Date(l));
        }
    }

    /********************* 月、年帮助 **********************************/

    /**
     * Date转为Calendar格式
     * 
     * @param date
     * @return
     */
    public static Calendar date2Calendar(Date date) {
        String dateString = date2String(date);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(dateString.substring(0, 4)), Integer.valueOf(dateString.substring(5, 7)) - 1,
            Integer.valueOf(dateString.substring(8, 10)));
        return calendar;
    }

    /**
     * 功能：得到当前月份月初 格式为：xxxx-yy-01 (eg: 2012-02-01)
     * 
     * @param date Date形式
     * @return String
     * @author pure
     */
    public static String firstDayCurMouth(Date date) {
        Calendar calendar = date2Calendar(date);
        int x = calendar.get(Calendar.YEAR);
        int y = calendar.get(Calendar.MONTH) + 1;
        String strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        return x + "-" + strY + "-01";
    }

    /**
     * 功能：得到当前月份月底 格式为：xxxx-yy-31 xxxx-yy-30 xxxx-02-28 xxxx-02-29 (eg:
     * 2007-12-31)
     * 
     * @param date Date形式
     * @return String
     * @author pure
     **/
    public static String lastDayCurMouth(Date date) {
        Calendar calendar = date2Calendar(date);
        int x = calendar.get(Calendar.YEAR);
        int y = calendar.get(Calendar.MONTH) + 1;

        String strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        String strZ = String.valueOf(dayInMonth(x, y));

        return x + "-" + strY + "-" + strZ;
    }

    /**
     * 功能：得到上个月份月初 格式为：xxxx-yy-01 (eg: 2012-02-01)
     * 
     * @param date Date形式
     * @return String
     * @author pure
     */
    public static String firstDayPrevMouth(Date date) {
        Calendar calendar = date2Calendar(date);
        int x = calendar.get(Calendar.YEAR);
        int y = calendar.get(Calendar.MONTH);
        if( y == 0 ) {
        	y = 12;
        	x--;
        }
        //y的取值0-11，如果要用y显示月份需要+1
        String strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        return x + "-" + strY + "-01";
    }

    /**
     * 功能：得到上个月份月底 格式为：xxxx-yy-31 xxxx-yy-30 xxxx-02-28 xxxx-02-29 (eg:
     * 2007-12-31)
     * 
     * @param date Date形式
     * @return String
     * @author pure
     **/
    public static String lastDayPrevMouth(Date date) {
        Calendar calendar = date2Calendar(date);
        int x = calendar.get(Calendar.YEAR);
        int y = calendar.get(Calendar.MONTH);
        if( y == 0 ) {
        	y = 12;
        	x--;
        }
        
        String strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        String strZ = String.valueOf(dayInMonth(x, y));

        return x + "-" + strY + "-" + strZ;
    }
    
    /**
     * 功能：得到下月月份月初 格式为：xxxx-yy-01 (eg: 2012-02-01)
     * 
     * @param date Date形式
     * @return String
     * @author pure
     */
    public static String firstDayNextMouth(Date date) {
        Calendar calendar = date2Calendar(date);
        int x = calendar.get(Calendar.YEAR);
        int y = calendar.get(Calendar.MONTH);
        y += 2;
        if( y == 13 ) {
        	y = 1;
        	x++;
        }
        
        String strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        return x + "-" + strY + "-01";
    }
   
    /**
     * 功能：得到下月月份月底 格式为：xxxx-yy-31 xxxx-yy-30 xxxx-02-28 xxxx-02-29 (eg:
     * 2007-12-31)
     * 
     * @param date Date形式
     * @return String
     * @author pure
     **/
    public static String lastDayNextMouth(Date date) {
        Calendar calendar = date2Calendar(date);
        int x = calendar.get(Calendar.YEAR);
        int y = calendar.get(Calendar.MONTH);
        y += 2;
        if( y == 13 ) {
        	y = 1;
        	x++;
        }
        
        String strY = y >= 10 ? String.valueOf(y) : ("0" + y);
        String strZ = String.valueOf(dayInMonth(x, y));

        return x + "-" + strY + "-" + strZ;
    }

    /**
     * 功能：得到当前季度季初 格式为：xxxx-01-01, xxxx-04-01, xxxx-07-01, xxxx-10-01 (eg:
     * 2007-10-01)
     * 
     * @param date Date形式
     * @return String
     * @author pure
     */
    public static String firstDayCurSeasion(Date date) {
        Calendar calendar = date2Calendar(date);
        String dateString = "";
        int x = calendar.get(Calendar.YEAR);
        int y = calendar.get(Calendar.MONTH) + 1;
        if (y >= 1 && y <= 3) {
            dateString = x + "-" + "01" + "-" + "01";
        }
        if (y >= 4 && y <= 6) {
            dateString = x + "-" + "04" + "-" + "01";
        }
        if (y >= 7 && y <= 9) {
            dateString = x + "-" + "07" + "-" + "01";
        }
        if (y >= 10 && y <= 12) {
            dateString = x + "-" + "10" + "-" + "01";
        }
        return dateString;
    }

    /**
     * 功能：得到当前季度季末 格式为：xxxx-03-31, xxxx-06-30, xxxx-09-30, xxxx-12-31 (eg:
     * 2007-12-31)
     * 
     * @param date Date形式
     * @return String
     * @author pure
     */
    public static String lastDayCurSeasion(Date date) {
        Calendar calendar = date2Calendar(date);
        String dateString = "";
        int x = calendar.get(Calendar.YEAR);
        int y = calendar.get(Calendar.MONTH) + 1;
        if (y >= 1 && y <= 3) {
            dateString = x + "-" + "03" + "-" + "31";
        }
        if (y >= 4 && y <= 6) {
            dateString = x + "-" + "06" + "-" + "30";
        }
        if (y >= 7 && y <= 9) {
            dateString = x + "-" + "09" + "-" + "30";
        }
        if (y >= 10 && y <= 12) {
            dateString = x + "-" + "12" + "-" + "31";
        }
        return dateString;
    }

    /**
     * 功能：得到当前年份年初 格式为：xxxx-01-01 (eg: 2007-01-01)
     * 
     * @param date String形式
     * @return String
     */
    public static String firstDayCurYear(Date date) {
        Calendar calendar = date2Calendar(date);
        int x = calendar.get(Calendar.YEAR);
        return x + "-01" + "-01";
    }

    /**
     * 功能：得到当前年份年底 格式为：xxxx-12-31 (eg: 2007-12-31)<br>
     * 
     * @param date Date形式
     * @return String
     */
    public static String lastDayCurYear(Date date) {
        Calendar calendar = date2Calendar(date);
        int x = calendar.get(Calendar.YEAR);
        return x + "-12" + "-31";
    }

    /**
     * 得到某年某月的天数
     * 
     * @param year 年数
     * @param mouth 月数
     * @return maxDate 天数
     * @author 李军
     */
    public static int dayInMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        //必须加上号数。并且号数不能为31,30,29。否在获得上月或者下月月末号数的时候会出现问题。
        cal.set(Calendar.DATE, 15);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        return maxDate;
    }
    
    
    /**
     * 将int型转换成date型
     * 
     * @param iDateTime 整型时间格式
     * @return date型时间格式
     * 
     * */
    public static Date int2Date(int iDateTime) {
        long lDateTime = Long.valueOf(iDateTime);
        return DateKit.long2Date(lDateTime);
    }

    /**
     * @description 将long形的转换成date型
     * @param dateTime
     * @return Date
     */
    public static Date long2Date(Long dateTime) {

        if (dateTime.toString().length() <= 10) {

            dateTime = dateTime * 1000;
        }
        return new Date(dateTime);
    }

    private static Date add(Date date, int calendarField, int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }

    /**
     * 获取年月.
     * 
     * @author XUJUN
     * @date 2013-2-22
     * @return 获取年月
     */
    public static String getYearMonth() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static Date addYears(Date date, int amount) {
        return add(date, Calendar.YEAR, amount);
    }

    public static Date addMonths(Date date, int amount) {
        return add(date, Calendar.MONTH, amount);
    }

    public static Date addWeeks(Date date, int amount) {
        return add(date, Calendar.WEEK_OF_YEAR, amount);
    }

    public static Date addDays(Date date, int amount) {
        return add(date, Calendar.DAY_OF_MONTH, amount);
    }

    /**
     * 根据周获取日期.
     * 
     * @author HC
     * @date Apr 22, 2013
     * @param num 0：本周 1：下周 以此类推
     * @param week 周几，对应Calendar中的常量，如周一-Calendar.MONDAY
     * @return 日期
     */
    public static Date getWeek(int num, int week) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, num * 7);
        //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK, week);
        return cal.getTime();
    }

    /**
     * 数字转周.
     * 
     * @author HC
     * @date Apr 22, 2013
     * @param value 数字，如1对应周一
     * @return 周数对应数值
     */
    public static Integer parse2CalWeek(int value) {
        switch (value + 1) {
        case Calendar.MONDAY:
            return Calendar.MONDAY;
        case Calendar.TUESDAY:
            return Calendar.TUESDAY;
        case Calendar.WEDNESDAY:
            return Calendar.WEDNESDAY;
        case Calendar.THURSDAY:
            return Calendar.THURSDAY;
        case Calendar.FRIDAY:
            return Calendar.FRIDAY;
        case Calendar.SATURDAY:
            return Calendar.MONDAY;
        case 8:
            return Calendar.SUNDAY;
        default:
            return null;
        }
    }
}
