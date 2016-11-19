package com.twosnail.frame.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.twosnail.frame.commin.exception.BuziException;

/**   
 * @Title: SysRolePermission.java
 */

@SuppressWarnings("serial")
public class SysRolePermission extends Model<SysRolePermission>{
	public static final SysRolePermission me = new SysRolePermission() ; 
	
	
	/**
	 * 获取用户权限
	 * @param id
	 * @return
	 */
	public List<String> getPermissionByUserId( int id ) {
		return Db.query( "SELECT rp.permission FROM sys_role_permission AS rp WHERE rp.roleId = (SELECT roleId FROM sys_user WHERE id = ? ) " , id ) ;
	}
	
	/**
     * 获取角色权限
     * @param roleId
     */
    public List<String> getPermissionByRoleId( int roleId ){
    	return Db.query( "SELECT rp.permission FROM sys_role_permission AS rp WHERE rp.roleId = ? " , roleId ) ;
    }
    
    /**
     * 添加权限信息
     * @param roleId
     * @param permis
     * @throws BuziException
     */
    public void addPermissions( int roleId , String[] permis ) throws BuziException{
    	//清空之前数据
    	Db.update( "DELETE FROM sys_role_permission WHERE roleId=?" , roleId ) ;
    	SysRolePermission rolePermission = null ;
    	for (String string : permis) {
    		rolePermission = new SysRolePermission() ;
    		rolePermission.set( "roleId" , roleId ) ;
    		rolePermission.set( "permission" , string ) ;
    		addPermission(rolePermission);
		}
    }
    
    
	/**
	 * 保存角色权限信息
	 * @param rolePermission
	 */
	public void addPermission( SysRolePermission rolePermission ) throws BuziException {
		if( !rolePermission.save() ) {
			throw new BuziException( "保存角色权限信息失败" );
		}
	}
	
	
	
}
