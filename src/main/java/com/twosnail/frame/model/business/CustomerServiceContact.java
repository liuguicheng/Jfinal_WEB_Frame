package com.twosnail.frame.model.business;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.kit.request.RequestKit;
import com.twosnail.frame.model.SysButton;

public class CustomerServiceContact extends Model<SysButton>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final CustomerServiceContact me = new CustomerServiceContact() ; 
	public Page<Record> getList(CustomerServiceContact sdc,int pageNumber, int pageSize) {
		//StringBuffer sb = new StringBuffer(" FROM tb_customer_service_contact a WHERE 1=1 ");
		return Db.paginate(pageNumber, pageSize,"select * from tb_customer_service_contact" , "" );
	}
	public boolean checkUserName(String userName) {
		return me.findFirst( "SELECT id,name FROM tb_customer_service_contact WHERE name=?" , userName) != null ;
	}
	public void addUser(CustomerServiceContact sysUser, HttpServletRequest request, HttpSession session) throws BuziException{
		if( !sysUser.save() ) {
            throw new BuziException( "添加信息失败!" );
        }
	}
}
