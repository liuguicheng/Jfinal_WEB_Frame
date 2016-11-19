package com.twosnail.frame.controller;

import java.util.List;

import com.twosnail.frame.commin.util.kit.request.RequestKit;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.twosnail.frame.model.SysButton;
import com.twosnail.frame.model.SysMenu;
import com.twosnail.frame.model.SysRole;
import com.twosnail.frame.model.SysRolePermission;
import com.twosnail.frame.model.SysUser;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.result.ResultObj;
import com.twosnail.frame.commin.util.tree.TreeNode;

/**   
 * @Title: RoleController.java
 */
public class RoleController extends Controller {
	
	private Logger logger = Logger.getLogger(RoleController.class) ;
	
	@RequiresPermissions("RoleController")
	public void index(){
		try {
			List<TreeNode<SysRole>> list = SysRole.me.getRoleList() ;
	        String tree = treeMenu( list,  new StringBuilder() ,  RequestKit.getBasePath(getRequest()) ) ;
	        setAttr( "tree", tree ) ;
	        
		} catch (Exception e) {
			this.logger.warn( "角色列表信息，初始化失败！" , e );
		}
        render( "role_list.html" );
	}
	
	/**
	 * 简介
	 */
	public void intro(){
		render( "role_intro.html" );
	}
	/**
     * 添加页面
     */
	@RequiresPermissions("RoleController.addview")
    public void addview(){
    	
    	int id = getParaToInt( "id" ) ;
        setAttr( "parentId" , id ) ;
        if( id == 0 ) {        	
        } else if( id == -1 ) {
        	setAttr( "parentName" , "超级管理员" ) ;
        } else {
        	setAttr( "parentName" , SysRole.me.getRoleName(id)) ;
        }        
    	render( "role_add.html" ) ;
    }
    
