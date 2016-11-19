package com.twosnail.frame.controller;

import java.util.List;

import com.twosnail.frame.commin.util.kit.request.RequestKit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.ClearInterceptor;
import com.jfinal.aop.ClearLayer;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.twosnail.frame.ext.CaptchaRender;
import com.twosnail.frame.model.SysLoginLog;
import com.twosnail.frame.model.SysMenu;
import com.twosnail.frame.model.SysUser;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.result.ResultObj;
import com.twosnail.frame.commin.util.tree.TreeNode;

/**   
 * @Title: IndexController.java
 */

public class IndexController extends Controller {
	
	private Logger logger = Logger.getLogger(IndexController.class) ;
	
	@ClearInterceptor(ClearLayer.ALL)
	public void index() {
		render("login.html");
	}
	
	@ClearInterceptor(ClearLayer.ALL)
	public void login(){
		index() ;
	}
	
	@ClearInterceptor(ClearLayer.ALL)
	public void img(){
		CaptchaRender img = new CaptchaRender(4); 
		this.setSessionAttr( CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY, img.getMd5RandonCode() );
		render(img);
	}	
	
	@ClearInterceptor(ClearLayer.ALL)
	public void check(){
		try {
			
			String captchaCode = getPara("code");
			Boolean isRemember = getParaToBoolean( "isRemember" ) ;
			Object objMd5RandomCode = this.getSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY);
            String md5RandomCode = null;
            if(objMd5RandomCode != null){
                md5RandomCode = objMd5RandomCode.toString();
                this.removeSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY);
            }
            if(!CaptchaRender.validate( md5RandomCode, captchaCode )){
            	renderJson( new ResultObj( ResultObj.FAIL , "验证码错误，请重新输入！" , null ) );
                return;
            }
			
			SysUser.me.userLogin( 
					getPara("loginName"), getPara("password") , isRemember , getSession() );
			
			//添加登录日志
			SysLoginLog.me.addLoginLog( getRequest() , getSession() );
			renderJson( new ResultObj( ResultObj.SUCCESS , null, null ) );
		} catch( BuziException e ) {
			logger.debug(  e.getMessage() );
			renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
		} catch( Exception e ) {
			logger.warn( "系统异常，登录失败！" , e );
			renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
		}
	}
	
	/**
	 * 后台主页
	 */
	public void main() {
		//菜单
		List<TreeNode<SysMenu>> list = SysMenu.me.getMenuTree() ;
        String tree = treeMenu( list,  new StringBuilder() , SecurityUtils.getSubject() , RequestKit.getBasePath(getRequest()) ) ;
        setAttr( "tree", tree ) ;
        setAttr( "userName", getSessionAttr( "userName" ) ) ;
		render("main.html");
	}
	
	/**
	 * 内容
	 */
	public void content() {
		render("content.html");
	}
	
	/**
	 * 退出
	 */
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		if ( currentUser.isAuthenticated() ) {
			currentUser.logout();
		}
		index();
	}
	
	
	public String treeMenu( List<TreeNode<SysMenu>> list , StringBuilder str , Subject currentUser ,String basePath ){    	
    	SysMenu sysMenu = null ;
    	for (TreeNode<SysMenu> node : list){
    		sysMenu = node.get() ;
    		
    		if(currentUser.isPermitted( sysMenu.getStr("permission") )){
				str.append( "<li " ) ;
				if( -1 == sysMenu.getInt( "parentId" ) ) ;
					str.append( "class=\"admin-parent\" " ) ;
				str.append( ">" ) ;
				
				str.append( " <a class=\"am-cf\" " ) ;
				if( node.getChildren().size() > 0 ) {
					str.append( " data-am-collapse=\"{target: '#"+ sysMenu.get("id") +"'}\"" ) ;
					str.append( "><span class=\""+sysMenu.get("icon")+"\"></span> " ) ;
					str.append( sysMenu.get("name") ) ;
					str.append( "<span class=\"am-icon-angle-right am-fr am-margin-right\"></span>" ) ;
					str.append( "</a>" ) ;
					
					str.append("<ul class=\"am-list am-collapse admin-sidebar-sub am-in\" id=\""+ sysMenu.get("id") +"\">") ;
					treeMenu( node.getChildren() , str , currentUser , basePath  );
					str.append("</ul>") ;
					
				} else {
					str.append( " href="+ basePath + sysMenu.get("href") +" target=\"content\"  " ) ;
					str.append( "><span class=\""+sysMenu.get("icon")+"\"></span> " ) ;
					str.append( sysMenu.get("name") ) ;
					str.append( "</a>" ) ;
				}
				str.append( "</li>" ) ;
    		}
		}
		return str.toString() ;
	}
}



