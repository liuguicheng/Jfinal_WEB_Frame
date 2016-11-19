package com.twosnail.frame.config;

import com.jfinal.config.Routes;
import com.twosnail.frame.controller.BtnLogController;
import com.twosnail.frame.controller.ButtonController;
import com.twosnail.frame.controller.IndexController;
import com.twosnail.frame.controller.LoginLogController;
import com.twosnail.frame.controller.MenuController;
import com.twosnail.frame.controller.RoleController;
import com.twosnail.frame.controller.UserController;
import com.twosnail.frame.controller.business.CustomerServiceContactController;
import com.twosnail.frame.model.business.CustomerServiceContact;

public class AdminRoutes extends Routes {

	@Override
	public void config() {
		add( "/" , IndexController.class  ) ;
		add( "/sys/user" , UserController.class  ) ;
		add( "/sys/role" , RoleController.class  ) ;
		add( "/sys/menu" , MenuController.class  ) ;
		add( "/sys/btn" , ButtonController.class  ) ;
		add( "/sys/log/login" , LoginLogController.class  ) ;
		add( "/sys/log/btn" , BtnLogController.class  ) ;
		add( "/business/customerservicecontact" , CustomerServiceContactController.class  ) ;
	}

}
