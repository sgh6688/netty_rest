package info.sheen.nettyRrest.util;

import info.sheen.nettyRrest.handler.RestExceptionHandler;
import info.sheen.nettyRrest.interceptor.UserInfoInterceptor;
import info.sheen.nettyRrest.server.ResteasyPlatServer;
import info.sheen.nettyRrest.service.UserService;
import info.sheen.nettyRrest.service.WechatTestService;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class SpringFactory {
	/**
	 * 系统执行的context
	 */
	private final ClassPathXmlApplicationContext springAC = new ClassPathXmlApplicationContext(
			"spring-config.xml");
	/**
	 * 工厂实例
	 */
	private static SpringFactory factory = null;

	/**
	 * netty server
	 */
	private ResteasyPlatServer resteasyPlatServer = null;

	private UserInfoInterceptor userInfoInterceptor = null;

	private UserService userService = null;

	private RestExceptionHandler exceptionHandler = null;

	private WechatTestService wechatService = null;

	private SpringFactory() {
		super();
	}

	/**
	 * @return 数据库接口工厂实例
	 */
	public static synchronized SpringFactory getInstance() {
		if (factory == null) {
			factory = new SpringFactory();
		}
		return factory;
	}

	/**
	 * 获取Spring容器中指定名称的bean
	 * 
	 * @param name
	 *            对象名称
	 * @return 对象
	 */
	public static Object getBean(String name) {
		return factory.springAC.getBean(name);
	}

	/**
	 * @return AresOpenPlatServer
	 */
	public ResteasyPlatServer getResteasyPlatServer() {
		if (resteasyPlatServer == null) {
			resteasyPlatServer = (ResteasyPlatServer) springAC
					.getBean("resteasyPlatServer");
		}
		return resteasyPlatServer;
	}

	public UserInfoInterceptor getUserInfoInterceptor() {
		if (userInfoInterceptor == null) {
			userInfoInterceptor = (UserInfoInterceptor) springAC
					.getBean("userInfoInterceptor");
		}

		return userInfoInterceptor;
	}

	public RestExceptionHandler getExceptionHandler() {
		if (exceptionHandler == null) {
			exceptionHandler = (RestExceptionHandler) springAC
					.getBean("exceptionHandler");
		}

		return exceptionHandler;
	}

	public UserService getUserInfoService() {
		if (userService == null) {
			userService = (UserService) springAC.getBean("userService");
		}

		return userService;
	}

	public WechatTestService getWechatTestService() {
		if (wechatService == null)
			wechatService = (WechatTestService) springAC
					.getBean("wechatService");
		return wechatService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setResteasyPlatServer(ResteasyPlatServer resteasyPlatServer) {
		this.resteasyPlatServer = resteasyPlatServer;
	}

	public void setUserInfoInterceptor(UserInfoInterceptor userInfoInterceptor) {
		this.userInfoInterceptor = userInfoInterceptor;
	}

	public void setWechatService(WechatTestService wechatService) {
		this.wechatService = wechatService;
	}

	/**
	 * 获取所有service类
	 * 
	 * public List<Object> searchServiceClasses() { Map<String, AbstractService>
	 * map = factory.springAC .getBeansOfType(AbstractService.class);
	 * List<Object> list = new ArrayList<Object>(); for (AbstractService service
	 * : map.values()) { list.add(service); } return list; }
	 */

}
