package com.twosnail.frame.commin.util.kit.request;

import javax.servlet.http.HttpServletRequest;

import com.twosnail.frame.commin.util.kit.string.Validation;

public class RequestKit {

	public static String getIpAddr( HttpServletRequest request ) {
	       
		String ip = request.getHeader("x-forwarded-for");  
	    
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("Proxy-Client-IP");  
		}  
	    
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getHeader("WL-Proxy-Client-IP");  
		}  
	    
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			ip = request.getRemoteAddr();  
		}
		String[] ips = ip.split( "," );
		//从末尾开始遍历
		for( int i = ips.length - 1; i >= 0; i-- ) {
			if( Validation.isNullTrim( ips[i] ) ) {
				continue;
			}
			ip = ips[i].trim();
			if( ip.startsWith( "10." ) ) {
				continue;
			}
			if( ip.equals( "127.0.0.1" ) ) {
				continue;
			}
			if( ip.equals( "localhost" ) ) {
				continue;
			}
			return ip;
		}
		return "";  
	}
	
	public static String getBasePath( HttpServletRequest request ) {
		
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		return basePath;
	}
	
	
}
