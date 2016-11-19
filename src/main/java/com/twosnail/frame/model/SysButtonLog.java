package com.twosnail.frame.model;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.kit.request.RequestKit;

/**   
 * @Title: SysButtonLog.java
 */

@SuppressWarnings("serial")
public class SysButtonLog extends Model<SysButtonLog>{
	public static final SysButtonLog me = new SysButtonLog() ; 
	
	/**
	 * 获取按钮日志
	 * @param keyWord
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Record> getButtonLog( String keyWord , int pageNumber, int pageSize) {
		StringBuffer sb = new StringBuffer(" FROM sys_button_log a WHERE 1=1 ");
		/*if( keyWord != null && "".equals( keyWord = keyWord.trim() ) ) {
			sb.append( " AND (a.userName LIKE '%"+keyWord+"%' or a.id LIKE '%"+keyWord+"%')" ) ;
		}*/
		
		return Db.paginate(pageNumber, pageSize, 
				"SELECT a.*,(select a1.userName from sys_user a1 WHERE a1.id = a.userId) userName " , sb.toString() );
			
	}
	
	public void delUserTx( String[] ids ) throws BuziException{
		for (String id : ids) {
			if( !me.deleteById( id ) ) {
	            throw new BuziException( "删除用户失败!" );
	        }
		}
    }
	
	/**
	 * 按钮操作日志记录
	 * @param request
	 * @param session
	 * @param methodClass 方法类
	 * @param methodName  方法名
	 * @param methodPath  方法路径
	 * @param methodParam 方法参数
	 * @param logDesc	  描述
	 */
	public void addButtonLog( HttpServletRequest request , HttpSession session,
			String methodClass,String methodName,String methodPath,
			String methodParam,String logDesc ) {
		SysButtonLog log = new SysButtonLog() ;
		try {
			log.set( "userId", session.getAttribute("userId") );
			log.set( "methodClass",methodClass);
			log.set( "methodName",methodName);
			log.set( "methodPath",methodPath);
			log.set( "methodParam",methodParam);
			log.set( "logIp", RequestKit.getIpAddr(request));
			log.set( "operaTime" ,System.currentTimeMillis());
			log.set( "logDesc",logDesc);
			if( !log.save() ) {
	            throw new BuziException( "添加信息失败!" );
	        }
		} catch (Exception e) {
		}
		
	}
}
