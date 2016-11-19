package com.twosnail.frame.interceptor;

import java.util.HashSet;
import java.util.Set;

import com.jfinal.aop.Interceptor;
import com.jfinal.core.ActionInvocation;
import com.jfinal.core.Controller;
import com.twosnail.frame.model.SysButtonLog;

/**
 * 操作日志 拦截器
 */
public class BtnLogInterceptor implements Interceptor {

	private static final Set<String> excludedActionKeys = new HashSet<String>();

	public static void addExcludedActionKey(String actionKey) {
		excludedActionKeys.add(actionKey);
	}

	@Override
	public void intercept(ActionInvocation ai) {
		if (!excludedActionKeys.contains(ai.getActionKey())) {
			// 这里写拦截器相应的处理逻辑
			String method = ai.getMethod().getName();
			String controllerclassname = ai.getController().getClass().getName();
			String view = ai.getViewPath();
			Controller c = ai.getController();
			SysButtonLog.me.addButtonLog(c.getRequest(), c.getSession(), controllerclassname, method, view, "", "");
		}
		ai.invoke();
	}

}
