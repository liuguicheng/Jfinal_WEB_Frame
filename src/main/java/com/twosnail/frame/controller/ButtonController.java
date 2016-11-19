package com.twosnail.frame.controller;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.twosnail.frame.model.SysButton;
import com.twosnail.frame.model.SysRole;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.result.ResultObj;

/**   
 * @Title: ButtonController.java
 */
public class ButtonController extends Controller {
	
	private Logger logger = Logger.getLogger(ButtonController.class) ;
	
	public void index(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			int menuId = getParaToInt("menuId") ;
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 5 : numPerPage;
	        
	        Page<SysButton> list = SysButton.me.getButton( menuId , pageNum, numPerPage );
	        setAttr( "list", list );
	        setAttr( "menuId", menuId );
	        
		} catch (Exception e) {
			this.logger.warn( "功能按钮列表信息，初始化失败！" , e );
		}
        render( "btn_list.html" );
	}
	
	/**
     * 添加页面
     */
    public void addview(){
    	render( "user_add.html" ) ;
    }
    
    /**
     * 添加页面
     */
    public void addinit(){
    	
        JSONObject result = new JSONObject() ;
        try {
        	List<SysRole> roles = SysRole.me.getSysRoles();
        	result.put( "roles" ,  roles ) ;
        	renderJson(new ResultObj( ResultObj.SUCCESS , "", result )) ;
		} catch (Exception e) {
			this.logger.warn( "添加功能按钮信息初始化失败！" , e );
			renderJson(new ResultObj( ResultObj.FAIL , e.getMessage() , null ))  ;
		}
        
    }
    
    /**
     * 保存页面
     */
    public void addsave() {
    	
    	SysButton sysButton = getModel(SysButton.class) ;
        String dp = getPara("isDefPassWord");
        int isDefPas = dp == null ? 1 : 0 ;
        sysButton.set( "isDefPassWord" , isDefPas);
        if(isDefPas==0)
        	sysButton.set( "passWord" , "PassWord" );
        if( sysButton == null || sysButton.getInt("roleId") < 0 )
        	renderJson( new ResultObj( ResultObj.FAIL , "角色不能为空！" , null )) ;
        if( SysButton.me.checkButtonName( sysButton.getStr("userName")) )
        	renderJson( new ResultObj( ResultObj.FAIL , "新增失败，功能按钮名存在！" , null ) ) ;
    	try {
             SysButton.me.addButton( sysButton );
             renderJson( new ResultObj( ResultObj.SUCCESS , "添加成功！" , null ) ) ;
         } catch( BuziException e ) {
             this.logger.warn( "新增功能按钮失败" , e );
             renderJson( new ResultObj( ResultObj.FAIL , "添加失败"+e.getMessage() , null )) ;
         }
    }
    
    /**
     * 修改页面
     * @return
     */
    public void editview(){
        
        try {
        	List<SysRole> roles = SysRole.me.getSysRoles();
            SysButton sysButton = SysButton.me.getButtonById(getParaToInt("id")) ;        
            setAttr( "sysButton" , sysButton ) ;
            setAttr( "roles" ,  roles ) ;
		} catch (Exception e) {
			this.logger.warn( "修改信息，初始化信息失败！" , e );
		}
		render( "user_edit.html" ) ;
    }
    
    /**
     * 修改保存页面
     * @param sysButton
     * @param request
     * @return
     */
    public void editsave(){
    	
        String message = "" ;
        SysButton sysButton = getModel(SysButton.class ) ;
        try {
        	//角色编号不能为空
            if( sysButton == null || sysButton.get("") == null ){
            	renderJson(  new ResultObj( ResultObj.FAIL , "功能按钮名不能为空！" , null ) );
            }
            SysButton.me.updButton( sysButton , getRequest() ) ;
            renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) ) ;
            return ;
        } catch( BuziException e ) {
            this.logger.warn( "保存功能按钮信息失败！"  , e );
            message = "保存功能按钮信息失败！";
        } catch (Exception e) {
        	this.logger.warn( "保存功能按钮信息,系统错误！"  , e );
        	message = "保存功能按钮信息,系统错误！" ;
		}
        renderJson( new ResultObj( ResultObj.FAIL , message , null ) ) ;
    }
    
    /**
     * 删除
     * @return
     */
    public void delete(){
    	String id  = getPara( "id" ) ;
        try {
        	if( id == null || "".equals( id ) ) {
        		renderJson( new ResultObj( ResultObj.FAIL , "参数Ids不能为空！" , null ) );
        	} else {
        		String[] ids =  id.split(",") ;
        		SysButton.me.delButtonTx(  ids );
        	}            
        	renderJson( new ResultObj( ResultObj.SUCCESS , null , null ) );
        } catch ( BuziException e ) {
            this.logger.info( "删除失败！", e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null ) );
        }
    }
    
	
	
}
