package info.sheen.nettyRrest.service;

import info.sheen.nettyRrest.util.CheckUtil;
import info.sheen.nettyRrest.util.XMLParseUtil;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
public class WechatTestService {
	private static Logger logger = LoggerFactory
			.getLogger(WechatTestService.class);

	public final static String wechatAppSecret = "717a6a3ef044a20b9b4ced51fddf4e8d";

	@GET
	@Path("/wx")
	@Produces("text/plain")
	public String test(@QueryParam("signature") String signature,
			@QueryParam("nonce") String nonce,
			@QueryParam("timestamp") String timestamp,
			@QueryParam("echostr") String echostr) {

		logger.info("signature:" + signature + ", timestamp:" + timestamp
				+ ", nonce:" + nonce + ", echostr:" + echostr);
		if (CheckUtil.check(signature, timestamp, nonce)) {

			return echostr;
		}
		return "not valid";
	}

	@POST
	@Path("/wx")
	@Produces("text/plain")
	public String textAdd(String data) {
		logger.info("request xml data:" + data);
		Map<String, String> paramMap = XMLParseUtil.xmlToMap(data);
		logger.info("request xml data to map:" + paramMap);
		return "";
	}
}