    /**
     * 保存页面
     */
    public void add() {
    	SysRole sysRole = getModel( SysRole.class ) ;
    	try {
            //添加角色信息
    		SysRole.me.addRole( sysRole );
    		renderJson( new ResultObj( ResultObj.SUCCESS , "", null ) ) ;
    		return ;
        } catch( BuziException e ) {
            this.logger.warn( "添加失败！" , e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
        } catch (Exception e) {
        	this.logger.warn( "系统异常！" , e );
        	renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
		}
    }
    
    /**
     * 修改页面
     * @return
     */
    @RequiresPermissions("RoleController.editview")
    public void editview(){        
    	try {
    		setAttr( "sysRole" , SysRole.me.getRoleById( getParaToInt("id") )  ) ;
		} catch (Exception e) {
			this.logger.warn( "修改角色信息，初始化失败！" , e ); 
		}
		render( "role_edit.html" ) ;
    }
    
    /**
     * 修改保存页面
     * @param infoUser
     * @param request
     * @return
     */
    public void edit(){
    	SysRole sysRole = getModel( SysRole.class ) ;
    	try {
    		SysRole.me.updRole(sysRole) ;
        	renderJson( new ResultObj( ResultObj.SUCCESS , "" , null )) ;
        	return ;
        } catch( BuziException e ) {
            this.logger.warn( "修改角色信息失败！" ,e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
            return ;
        } catch (Exception e) {
        	this.logger.warn( "系统异常！" , e );
        	renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
        	return ;
		}
    }
    

    /**
     * 修改状态
     */
    @RequiresPermissions("RoleController.editstatus")
    public void editstatus(){
    	int id  = getParaToInt( "id" ) ;
    	try {
        	SysRole sysRole= SysRole.me.getRoleById( id ) ;
            int isUsed = sysRole.getInt("isUsed") == 1 ? 0 : 1 ;
            sysRole.set( "isUsed" , isUsed );
            SysRole.me.updRoleStasus( sysRole );
            renderJson( new ResultObj( ResultObj.SUCCESS , "" , null )) ;
        } catch ( BuziException e ) {
            this.logger.warn( e.getMessage() , e );
            renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
        } catch (Exception e) {
        	this.logger.warn( "系统异常！" , e );
        	renderJson( new ResultObj( ResultObj.FAIL , e.getMessage() , null )) ;
		}
    }
    
    /**
     * 删除
     */
    @RequiresPermissions("RoleController.delview")
    public void delview(){
    	int id  = getParaToInt( "id" ) ;
    	try {
			if( SysUser.me.checkUserById( id ) ){
	        	try {
	        		SysRole.me.delRoleTx( id );
	        		renderJson( new ResultObj( ResultObj.SUCCESS , "删除成功" , null )) ;
	        	} catch ( BuziException e ) {
	        		this.logger.warn( "删除失败！" , e );
	        		renderJson( new ResultObj( ResultObj.FAIL , "删除失败！" , null )) ;
	        	}
	        }else{
	        	renderJson( new ResultObj( ResultObj.FAIL , "该角色存在角色，不能删除" , null )) ;
	        }
		} catch (Exception e ) {
			this.logger.warn( "系统异常！" , e );
			renderJson( new ResultObj( ResultObj.FAIL , "系统异常！" , null )) ;
		}
    }
    
    /**
     * 用户授权页面
     */
    @RequiresPermissions("RoleController.permissionview")
	public void permissionview(){
		JSONObject result = new JSONObject() ;
		try {
			Integer id  = getParaToInt( "id" ) ; //角色Id
			result.put( "menu" , SysRole.me.getPrimession() ) ;
			List<TreeNode<SysMenu>> list  = SysRole.me.getPrimession() ;
			List<String> permission = SysRolePermission.me.getPermissionByRoleId( id ) ;
			String str = permission( list, permission, new StringBuilder() ,  RequestKit.getBasePath(getRequest())  ) ;
			setAttr( "id" , id ) ;
			setAttr( "permission" , str ) ;
			render( "role_permission.html" );
		} catch (Exception e) {
			this.logger.warn( "获取用户授权信息失败！" , e  );
			return ;
		}
	}
	
	/**
	 * 保存用户权限
	 */
	public void permission() {
		int id = getParaToInt( "id" ) ;
		String[] permis = getParaValues( "permis" ) ;
		try {
			SysRolePermission.me.addPermissions( id, permis );
			renderJson( new ResultObj( ResultObj.SUCCESS , null , null )) ;
		} catch (BuziException e) {
			this.logger.warn( "添加失败！" , e );
			renderJson( new ResultObj( ResultObj.FAIL , "添加失败！" , null )) ;
		} catch (Exception e) {
			this.logger.warn( "系统异常！" , e );
			renderJson( new ResultObj( ResultObj.FAIL , "系统异常！" , null )) ;
		}
	}
    
	/**
	 */
	private String permission( List<TreeNode<SysMenu>> list , List<String> permission , StringBuilder str , String basePath ){    	
		SysMenu menu = null ;
		int id = -1 ;
		List<SysButton> button = null ;
		String checked = null ;
    	for (TreeNode<SysMenu> node : list ){
    		menu = node.get() ;	
    		id = menu.getInt("id") ;
    		button = SysButton.me.getButton(id) ;   		
			str.append( "<tr> " ) ;
			checked = permission.contains(menu.get("permission"))?"checked":"" ;
			if( node.getChildren().size() > 0 ) {
				//根菜单
				str.append( "<td><label class=\"am-checkbox-inline\"><input type=\"checkbox\" "+checked+" value='"+menu.get("permission")+"' name=\"permis\"/><strong>" + menu.get("name") +"</strong></label></td>" ) ;				
				permission( node.getChildren() , permission ,str , basePath );				
			} else {
				str.append( "<td>" ) ;
				if( -1 != menu.getInt( "parentId" ) ){
					str.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label class=\"am-checkbox-inline\"><input type=\"checkbox\" "+checked+" value='"+menu.get("permission")+"' name=\"permis\"/>" ) ;
					str.append( menu.get("name") +"</label></td>" ) ;
				} else{
					//根菜单
					str.append( "<label class=\"am-checkbox-inline\"><input type=\"checkbox\" "+checked+" value='"+menu.get("permission")+"' name=\"permis\"/><strong>" + menu.get("name") +"</strong></label></td>" ) ;
				}
				
				for (SysButton sysButton : button) {
					checked = permission.contains(sysButton.getStr("value"))?"checked":"" ;
					str.append( "<td><label class=\"am-checkbox-inline\"><input type=\"checkbox\" "+checked+" value='"+sysButton.getStr("value")+"' name=\"permis\"/>" + sysButton.getStr("name") +"</label></td>" ) ;
				}
				
			}
			str.append( "</tr>" ) ;
		}
		return str.toString() ;
	}
	
	public String treeMenu( List<TreeNode<SysRole>> list , StringBuilder str , String basePath ){    	
    	SysRole sysRole = null ;
    	for (TreeNode<SysRole> node : list){
			sysRole = node.get() ;	
			str.append( "<li " ) ;
			if( -1 == sysRole.getInt( "parentId" ) ) ;
				str.append( "class=\"admin-parent\" " ) ;
			str.append( ">" ) ;
			
			str.append( " <a class=\"am-cf\" onclick=\"setIdValue("+sysRole.get("id")+");\"  " ) ;
			str.append( " href="+ basePath + "/sys/role/editview?id=" +sysRole.get("id") +" target=\"content_in\"  " ) ;
			if( node.getChildren().size() > 0 ) {
				
				str.append( " data-am-collapse=\"{target: '#"+ sysRole.get("id") +"'}\"" ) ;
				
				str.append( "><span class=\"am-icon-users\"></span> " ) ;
				str.append( sysRole.get("roleName") ) ;
				str.append( "<span class=\"am-icon-angle-right am-fr am-margin-right\"></span>" ) ;
				str.append( "</a>" ) ;
				
				str.append("<ul class=\"am-list am-collapse admin-sidebar-sub am-in\" id=\""+ sysRole.get("id") +"\">") ;
				treeMenu( node.getChildren() , str , basePath );
				str.append("</ul>") ;
				
			} else {
				str.append( "><span class=\"am-icon-user-secret\"></span> " ) ;
				str.append( sysRole.get("roleName") ) ;
				str.append( "</a>" ) ;
			}
			str.append( "</li>" ) ;
		}
		return str.toString() ;
	}
	
	
}
