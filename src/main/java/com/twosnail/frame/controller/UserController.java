package com.twosnail.frame.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.frame.model.SysRole;
import com.twosnail.frame.model.SysUser;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.result.ResultObj;
import com.twosnail.frame.interceptor.BtnLogInterceptor;

/**   
 * @Title: UserController.java
 * 添加操作日志@Before(BtnLogInterceptor.class)
 */
public class UserController extends Controller {
	
	private Logger logger = Logger.getLogger(UserController.class) ;
	
	@RequiresPermissions("UserController")
	public void index(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			String keyWord = getPara("keyWord") ;
			Integer roleId = getParaToInt("roleId") ;
			
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 5 : numPerPage;
	        
	        Page<Record> list = SysUser.me.getUser( roleId , getPara("keyWord") , pageNum, numPerPage );
	        
	        setAttr( "list", list );
	        setAttr( "roles", SysRole.me.getSysRoles() );
	        setAttr( "keyWord", keyWord );
	        setAttr( "roleId", roleId );
	        
		} catch (Exception e) {
			this.logger.warn( "用户列表信息，初始化失败！" , e );
		}
        render( "user_list.html" );
	}
	
	
	/**
     * 添加页面
     */
	@RequiresPermissions("UserController.addview")
    public void addview(){
		try {
			setAttr( "roles" ,  SysRole.me.getSysRoles() ) ;
		} catch (Exception e) {
			this.logger.warn( "添加用户，初始化信息失败！" , e );
		}
    	render( "user_add.html" ) ;
    }
    
    /**
     * 保存页面
     */
	@Before(BtnLogInterceptor.class)
    public void addsave() {
    	
    	SysUser sysUser = getModel(SysUser.class) ;
        String dp = getPara("isDefPassWord");
        int isDefPas = dp == null ? 1 : 0 ;
        sysUser.set( "isDefPassWord" , isDefPas);
        if(isDefPas==0)
        	sysUser.set( "passWord" , "PassWord" );
        if( sysUser == null || sysUser.getInt("roleId") < 0 )
        	renderJson( new ResultObj( ResultObj.FAIL , "角色不能为空！" , null )) ;
        if( SysUser.me.checkUserName( sysUser.getStr("userName")) )
        	renderJson( new ResultObj( ResultObj.FAIL , "新增失败，用户名存在！" , null ) ) ;
    	try {
             SysUser.me.addUser( sysUser , getRequest() , getSession() );
             renderJson( new ResultObj( ResultObj.SUCCESS , "添加成功！" , null ) ) ;
         } catch( BuziException e ) {
             this.logger.warn( "新增用户失败" , e );
             renderJson( new ResultObj( ResultObj.FAIL , "添加失败"+e.getMessage() , null )) ;
         }
    }
    
    /**
     * 修改页面
     */
    @RequiresPermissions("UserController.editview")
    public void editview(){
        try {
            setAttr( "sysUser" , SysUser.me.getUserById( getParaToInt("id") ) ) ;
            setAttr( "roles" ,  SysRole.me.getSysRoles() ) ;
		} catch (Exception e) {
			this.logger.warn( "修改信息，初始化信息失败！" , e );
		}
		render( "user_edit.html" ) ;
    }
    
    /**
     * 修改保存页面
     * @return
     */
    @Before(BtnLogInterceptor.class)
    public void editsave(){
        String message = "" ;
        SysUser sysUser = getModel(SysUser.class ) ;
        try {
        	//角色编号不能为空
            if( sysUser == null || sysUser.get("") == null ){
            	renderJson(  new ResultObj( ResultObj.FAIL , "用户名不能为空！" , null ) );
            }
            SysUser.me.updUser( sysUser , getRequest() , getSession() ) ;
            renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) ) ;
            return ;
        } catch( BuziException e ) {
            this.logger.warn( "保存用户信息失败！"  , e );
            message = "保存用户信息失败！";
        } catch (Exception e) {
        	this.logger.warn( "保存用户信息,系统错误！"  , e );
        	message = "保存用户信息,系统错误！" ;
		}
        renderJson( new ResultObj( ResultObj.FAIL , message , null ) ) ;
    }
    

    /**
     * 修改状态
     */
    @Before(BtnLogInterceptor.class)
    @RequiresPermissions("UserController.upstatus")
    public void upstatus(){
        try {
            SysUser.me.updUserStasus( getParaToLong("id") , getParaToInt("isUsed") );
            renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
            return ;
        } catch ( BuziException e ) {
            this.logger.warn( "修改状态失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
            return ;
        } catch ( Exception e ) {
            this.logger.warn( "系统异常，状态失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
            return ;
        }
    }
    
    /**
     * 删除
     * @return
     */
    @Before(BtnLogInterceptor.class)
    @RequiresPermissions("UserController.delete")
    public void delete(){
    	String id  = getPara( "id" ) ;
        try {
        	if( id == null || "".equals( id ) ) {
        		renderJson( new ResultObj( ResultObj.FAIL , "参数Ids不能为空！" , null ) );
        	} else {
        		SysUser.me.delUser(  id );
        	}
        	renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
        } catch ( Exception e ) {
            this.logger.info( "系统异常，删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
        }
    }
    
    /**
     * 用户信息
     */
	public void info(){		
		try {
	        setAttr( "sysUser" , SysUser.me.getUserInfoById( getParaToInt("id") ) ) ;
		} catch (Exception e) {
			this.logger.warn( "添加信息，初始化失败！" , e );
		}
        render( "user_info.html" );
    }
	
}
