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
public class RestClient implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(RestClient.class);
	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json;charset=utf-8";

	public static String httpPostWithJSON(String url, String encoderJson)
			throws Exception {
		// 将JSON进行UTF-8编码,以便传输中文
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
				Thread r = new Thread(new RestClient());
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
	}

	public void run() {
		JSONObject json = new JSONObject();
		JSONObject data = new JSONObject();
		data.put("requestId", "00000000001");
		data.put("userId", "0000000000000000001");
		data.put("Ip", "172.0.0.1");
		data.put("devId", "111112222233333444445555566666");
		data.put("cardNo", "6200000000000000001");
		data.put("mobile", "13700000001");
		json.put("id", "00001");
		json.put("appKey", "sdf235safsaf23fsd");
		json.put("data", data);
		logger.info("request message:" + json.toJSONString());
		try {
			httpPostWithJSON("http://192.168.0.100/userService/query",
					json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
