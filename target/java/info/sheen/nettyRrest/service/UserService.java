package info.sheen.nettyRrest.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

@Path("/userService")
public class UserService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserService() {
	}

	@POST
	@Path("/query")
	@Produces("text/plain")
	public String queryUser(String data) {
		logger.info("request data:" + data);
		JSONObject response = new JSONObject();
		response.put("respCode", "0001");
		response.put("respMsg", "success");
		response.put("data", new JSONObject().put("name", "Tom"));
		logger.info(response.toJSONString());
		return response.toJSONString();
	}

}
