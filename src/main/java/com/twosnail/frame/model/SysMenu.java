package com.twosnail.frame.model;

import java.util.List;

import com.jfinal.plugin.activerecord.Model;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.tree.TreeList;
import com.twosnail.frame.commin.util.tree.TreeNode;

/**   
 * @Title: SysMenu.java
 */
@SuppressWarnings("serial")
public class SysMenu extends Model<SysMenu> {
	public static final SysMenu me = new SysMenu() ; 
	
	/**
     * 获取菜单列表信息Tree
     * @return
     */
    public List<TreeNode<SysMenu>> getMenuTree(){
    	List<SysMenu> menu = this.getMenuList() ;
    	List<TreeNode<SysMenu>> tree =  TreeList.sort( menu, new TreeList.SortHandler<SysMenu>() {
			public int getId(SysMenu t){
				return t.getInt("id");
			}
			public int getParentId(SysMenu t){
				return t.getInt("parentId");
			}
    	} );
    	return tree ;
    }
    
    /**
     * 获取菜单列表信息List
     * @return
     */
    public List<SysMenu> getMenuList(){
    	return me.find( "SELECT * FROM sys_menu order by sortNo" ) ;
    }
    
    /**
	 * 通过id查询菜单信息
	 * @param id
	 * @return
	 */
	public SysMenu getMenuById( int id ){
		return me.findById( id ) ;
	}
	
	public String getMenuName(  int id  ) {
		if( id == -1 ) {
			return null ;
		}
		return this.getMenuById(id).getStr("name") ;
	}
	
	/**
	 * 添加菜单信息
	 * @param SysMenu
	 * @throws BuziException
	 */
	public int addMenu( SysMenu menu) throws BuziException {
		menu.set( "createTime" , System.currentTimeMillis() );
		if( !menu.save() ) {
            throw new BuziException( "添加信息失败!" );
        }
		return menu.getInt("id") ;
	}
    
	/**
	 * 修改菜单信息
	 * @param SysMenu
	 * @throws BuziException
	 */
	public void updMenu( SysMenu menu ) throws BuziException {
		if( !menu.update() ) {
            throw new BuziException( "修改菜单信息失败!" );
        }
	}
	
	/**
     * 修改菜单信息状态
     * @param SysMenu
     * @throws BuziException
     */
    public void updMenuStasus( SysMenu menu ) throws BuziException {
    	if(  !menu.update() ) {
            throw new BuziException( "修改信息失败" );
        }
    }   
    
    /**
     * 删除 菜单
     * @param id
     * @throws BuziException
     */
    public void delMenuTx( int id ) throws BuziException{
       if( !me.deleteById( id ) ) {
           throw new BuziException( "修改信息失败" );
       }
    }
	
}
