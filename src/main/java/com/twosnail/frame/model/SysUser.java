package com.twosnail.frame.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.frame.commin.util.kit.request.RequestKit;
import com.twosnail.frame.commin.exception.BuziException;

/**   
 * @Title: SysUser.java
 */

@SuppressWarnings("serial")
public class SysUser extends Model<SysUser>{
	
	private Logger logger = Logger.getLogger( SysUser.class ) ;
	public static final SysUser me = new SysUser() ; 
	public static final int STATUS_NOMAL = 1;    
	public static final int STATUS_FREEZE = 0;
	
	/**
	 * 处理用户登录
	 * @param loginName
	 * @param password
	 * @param session
	 * @throws BuziException  
	 */
	public void userLogin( 	String userName, String passWord, Boolean rm , HttpSession session ) 
			throws BuziException {
		
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken( userName, passWord );
		token.setRememberMe( rm );
		try {
			currentUser.login(token);
			SysUser user = me.findFirst( 
					"SELECT a.id,a.roleId,a.userName,a.isUsed FROM sys_user a  WHERE a.userName=?" , userName ) ;
			
			if( user.getInt( "isUsed" ) != SysUser.STATUS_NOMAL ) {
				//管理员被冻结
				throw new BuziException("账号 [" +userName + "]已经冻结.") ;
			}
			session.setAttribute( "userId" , user.get("id"));
			session.setAttribute( "userName" , user.get("userName"));
			
		} catch (UnknownAccountException e) {
			throw new BuziException("未知账号！") ;
		} catch (IncorrectCredentialsException e) {
			throw new BuziException("密码错误！") ;
		} catch (LockedAccountException e) {
			throw new BuziException("账号已经冻结！") ;
		} catch (Exception e) {
			this.logger.warn( "登录失败，系统异常！" , e );
			throw new BuziException("登录失败，系统异常！") ;
		}
	}
	
	/**
	 * 获取用户信息
	 * @param id
	 * @param keyWord
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<Record> getUser( Integer roleId ,String keyWord , int pageNumber, int pageSize) {
		StringBuffer sb = new StringBuffer(" FROM sys_user a WHERE 1=1 ");
		if( keyWord != null && "".equals( keyWord = keyWord.trim() ) ) {
			sb.append( " AND (a.userName LIKE '%"+keyWord+"%' or a.id LIKE '%"+keyWord+"%')" ) ;
		}
		if( roleId != null && roleId != -1 ) {
			sb.append( " AND a.roleId = " + roleId ) ;
		}
		return Db.paginate(pageNumber, pageSize, 
				"SELECT a.*,(select a1.roleName from sys_role a1 WHERE a1.id = a.roleId) roleName" , sb.toString() );
			
	}

	/**
	 * 判断用户名是否存在
	 * @param userName
	 * @return  true-->存在
	 */
	public boolean checkUserName(String userName){
		return me.findFirst( "SELECT id,userName FROM sys_user WHERE userName=?" , userName) != null ;
	}
	
	/**
	 * 检查用户名的个数
	 * @param userName
	 */
	public int checkUserNameCount( String userName ){
		return Db.queryInt("SELECT COUNT(1) FROM sys_user a WHERE a.userName = ?" , userName );
	}
	
	/**
     * 查看该角色下是否存在用户
     * @param id
     * @return true 不存在
     * @throws BuziException
     */
    public boolean checkUserById( int id ){
    	return Db.queryColumn("SELECT id FROM sys_user a WHERE a.roleId = ?" , id ) == null;
    }
    
	
	/**
	 * 添加用户信息
	 * @param sysUser
	 * @throws BuziException
	 */
	public void addUser( SysUser sysUser , HttpServletRequest request , HttpSession session ) throws BuziException{
		sysUser.set( "createTime" ,System.currentTimeMillis() );
        sysUser.set( "createId" ,session.getAttribute("userId") ) ;
        sysUser.set( "createIp" , RequestKit.getIpAddr(request)) ;
		if( !sysUser.save() ) {
            throw new BuziException( "添加信息失败!" );
        }
	}
	
	/**
	 * 通过id查询用户信息
	 * @param id
	 * @return
	 */
	public SysUser getUserById( long id){
		return me.findById(id) ;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Record getUserInfoById( long id ){
		return Db.findFirst( "SELECT u.* , r.roleName FROM `sys_user` u LEFT JOIN sys_role r ON u.roleId=r.id where u.`id` = ? " , id ) ;
	}
	/**
	 * 修改用户信息
	 * @param user
	 * @param request
	 * @throws BuziException
	 */
	public void updUser( 
			SysUser user , HttpServletRequest request  , HttpSession session ) throws BuziException{
		user.set( "operateId" , session.getAttribute("userId") ) ;
		user.set( "opetateTime" , System.currentTimeMillis() ) ;
		if( !user.update() ) {
            throw new BuziException( "添加信息失败!" );
        }
	}
	
	/**
	 * 修改用户状态
	 * @param id
	 * @param isUsed
	 * @throws BuziException
	 */
    public void updUserStasus( long id , int isUsed ) throws BuziException{
    	me.set( "id", id );
    	me.set( "isUsed", isUsed );
		if( !me.update() ) {
            throw new BuziException( "修改用户状态失败!" );
        };
    }
    
    /**
     * 删除用户
     * @param ids
     * @throws Exception
     */
    public void delUser( String ids ) throws Exception{
    	Db.update( "delete from `sys_user` where `id` in (?)" , ids ) ;
    }
    
}
