package com.twosnail.frame.commin.util.kit.list;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * List<Map<String,Object>>排序
 * @version 1.0
 * @author  
 * 按照map中某个key的值对list排序
 * */
public class ListSort implements Comparator<Map<String,Object>> {

	/**
	 * 需要比较的字段名称
	 */
	private String compareName = "";
	
	public ListSort(){}
	
	public ListSort( String compareName ){
		this.compareName = compareName;
	}

	/**
	 * 
	 * @param list 需要排序的List
	 * @return 排序后的List
	 */
	public List<Map<String,Object>> compareList(List<Map<String,Object>> list) {
		Collections.sort(list, this);
		return list;
	}

	/**
	 * 队列排序实现Comparator接口方法。
	 * 
	 * @param list1
	 * @param list2
	 * @return sortUp(list1,list2)=1 - list1>list2 sortUp(list1,list2)=-1 - list1<list2 sortUp(list1,list2)=0
	 *         - list1=list2
	 * 
	 * */
	@Override
	public int compare(Map<String,Object> list1, Map<String,Object> list2) {
		return sortUp(list1,list2);
	}

	/**
	 * 按时间将list排序
	 * 
	 * @param list1
	 * @param list2
	 * @return 1 - list1>list2 -1 - list1<list2 0 - list1=list2
	 * 
	 * */
	private int sortUp(Map<String,Object> list1, Map<String,Object> list2) {

		//分别取出需要比较的值
		Object a=list1.get(compareName);
		Object b=list2.get(compareName);
		 
		if(a instanceof Date){
			return sortDate(a,b);
		}
		else if(a instanceof Float||a instanceof Double){
			return sortFloat(a,b);
		}
		else if(a instanceof Long||a instanceof Integer){
			return sortInteger(a,b);
		}
		else if(a instanceof String){
			return sortStr(a,b);
		}
		return 0;//若什么都不是则返回为最初排序值
		 
	}
	
	/**
	 * date型比较
	 */
	private int sortDate(Object a, Object b){ 

 		//将Date型转换为int型再进行比较
 		long ai=((Date)a).getTime();
 		long bi=((Date)b).getTime();
		return sortInteger( ai , bi );
	}
 	 
 	
 	/**
 	 * Long,int型比较
 	 * @param a
 	 * @param b
 	 * @return
 	 */
	private int sortInteger(Object a , Object b){
 		
 		if (Integer.parseInt(a.toString())>Integer.parseInt(b.toString())) {
			return -1;
		} else if (Integer.parseInt(a.toString())<Integer.parseInt(b.toString())) {
			return 1;
		} else {
			return 0;
		}
 	}
 	
 	/**
 	 * float,double型比较
 	 * @param a
 	 * @param b
 	 * @return
 	 */
	private int sortFloat(Object a , Object b){
 		
 		if (Float.parseFloat(a.toString())>Float.parseFloat(b.toString())) {
			return -1;
		} else if (Float.parseFloat(a.toString())<Float.parseFloat(b.toString())) {
			return 1;
		} else {
			return 0;
		}
 	}
 	
	/**
 	 * String型比较(主要是整数日期格式的yyyy-mm-dd)
 	 * @param a
 	 * @param b
 	 * @return
 	 */
	private int sortStr(Object a,Object b){
 		String stra=(String)a;
 		String strb=(String)b;
 		stra=stra.replaceAll("-", "");
 		strb=strb.replaceAll("-", "");
 		if(!isNumeric(stra)||!isNumeric(strb)){
 			return 0;//若去除-之后还有其他字符，则返回为最初的排序结果
 		}
 		else{
 			return sortInteger(stra,strb);
 		}
 		
 	}


 	//判断是否全为数字
	private boolean isNumeric(String str)
 	{
 		Pattern pattern = Pattern.compile("[0-9]*");
     	Matcher isNum = pattern.matcher(str);
     	if( !isNum.matches() ){
    	
     		return false;
    	}
    	return true;
    } 
 	
}
