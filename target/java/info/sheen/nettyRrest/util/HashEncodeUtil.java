package info.sheen.nettyRrest.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shenguanghui
 * 
 */
public class HashEncodeUtil {
	private static Logger logger = LoggerFactory
			.getLogger(HashEncodeUtil.class);

	public static final String MD5 = "MD5";
	public static final String SHA1 = "SHA-1";
	public static final String SHA256 = "SHA-256";

	/**
	 * @param original
	 *            源字符串
	 * @param algorithm
	 *            加密算法
	 * @return
	 * @throws Exception
	 */
	public static String encode(String original, String algorithm) {
		if (null == original) {
			return null;
		}
		switch (algorithm) {
		case SHA256:
			return SHA256Encrypt(original);
		case SHA1:
			return SHA1Encrypt(original);
		case MD5:
			return MD5Encrypt(original);
		default:
			logger.warn("Illegal encode algorithm:" + algorithm
					+ ", return source str:" + original);
			return original;
		}

	}

	/**
	 * 544e28002c24c8d00af1392c3f17d8b62d1e9b41
	 * 
	 * @param original
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static String SHA1Encrypt(String original) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(SHA1);
		} catch (NoSuchAlgorithmException e) {
			logger.warn("Illegal encode algorithm:" + SHA1, e);
		}
		if (null != digest) {
			digest.update(original.getBytes());
			byte[] digestRes = digest.digest();
			String digestStr = getDigestStr(digestRes);
			return digestStr;
		}
		return null;
	}

	/**
	 * @param orignal
	 * @return
	 * @throws Exception
	 */
	private static String SHA256Encrypt(String original) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(SHA256);
		} catch (NoSuchAlgorithmException e) {
			logger.warn("Illegal encode algorithm:" + SHA256, e);
		}
		if (null != digest) {
			digest.update(original.getBytes());
			byte[] digestRes = digest.digest();
			String digestStr = getDigestStr(digestRes);
			return digestStr;
		}
		return null;
	}

	/**
	 * @param orignal
	 * @return
	 * @throws Exception
	 */
	private static String MD5Encrypt(String original) {
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException e) {
			logger.warn("Illegal encode algorithm:" + MD5, e);
		}
		if (null != digest) {
			digest.update(original.getBytes());
			byte[] digestRes = digest.digest();
			String digestStr = getDigestStr(digestRes);
			return digestStr;
		}
		return null;
	}

	private static String getDigestStr(byte[] origBytes) {
		String tempStr = null;
		StringBuilder stb = new StringBuilder();
		for (int i = 0; i < origBytes.length; i++) {
			tempStr = Integer.toHexString(origBytes[i] & 0xff);
			if (tempStr.length() == 1) {
				stb.append("0");
			}
			stb.append(tempStr);

		}
		return stb.toString();
	}

	public static void main(String[] args) throws Exception {

		// String a = "";
		// String b = null;
		// String c = "   ";
		//
		// Validate.notEmpty(a);
		// Validate.notEmpty(b);
		// Validate.notEmpty(c);
		//
		// String src = "test" + new Random().nextFloat() + "eee";
		// String des = null;
		// try {
		// des = encrypt(src, " tes ");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// System.out.println(des);
		String luaScriptStr = "if(tonumber(redis.call('LLEN',KEYS[1]))>=tonumber(ARGV[1])) then redis.call('lpop', KEYS[1]) end  redis.call('rpush',KEYS[1],ARGV[2]) return 'OK'";
		String res = null;
		for (int i = 0; i < 100000000; i++) {
			res = encode(luaScriptStr, MD5);
		}
		System.out.println(res);
	}

}
