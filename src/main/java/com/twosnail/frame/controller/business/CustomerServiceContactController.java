package com.twosnail.frame.controller.business;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.log.Logger;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.twosnail.frame.commin.exception.BuziException;
import com.twosnail.frame.commin.util.result.ResultObj;
import com.twosnail.frame.interceptor.BtnLogInterceptor;
import com.twosnail.frame.model.business.CustomerServiceContact;

/**
 * 客服通讯录
 * lgc
 */
public class CustomerServiceContactController extends Controller {
	
	private Logger logger = Logger.getLogger(CustomerServiceContactController.class) ;

	@RequiresPermissions("business.CustomerServiceContactController")
	public void index(){
		try {
			Integer pageNum = getParaToInt( "pageNum" ) ;
			Integer numPerPage = getParaToInt( "numPerPage" ) ;
			
	        pageNum = pageNum == null ? 1 : pageNum;
	        numPerPage = ( numPerPage == null || numPerPage == 0 ) ? 5 : numPerPage;
	        CustomerServiceContact csc=new CustomerServiceContact();
	        Page<Record> list = CustomerServiceContact.me.getList(csc, pageNum, numPerPage);
	        setAttr( "list", list );
	        
		} catch (Exception e) {
			this.logger.warn( "客服列表信息，初始化失败！" , e );
		}
        render( "customerServiceContact_list.html" );
	}
	
	/**
     * 添加页面
     */
	@RequiresPermissions("CustomerServiceContactController.addview")
    public void addview(){
		try {
		} catch (Exception e) {
			this.logger.warn( "添加客服，初始化信息失败！" , e );
		}
    	render( "customerServiceContact_dd.html" ) ;
    }
    
    /**
     * 保存页面
     */
	@Before(BtnLogInterceptor.class)
    public void addsave() {
    	
		CustomerServiceContact sysUser = getModel(CustomerServiceContact.class) ;
        String dp = getPara("isDefPassWord");
        int isDefPas = dp == null ? 1 : 0 ;
        sysUser.set( "isDefPassWord" , isDefPas);
        if(isDefPas==0)
        	sysUser.set( "passWord" , "PassWord" );
        if( CustomerServiceContact.me.checkUserName( sysUser.getStr("userName")) )
        	renderJson( new ResultObj( ResultObj.FAIL , "新增失败，用户名存在！" , null ) ) ;
    	try {
    		CustomerServiceContact.me.addUser( sysUser , getRequest() , getSession() );
             renderJson( new ResultObj( ResultObj.SUCCESS , "添加成功！" , null ) ) ;
         } catch( BuziException e ) {
             this.logger.warn( "新增客服失败" , e );
             renderJson( new ResultObj( ResultObj.FAIL , "添加失败"+e.getMessage() , null )) ;
         }
    }
	
	

}
