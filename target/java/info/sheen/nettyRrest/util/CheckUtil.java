package info.sheen.nettyRrest.util;

import java.util.Arrays;

public class CheckUtil {
	private static final String token = "testwechat";

	public static boolean check(String signature, String timestamp, String nonce) {
		if (signature == null || timestamp == null || nonce == null)
			return false;
		String[] array = new String[] { token, timestamp, nonce };
		Arrays.sort(array);
		StringBuilder sb = new StringBuilder();

		for (String str : array) {
			sb.append(str);
		}
		String encodeStr = HashEncodeUtil.encode(sb.toString(),
				HashEncodeUtil.SHA1);
		return encodeStr.equals(signature);
	}
}
