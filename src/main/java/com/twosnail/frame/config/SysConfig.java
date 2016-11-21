package com.twosnail.frame.config;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.log.Logger;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.WebAppResourceLoader;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.alibaba.druid.util.JdbcConstants;
import com.alibaba.druid.wall.WallFilter;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.plugin.shiro.ShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.tx.TxByRegex;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.twosnail.frame.interceptor.AuthInterceptor;
import com.twosnail.frame.model.SysButton;
import com.twosnail.frame.model.SysButtonLog;
import com.twosnail.frame.model.SysLoginLog;
import com.twosnail.frame.model.SysMenu;
import com.twosnail.frame.model.SysRole;
import com.twosnail.frame.model.SysRolePermission;
import com.twosnail.frame.model.SysUser;
import com.twosnail.frame.model.business.CustomerServiceContact;
import com.twosnail.frame.commin.util.tools.ShiroExt;
import com.twosnail.frame.ext.ExtRoutes;

/**
 * API引导式配置
 */
public class SysConfig extends JFinalConfig {

	public static final SysConfig me = new SysConfig() ;
	private Logger logger = Logger.getLogger( SysConfig.class ) ;
	private Routes routes;
	/**
	 * 配置常量
	 */
	public void configConstant( Constants me )  {
		logger.debug( "-----------------配置常量 START------------------------" );
		// 加载少量必要配置，随后可用getProperty(...)获取值
		loadPropertyFile("jfinal.properties");
		logger.debug( "加载少量必要配置!" );

		// 加载菜单权限
		//loadPropertyFile("auth.properties");
		me.setDevMode(getPropertyToBoolean("devModel", false));
		logger.debug("加载菜单权限!");

		// Beetl
		me.setMainRenderFactory(new BeetlRenderFactory());

		GroupTemplate gt = BeetlRenderFactory.groupTemplate;
		gt.registerFunctionPackage("so", new ShiroExt());

		//设置根目录
		WebAppResourceLoader loader = (WebAppResourceLoader ) gt.getResourceLoader();
		loader.setRoot("src/main/webapp/view");
//		 String path=PathKit.getWebRootPath();
//		loader.setRoot(path+"/view");
		logger.debug("设置根目录:" + loader.getRoot());

		//全局变量
		Map<String, Object> sharedVars = new HashMap<String, Object>();
		sharedVars.put("rootPath", "http://localhost") ;
//		sharedVars.put("rootPath", "http://112.74.105.229/Jfinal_WEB_Frame") ;
		gt.setSharedVars(sharedVars);

		//error页面
		me.setError404View("error/404.html");
		logger.debug("设置error页面！");
		logger.debug("-----------------配置常量 END------------------------");
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		logger.debug( "-----------------配置路由 START------------------------" );
		this.routes = me;
		me.add( new AdminRoutes() );	// 后台视图地址映射
		me.add( new ExtRoutes() );	// 前台视图地址映射
		
		/*this.routes = me;
		AutoBindRoutes routes = new AutoBindRoutes();
		routes.addExcludeClasses(Controller.class);
		me.add(routes);*/
		logger.debug( "-----------------配置路由 END------------------------" );
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
		logger.debug( "-----------------配置插件 START------------------------" );
		// 配置C3p0数据库连接池插件
		//C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password").trim());
		//me.add(c3p0Plugin);

		//阿里巴巴 数据库连接池插件
		DruidPlugin druidPlugin = new DruidPlugin(getProperty("jdbcUrl"), getProperty("user"), getProperty("password"));
		//dp.addFilter(new StatFilter());
		WallFilter wallFilter = new WallFilter();
		wallFilter.setDbType( JdbcConstants.MYSQL );
		druidPlugin.addFilter(wallFilter);
		me.add(druidPlugin);
		logger.debug("阿里巴巴 数据库连接池插件！");


		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin( druidPlugin );
		// arp.setContainerFactory(new CaseInsensitiveContainerFactory());
		arp.setShowSql(getPropertyToBoolean("showSql", true));
		me.add(arp);
		logger.debug("配置ActiveRecord插件！");

		// 映射表到模型
		arp.addMapping( "sys_user" , SysUser.class ) ;
		arp.addMapping("sys_role", SysRole.class) ;
		arp.addMapping("sys_menu", SysMenu.class) ;
		arp.addMapping( "sys_button" , SysButton.class ) ;
		arp.addMapping( "sys_role_permission" , SysRolePermission.class ) ;
		arp.addMapping( "sys_login_log" , SysLoginLog.class ) ;
		arp.addMapping( "sys_button_log" , SysButtonLog.class ) ;
		arp.addMapping( "tb_customer_service_contact" , CustomerServiceContact.class ) ;
		logger.debug("映射表到模型!");


		// 加载Shiro插件
		me.add(new ShiroPlugin(routes));
		logger.debug("加载Shiro插件!");

		// 缓存插件
		me.add(new EhCachePlugin());
		logger.debug("缓存插件!");

		logger.debug("-----------------配置插件 END------------------------");
	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		logger.debug("-----------------配置全局拦截器 START------------------------");
		me.add(new AuthInterceptor());
		//添加shrio权限管理拦截器
		me.add(new ShiroInterceptor());
		logger.debug("添加shrio权限管理拦截器!");

		//开启事务
		me.add(new TxByRegex("upd.*"));
		me.add(new TxByRegex("add.*"));
		me.add(new TxByRegex("del.*"));
		me.add(new TxByRegex(".*Tx"));
		logger.debug("开启事务!");
		logger.debug("-----------------配置全局拦截器 END--------------------------");
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {
		logger.debug( "-----------------配置处理器 START------------------------" );
		logger.debug( "-----------------配置处理器 END--------------------------" );
	}

	/**
	 * 建议使用 JFinal 手册推荐的方式启动项目
	 * 运行此 main 方法可以启动项目，此main方法可以放置在任意的Class类定义中，不一定要放于此
	 */
	/*public static void main(String[] args) {
		JFinal.start("src/main/webapp", 80, "/", 5);
	}*/
}
