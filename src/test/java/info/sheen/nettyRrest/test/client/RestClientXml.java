package info.sheen.nettyRrest.test.client;

/**
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("all")
public class RestClientXml implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(RestClientXml.class);
	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json;charset=utf-8";

	public static String httpPostWithJSON(String url, String encoderJson)
			throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
		// httpPost.addHeader(HTTP.CONTENT_TYPE,
		// "application/x-www-form-urlencoded");
		httpPost.addHeader("CONNECTION", HTTP.CONN_CLOSE);
		StringEntity se = new StringEntity(encoderJson);
		httpPost.setEntity(se);

		HttpResponse response = httpClient.execute(httpPost);

		HttpEntity entity = response.getEntity();
		InputStream content = entity.getContent();

		JSONObject json = toJson(content);
		String result = json.toJSONString();
		byte[] bytes = result.getBytes();
		int length = bytes.length;
		logger.info("response json string:" + result);
		return json.toJSONString();
	}

	private static JSONObject toJson(InputStream in) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(in, "GB2312"));
			StringBuilder sb = new StringBuilder();
			String line;

			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			String string = sb.toString();
			logger.info("response message:" + string);
			if (string != null && !string.equals("")) {
				return JSON.parseObject(string);
			} else {
				return new JSONObject();
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return new JSONObject();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		try {
			for (int count = 0; count < 1; count++) {
				Thread r = new Thread(new RestClientXml());
				r.start();
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String substring(String text, int length, String encode)
			throws UnsupportedEncodingException {
		if (text == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int currentLength = 0;
		for (char c : text.toCharArray()) {
			currentLength += String.valueOf(c).getBytes(encode).length;
			if (currentLength <= length) {
				sb.append(c);
			} else {
				break;
			}
		}
		return sb.toString();
	}// 获取XML

	public String getXMLString() {
		String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
		StringBuffer sb = new StringBuffer();
		sb.append(XML_HEADER);
		sb.append("<a>");
		sb.append("<b>");
		sb.append("<c>");
		sb.append("DWMC");
		sb.append("</c>");
		sb.append("<d>");
		sb.append("id=10");
		sb.append("</d>");
		// sb.append("</SELECT>");
		sb.append("</b>");
		sb.append("</a>");
		// 返回String格式
		return sb.toString();
	}

	// 使用POST

	public void run() {
		logger.info("request message:" + this.getXMLString());
		try {
			httpPostWithJSON("http://192.168.0.100/wx",
					this.getXMLString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
