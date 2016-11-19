package com.twosnail.frame.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.frame.model.SysButtonLog;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.result.ResultObj;

/**   
 * @Title: UserController.java
 */
public class BtnLogController extends Controller {
	
	private Logger logger = Logger.getLogger(BtnLogController.class) ;
	
	/**
	 * 按钮日志
	 */
	@RequiresPermissions("BtnLogController")
	public void index(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			String keyWord = getPara("keyWord") ;
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 5 : numPerPage;
	        
	        Page<Record> list = SysButtonLog.me.getButtonLog(  getPara("keyWord"), pageNum, numPerPage );
	        setAttr( "list", list );
	        setAttr( "keyWord", keyWord );	        
		} catch (Exception e) {
			this.logger.warn( "用户列表信息，初始化失败！" , e );
		}
        render( "botton_log_list.html" );
	}
	
	/**
     * 删除操作日志
     */
	@RequiresPermissions("BtnLogController.delbotton")
    public void delbotton(){
    	String id  = getPara( "id" ) ;
        try {
        	if( id == null || "".equals( id ) ) {
        		renderJson( new ResultObj( ResultObj.FAIL , "参数Ids不能为空！" , null ) );
        	} else {
        		String[] ids =  id.split(",") ;
        		SysButtonLog.me.delUserTx(  ids );
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
