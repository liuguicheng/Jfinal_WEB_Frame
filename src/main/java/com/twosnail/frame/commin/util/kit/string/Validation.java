package com.twosnail.frame.commin.util.kit.string;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串验证
 * @author lihaiyan
 *
 */
public class Validation {
	
	private final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();
	static {
		zoneNum.put(11, "北京");
		zoneNum.put(12, "天津");
		zoneNum.put(13, "河北");
		zoneNum.put(14, "山西");
		zoneNum.put(15, "内蒙古");
		zoneNum.put(21, "辽宁");
		zoneNum.put(22, "吉林");
		zoneNum.put(23, "黑龙江");
		zoneNum.put(31, "上海");
		zoneNum.put(32, "江苏");
		zoneNum.put(33, "浙江");
		zoneNum.put(34, "安徽");
		zoneNum.put(35, "福建");
		zoneNum.put(36, "江西");
		zoneNum.put(37, "山东");
		zoneNum.put(41, "河南");
		zoneNum.put(42, "湖北");
		zoneNum.put(43, "湖南");
		zoneNum.put(44, "广东");
		zoneNum.put(45, "广西");
		zoneNum.put(46, "海南");
		zoneNum.put(50, "重庆");
		zoneNum.put(51, "四川");
		zoneNum.put(52, "贵州");
		zoneNum.put(53, "云南");
		zoneNum.put(54, "西藏");
		zoneNum.put(61, "陕西");
		zoneNum.put(62, "甘肃");
		zoneNum.put(63, "青海");
		zoneNum.put(64, "宁夏");
		zoneNum.put(65, "新疆");
		zoneNum.put(71, "台湾");
		zoneNum.put(81, "香港");
		zoneNum.put(82, "澳门");
		zoneNum.put(91, "国外");
	}

	private final static int[] PARITYBIT = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
	private final static int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/**
	 * 
	 * 身份证验证
	 * 
	 * @param s
	 *            号码内容
	 * @return 是否有效 null和"" 都是false
	 */
	public static boolean isIdcard(String s) {
		if (s == null || (s.length() != 15 && s.length() != 18))
			return false;
		final char[] cs = s.toUpperCase().toCharArray();
		// 校验位数
		int power = 0;
		for (int i = 0; i < cs.length; i++) {
			if (i == cs.length - 1 && cs[i] == 'X')
				break;// 最后一位可以 是X或x
			if (cs[i] < '0' || cs[i] > '9')
				return false;
			if (i < cs.length - 1) {
				power += (cs[i] - '0') * POWER_LIST[i];
			}
		}

		// 校验区位码
		if (!zoneNum.containsKey(Integer.valueOf(s.substring(0, 2)))) {
			return false;
		}

		// 校验年份
		String year = s.length() == 15 ? "19" + s.substring(6, 8) : s.substring(6, 10);
		final int iyear = Integer.parseInt(year);
		if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR))
			return false;// 1900年的PASS，超过今年的PASS

		// 校验月份
		String month = s.length() == 15 ? s.substring(8, 10) : s.substring(10, 12);
		final int imonth = Integer.parseInt(month);
		if (imonth < 1 || imonth > 12) {
			return false;
		}

		// 校验天数

		String day = s.length() == 15 ? s.substring(10, 12) : s.substring(12, 14);
		final int iday = Integer.parseInt(day);
		if (iday < 1 || iday > 31)
			return false;

		//校验一个合法的年月日
		if (!validate(iyear, imonth, iday))
			return false;

		// 校验"校验码"
		if (s.length() == 15)
			return true;
		return cs[cs.length - 1] == PARITYBIT[power % 11];
	}
	
	/**
	 * 校验合法的年月日
	 * @param year
	 * @param imonth
	 * @param iday
	 * @return
	 */
	private static boolean validate(int year, int imonth, int iday) {
		// 比如考虑闰月，大小月等
		return true;
	}
	
	
   	/**
 	 * 验证输入的固定电话号码是否符合
 	 * @param faxNum
 	 * @return 是否合法   true为座机  false：不为座机
 	 */
 	public static boolean isPhone(String faxNum)
 	{
 		boolean tag = true;
 		final String regex = "0[0-9]{2,3}[2-9][0-9]{6,7}[0-9]{1,4}$";
 		final Pattern pattern = Pattern.compile(regex);
 		final Matcher mat = pattern.matcher(faxNum);
 		if (!mat.find())
 		{
 			tag = false;
 		}
 		return tag;
 	}
 	
 	/**
	 * 验证输入的邮箱格式是否符合
	 * @param email
	 * @return 是否合法
	 * @author  刘洋帆  
	 */
	public static boolean isEmail(String email)
	{
		boolean tag = true;
		final String regex = "^(\\w+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(regex);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find())
		{
			tag = false;
		}
		return tag;
	}
	
	/**
	 * 验证输入的手机号码是否符合
	 * @param mobiles
	 * @return 是否合法
	 * @author  刘洋帆  
	 */
	public static boolean isMobile(String mobiles)
	{
		return mobiles.matches( "^0?1[34586]{1}[0-9]{9}" );
	}
	
	/**
	 * 检查字符串是否全是数字
	 * @param  str  
	 * @return  如果全是数字返回true否则返回false
	 */
	public static boolean isDigit(String str){
		 char[] ch = str.toCharArray();
		 for (int i = 0; i < ch.length; i++) {
		   if (!Character.isDigit(ch[i])) {
			 return false;
		  }
	    }
		return  true;
	}  
	/**
 	 * 验证邮政编码是否符合
 	 * @param User_Zipcode
 	 * @return 是否合法
 	 */
 	public static boolean isZipcode(String User_Zipcode)
 	{
 		boolean tag = true;
 		final String regex = "^\\d{6}$";
 		final Pattern pattern = Pattern.compile(regex);
 		final Matcher mat = pattern.matcher(User_Zipcode);
 		if (!mat.find())
 		{
 			tag = false;
 		}
 		return tag;
 	}
 
 	/**
 	 * 字符串是否为空，	true -- 为空
 	 * 				  	false -- 不为空
 	 * @param str
 	 * @return
 	 */
 	public static boolean isNull( String str ) {
 		if( str == null || "".equals( str ) || "null".equals( str ) ) {
 			return true;
 		}
 		return false;
 	}
 	
 	public static boolean isNullTrim( String str ) {
 		if( str == null || "".equals( str.trim() ) || "null".equals( str.trim() ) ) {
 			return true;
 		}
 		return false;
 	}
 	
	/**
 	 * list是否为空，	true -- 为空
 	 * 				  	false -- 不为空
 	 * @param str
 	 * @return
 	 */
 	public static boolean isNull(Collection<?> list){
 		if(list == null || list.size() <= 0){
 			return true;
 		}
 		return false;
 	}
 	
 	/**
 	 * map是否为空，	true -- 为空
 	 * 				  	false -- 不为空
 	 * @param str
 	 * @return
 	 */
 	public static boolean isMapNull(Map<?, ?> map){
 		if(map == null || map.size() <= 0){
 			return true;
 		}
 		return false;
 	}
}
