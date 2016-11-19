package com.twosnail.frame.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.tree.TreeList;
import com.twosnail.frame.commin.util.tree.TreeNode;

/**   
 * @Title: SysRole.java
 */

@SuppressWarnings("serial")
public class SysRole extends Model<SysRole>{
	public static final SysRole me = new SysRole() ; 
	
	/**
     * 获取所有角色
     */
    public List<SysRole> getSysRoles(){
    	return me.find( "SELECT a.* FROM sys_role a " ) ;
    }
    
    /**
     * 获取角色列表信息（树结构）
     * @return
     */
    public List<TreeNode<SysRole>> getRoleList(){
    	List<SysRole> sysRole = me.find( "SELECT * FROM sys_role " ) ;
    	List<TreeNode<SysRole>> list =  TreeList.sort( sysRole, new TreeList.SortHandler<SysRole>() {
			public int getId(SysRole t){
				return t.getInt("id");
			}
			public int getParentId(SysRole t){
				return t.getInt("parentId");
			}
    	} );
    	return list ;
    }
    
    /**
	 * 通过id查询角色信息
	 * @param id
	 * @return
	 */
	public SysRole getRoleById( int id ){
		return me.findById( id ) ;
	}
	
	public String getRoleName(  int id  ) {
		return this.getRoleById(id).getStr("roleName") ;
	}
	
	/**
     * 获取所有角色
     */
    public List<SysRole> getSysByUserId( int userId ){
    	return me.find( "SELECT a.* FROM sys_role a LEFT JOIN sys_user b ON a.id = b.roleId WHERE b.userId = ?" , userId ) ;
    }
    
	/**
	 * 添加角色信息
	 * @param sysRole
	 * @throws BuziException
	 */
	public void addRole( SysRole sysRole) throws BuziException {
		sysRole.set( "createTime" , System.currentTimeMillis() );
		if( !sysRole.save() ) {
            throw new BuziException( "添加信息失败!" );
        }
	}
	
	/**
	 * 修改角色信息
	 * @param sysRole
	 * @throws BuziException
	 */
	public void updRole( SysRole sysRole ) throws BuziException {
		if( !sysRole.update() ) {
            throw new BuziException( "修改角色信息失败!" );
        }
	}
	
	/**
     * 修改角色信息状态
     * @param SysRole
     * @throws BuziException
     */
    public void updRoleStasus( SysRole sysRole ) throws BuziException {
    	if(  !sysRole.update() ) {
            throw new BuziException( "修改信息失败" );
        }
    }   
    
    /**
     * 删除 角色
     * @param id
     * @throws BuziException
     */
    public void delRoleTx( int id ) throws BuziException{
       if( !me.deleteById( id ) ) {
           throw new BuziException( "修改信息失败" );
       }
    }
    
    /**
     * 删除该角色所有权限
     * @param id
     */
    public void delPrivilegeByid( long id ) throws BuziException{
    	if( !me.deleteById(id)  ) {
            throw new BuziException( "修改信息失败" );
        }
    }
    
    /**
     * 获取权限
     * @return
     */
    public List<TreeNode<SysMenu>> getPrimession(){
    	List<SysMenu> list = SysMenu.me.getMenuList() ;
    	List<TreeNode<SysMenu>> tree =  TreeList.sort( list, new TreeList.SortHandler<SysMenu>() {
			public int getId(SysMenu t){
				return t.getInt("id");
			}
			public int getParentId(SysMenu t){
				return t.getInt("parentId");
			}
    	});
    	return tree ;
    }
    
    
}
