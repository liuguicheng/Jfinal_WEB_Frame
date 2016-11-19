package com.twosnail.frame.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.frame.model.SysLoginLog;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.result.ResultObj;

/**   
 * @Title: UserController.java
 */
public class LoginLogController extends Controller {
	
	private Logger logger = Logger.getLogger(LoginLogController.class) ;
	
	/**
	 * 登录日志
	 */
	@RequiresPermissions("LoginLogController")
	public void index(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			String keyWord = getPara("keyWord") ;
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 5 : numPerPage;
	        
	        Page<Record> list = SysLoginLog.me.getLoginLog(  getPara("keyWord"), pageNum, numPerPage );
	        setAttr( "list", list );
	        setAttr( "keyWord", keyWord );	        
		} catch (Exception e) {
			this.logger.warn( "用户列表信息，初始化失败！" , e );
		}
        render( "login_log_list.html" );
	}
	
	/**
     * 删除登录日志
     */
	@RequiresPermissions("LoginLogController.dellogin")
    public void dellogin(){
    	String id  = getPara( "id" ) ;
        try {
        	if( id == null || "".equals( id ) ) {
        		renderJson( new ResultObj( ResultObj.FAIL , "参数Ids不能为空！" , null ) );
        	} else {
        		SysLoginLog.me.delUserTx(  id );
        	}
        	renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
        } catch ( BuziException e ) {
            this.logger.warn( "删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
        } catch (Exception e) {
        	this.logger.warn( "系统异常，删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
		}
    }
	
}
