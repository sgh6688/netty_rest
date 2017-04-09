package info.sheen.nettyRrest.server;

import info.sheen.nettyRrest.util.SpringFactory;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.jboss.resteasy.spi.ResteasyDeployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResteasyPlatServer {
	/**
	 * 日志Logger
	 */
	private static Logger logger = LoggerFactory
			.getLogger(ResteasyPlatServer.class);

	private String address;

	private int serverPort = SERVER_PORT_NUM;

	/**
	 * core size
	 */
	private int ioWorkCount = 2;
	/**
	 * 处理任务线程数
	 */
	private int executorThreadCount = 40;
	/**
	 * 请求处理线程数
	 */
	private int maxRequestSize = 10;

	/**
	 * 调用端口号
	 */
	private static final int SERVER_PORT_NUM = 8050;

	/**
	 * 构造函数
	 */
	public ResteasyPlatServer() {
	}

	/**
	 * 初始化
	 */
	public void initialize() {
		logger.info("rest plat server initializing...");
		// Check server address configuration
		if (address == null || address.trim().isEmpty() || serverPort < 0) {
			throw new IllegalArgumentException(
					"Invalid address configuration : " + address
							+ ", serverPort:" + serverPort);
		}

		String[] ipConfigs = address.split(",");

		// Check thread pool configuration
		if (ioWorkCount <= 0 || executorThreadCount <= 0 || maxRequestSize <= 0
				|| ioWorkCount <= 0 || executorThreadCount <= 0
				|| maxRequestSize <= 0) {
			throw new IllegalArgumentException(
					"Invalid address configuration for thread pool");
		}

		// 判断当前机器是批量主机还是备机
		String realIP = ipConfigs[0];
		List<String> ips = getCurrentIPs();
		for (String ip : ips) {
			for (String configIp : ipConfigs) {
				if (configIp.equals(ip)) {
					realIP = configIp;
					break;
				}
			}
		}
		logger.info("realIP=" + realIP);
		logger.info("skyEyePort=" + serverPort);

		NettyServer skyEyeNetty = new NettyServer();
		ResteasyDeployment skyEyeDeployment = new ResteasyDeployment();
		List<Object> skyEyeProviders = new ArrayList<Object>();
		skyEyeProviders.add(SpringFactory.getInstance()
				.getUserInfoInterceptor());
		skyEyeProviders.add(SpringFactory.getInstance().getExceptionHandler());

		skyEyeDeployment.setProviders(skyEyeProviders);

		List<Object> skyEyeServiceResources = new ArrayList<Object>();
		skyEyeServiceResources.add(SpringFactory.getInstance()
				.getUserInfoService());
		skyEyeServiceResources.add(SpringFactory.getInstance()
				.getWechatTestService());

		skyEyeDeployment.setResources(skyEyeServiceResources);
		skyEyeNetty.setDeployment(skyEyeDeployment);
		skyEyeNetty.setExecutorThreadCount(executorThreadCount);
		skyEyeNetty.setIoWorkerCount(ioWorkCount);
		skyEyeNetty.setMaxRequestSize(maxRequestSize);
		skyEyeNetty.setHostname(realIP);
		skyEyeNetty.setPort(serverPort);
		skyEyeNetty.setRootResourcePath("");
		skyEyeNetty.setSecurityDomain(null);
		skyEyeNetty.start();
	}

	/**
	 * 获取本机所有的IP
	 * 
	 * @return 本机ip地址集合
	 */
	private List<String> getCurrentIPs() {
		List<String> ips = new ArrayList<String>();
		try {
			// 获取主机网络接口列表
			Enumeration<NetworkInterface> interfaceList = NetworkInterface
					.getNetworkInterfaces();
			// 检测接口列表是否为空,即使主机没有任何其他网络连接，回环接口(loopback)也应该是存在的
			if (interfaceList == null) {
				logger.info("--没有发现接口--");
			} else {
				while (interfaceList.hasMoreElements()) {
					// 获取并打印每个接口的地址
					NetworkInterface iface = interfaceList.nextElement();
					// 打印接口名称
					logger.info("Interface " + iface.getName() + ";");
					// 获取与接口相关联的地址
					Enumeration<InetAddress> addressList = iface
							.getInetAddresses();
					// 是否为空
					if (!addressList.hasMoreElements()) {
						logger.info("\t(没有这个接口相关的地址)");
					}
					// 列表的迭代，打印出每个地址
					while (addressList.hasMoreElements()) {
						InetAddress address = addressList.nextElement();
						logger.info("\tAddress"
								+ ((address instanceof Inet4Address ? "(v4)"
										: address instanceof Inet6Address ? "v6"
												: "(?)")));
						logger.info("\t:" + address.getHostAddress());
						ips.add(address.getHostAddress());
					}
				}
			}
		} catch (SocketException se) {
			logger.info("获取网络接口错误:" + se.getMessage());
		}
		return ips;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public int getIoWorkCount() {
		return ioWorkCount;
	}

	public void setIoWorkCount(int ioWorkCount) {
		this.ioWorkCount = ioWorkCount;
	}

	public int getExecutorThreadCount() {
		return executorThreadCount;
	}

	public void setExecutorThreadCount(int executorThreadCount) {
		this.executorThreadCount = executorThreadCount;
	}

	public int getMaxRequestSize() {
		return maxRequestSize;
	}

	public void setMaxRequestSize(int maxRequestSize) {
		this.maxRequestSize = maxRequestSize;
	}

}
