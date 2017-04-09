package info.sheen.nettyRrest.handler;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.HttpResponseCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

@Provider
public class RestExceptionHandler implements ExceptionMapper<Exception> {

	/**
	 * 日志Logger
	 */
	private static Logger logger = LoggerFactory
			.getLogger(RestExceptionHandler.class);

	public Response toResponse(Exception e) {
		logger.error("*******handler exception***********", e);
		JSONObject execeptionRes = new JSONObject();
		execeptionRes.put("respCode", "9999");
		execeptionRes.put("respMsg", "fail for server internal error!");
		ServerResponse response = new ServerResponse();
		response.setStatus(HttpResponseCodes.SC_OK);
		MultivaluedMap<String, Object> headers = new Headers<Object>();
		headers.add("Content-Type", "text/plain;charset=GB18030");
		response.setMetadata(headers);
		response.setEntity(execeptionRes.toJSONString());
		return response;
	}
}
