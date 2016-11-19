package com.twosnail.frame.interceptor;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;

/**   
 * @Title: AuthInterceptor.java
 */
public class AuthInterceptor implements Interceptor{

	
	@Override
	public void intercept(ActionInvocation ai) {
		
		Controller controller = ai.getController();
		
		Subject subject = SecurityUtils.getSubject() ;
		if( subject != null && subject.isAuthenticated()) {
			ai.invoke();
		} else {
			//重新定向
			controller.redirect( "/login" );
		}
	}

}
