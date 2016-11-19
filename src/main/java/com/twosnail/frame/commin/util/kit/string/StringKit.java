/**
 * 
 */
package com.twosnail.frame.commin.util.kit.string;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串帮助类
 * @author HUSL
 * @date 2012-2-21
 */
public class StringKit {

	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String numberChar = "0123456789";

	/**
	 * @description 产生随机字符串
	 * @param length  字符串长度
	 * @return String
	 */
	public static String randomString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++)
			sb.append(allChar.charAt(random.nextInt(numberChar.length())));

		return sb.toString();
	}

	/**
	 * 组合字符串方法
	 * 
	 * @param ArrayList
	 *            <String> as 需要组合的字符串数组。
	 * @param String
	 *            separator 用来分隔的分隔符。
	 * @return String s 被组合的字符串。
	 * */
	public static String composeStr(List<String> list, String separator) {
		StringBuffer buffer = new StringBuffer();
		
		int length = list.size();
		for (int i = 0; i < length; i++) {
			String s = list.get(i);
			if(Validation.isNull(s)){
				continue;
			}
			s = s.replaceAll(separator, "");
			
			if (i == length - 1) {
				buffer.append(s);
			} else {
				buffer.append(s + separator);
			}
		}
		return buffer.toString();
	}

	/**
	 * 分解字符串方法
	 * 
	 * @param String
	 *            s 需要分解的字符串。
	 * @param String
	 *            separator 用来分隔的分隔符。
	 * @return ArrayList<String> as 被分解的字符串数组。
	 * */
	public static List<String> resolveStr(String str, String separator) {
		List<String> list = new ArrayList<String>();
		
		if(Validation.isNull(str)){
			return list;
		}
		
		String[] array = str.split(separator);
		
		int length = array.length;
		for (int i = 0; i < length; i++) {
			String s = array[i];
			
			if(!Validation.isNull(s)){
				list.add(s);
			}
		}
		return list;
	}
	
	
	/**
	 * 分解字符串方法
	 * */
	public static String groupStr(List<String> str, String separator) {
		StringBuffer buffer = new StringBuffer();
		
		if(str == null || str.size() <= 0){
			return buffer.toString();
		}
		
		for (int i = 0; i < str.size(); i++) {
			buffer.append(str.get(i));
			
			if(i != str.size() -1){
				buffer.append(separator);
			}
		}

		return buffer.toString();
	}


	/**
	 * 过滤script
	 */
	public static String scriptFilter( String html ) {
		String exec = "<\\S*(?i)script[\\s\\S]*</script>";
		return html.replaceAll(exec, "");
	}
	
	/**
	 * 过滤iframe
	 */
	public static String iframeFilter( String html ) {
		String exec = "<(?i)iframe[\\s\\S]*</iframe>";
		return html.replaceAll(exec, "");
	}
	
	/**
	 * 过滤frameset
	 */
	public static String framesetFilter( String html ) {
		String exec = "<(?i)frameset[\\s\\S]*</frameset>";
		return html.replaceAll( exec, "" );
	}
	
	/**
	 * 过滤href
	 */
	public static String hrefFilter( String html ) {
		String exec = "(?i)href\\s*=[\'\"][^\'^\"]*[\'\"]";
		return html.replaceAll( exec, "" );
	}
	
	/**
	 * 过滤on***
	 */
	public static String onFilter( String html ) {
		String exec	= "[\'\"\\s]+(?i)on[\\s|\\S]*=";
		return html.replaceAll( exec, "" );
	}
	
	/**
	 * 过滤html中的script、iframe、href、frameset
	 */
	public static String filterHtml( String html ) {
		return onFilter( hrefFilter( framesetFilter( iframeFilter( scriptFilter( html ) ) ) ) );
	}
 	
 	/**
 	 * 过滤双引号和单引号
 	 * @param str
 	 * @return
 	 */
 	public static String filterQuotation(String str){
 		String str2 =str.replace("'", "\\'"); 
 		return str2.replace("\"", "\\\""); 
 		
 	}
	
	/**
	 * @description 编码转换
	 * @param str:待转换字符串
	 * @param code:编码
	 * @return
	 */
	public static String changeCode(String str, String oldCode, String newCode) {
		try {
			if (Validation.isNull(str))
				return "";
			else {
				str = new String( str.getBytes( oldCode ), newCode );
				return str;
			}
		} catch (Exception e) {
			return "";
		}
	}
	
    /**
	 * 对比两个字符串的大小<br/>
	 * 如果a和b的长度不同，则长度长的对比结果大<br/>
	 * 如果a和b的长度相同，则从第0位开始对比每一位的asc码。asc码大的对比结果大<br/>
	 * @param a
	 * @param b 
	 * @return 1  - a>b <br/>
	 * 		   -1 - a<b <br/>
	 * 		   0  - a=b <br/>
	 */
	public static int comparetString( String a, String b ) {
		int big = a.length() - b.length();
		if( big > 0 ) {
			return 1;
		} else if( big < 0 ) {
			return -1;
		} else {
			int l = a.length();
			for( int i = 0; i < l; i++ ) {
				big = a.charAt( i ) - b.charAt( i );
				if( big > 0 ) {
					return 1;
				} else if( big < 0 ) {
					return -1;
				}
			}
		}
		return 0;
	}
    
	/**
	 * 字符串缩略
	 * @param str
	 * @param length
	 * @return 特定长度字符串 + ...
	 */
	public static String strAbridge(String str, int length){
		return strAbridge(str, length, "...");
	}
	
	/**
	 * 字符串缩略
	 * @param str
	 * @param length
	 * @param suffix
	 * @return 特定长度字符串 + 后缀
	 */
	public static String strAbridge(String str, int length, String suffix){
		if(Validation.isNull(str)){
			return str;
		}
		
		if(length >= str.length()){
			return str;
		}
		
		String s = str.substring(0, length);
		return s + suffix;
	}

	/**
	 * 数组中是否包含某字段
	 * @param strs
	 * @param string
	 * @return
	 */
	public static boolean contain(String[] strs, String string) {
		for (String str : strs) {
		    if(str==null||str.equals("")){
		        continue;
		    }
			if(string.equals(str) || string.contains(str)){
				return true;
			}
		}
		
		return false;
	}

	private final static String regxpForHtml = "<([^>]*)>"; 
	/**
	 * 过滤html标签
	 * @param content
	 * @return
	 */
	public static String htmlFilter(String str) {
		if(Validation.isNull(str)){
			return str;
		}
		
		str = filterHtml(str);
		
		Pattern pattern = Pattern.compile(regxpForHtml);
		Matcher matcher = pattern.matcher(str);

		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}

		matcher.appendTail(sb);
		return sb.toString();
	}
	
	
	/**
	 * 过滤不可见字符.
	 * 目前只能显示中文，和英文。其他字符会被过滤
	 * @param content
	 * @return
	 */
	public static String filterUnReadChar( String content ) {
		
		String regex = "([[^a-z^A-Z\\/<>=\"-:., ]&&[^\\u3400-\\u4db5]&&[^\\u4e00-\\u9fa5]&&[^\\u9fa6-\\u9fbb]&&[^\\uf900-\\ufa2d]&&[^\\ufa30-\\ufa6a]&&[^\\ufa70-\\ufad9]&&[^\\u20000-\\u2a6d6]&&[^\\u2f800-\\u2fa1d]&&[^\\uff00-\\uffef]&&[^\\u2E80-\\u2EFF]&&[^\\u3000-\\u303F]&&[^\\u3040-\\u309f]&&[^\\u30a0-\\u30ff]])";
		Pattern p = Pattern.compile( regex, Pattern.UNICODE_CASE );
		Matcher m = p.matcher( content );
		return m.replaceAll( " " );
	}
	
}
