package info.sheen.nettyRrest.interceptor;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@ServerInterceptor
public class UserInfoInterceptor implements PreProcessInterceptor {

	private static Logger logger = LoggerFactory
			.getLogger(UserInfoInterceptor.class);

	public UserInfoInterceptor() {
	}

	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod method)
			throws Failure, WebApplicationException {
		logger.info("come into interceptor, request method :"
				+ method.getMethod().getName() + ", request path:"
				+ request.getPreprocessedPath());

		// HttpHeaders headers = request.getHttpHeaders();
		// logger.info(headers.getRequestHeader(HttpHeaders.CONTENT_TYPE)
		// .toString());
		// List<String> contentTypes =
		// request.getHttpHeaders().getRequestHeader(
		// HttpHeaders.CONTENT_TYPE);
		// if (contentTypes != null && contentTypes.size() == 1
		// && contentTypes.contains("application/x-www-form-urlencoded")) {
		// logger.info(", formParams" + request.getFormParameters()
		// + ", decodeParams:" + request.getDecodedFormParameters());
		//
		// }

		// BufferedReader reader = new BufferedReader(new nputStreamReader(
		// request.getInputStream()));
		// StringBuilder sb = new StringBuilder();
		// String s;
		// try {
		// while ((s = reader.readLine()) != null) {
		// sb.append(s);
		// }
		// } catch (IOException e) {
		// logger.warn("11111111111", e);
		// }
		// logger.info(sb.toString());
		return null;
	}
}
