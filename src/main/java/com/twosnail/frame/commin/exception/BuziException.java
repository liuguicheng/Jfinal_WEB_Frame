package com.twosnail.frame.commin.exception;
/**   
 * @Title: BuziException.java
 * 自定义异常
 */
public class BuziException extends Exception{

	private static final long serialVersionUID = 1L;

	public BuziException(String message){
		super(message);
	}
	
	public BuziException(Throwable e){
		super(e);
	}
	
	public BuziException(String message, Throwable e){
		super(message, e);
	}
	
}
