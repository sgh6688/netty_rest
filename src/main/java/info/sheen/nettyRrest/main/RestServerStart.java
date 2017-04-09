package info.sheen.nettyRrest.main;

import info.sheen.nettyRrest.util.SpringFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class RestServerStart {
	/**
	 * 日志Logger
	 */
	private static Logger logger = LoggerFactory
			.getLogger(RestServerStart.class);

	public static void main(String[] args) {
		SpringFactory.getInstance().getResteasyPlatServer().initialize();
		logger.info("start server");
	}
}
