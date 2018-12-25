package com.yonyou.iuap.project.util;

import java.security.MessageDigest;

/**
 * @FileName MD5Util.java
 * @Description: MD5加密 实现32位,16位密码算法,默认字符编码为UTF-8
 *
 * @Date 2010年5月8日 
 * @author Yan Hua
 * @version 1.0
 * 
 */
public class MD5Util {
	
	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * MD5 32位加密
	 * 
	 * @param origin
	 *            字符源
	 * @param charset
	 *            字符编码
	 * @return 32位密文
	 */
	public static String code32(String origin, String charset) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charset == null) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charset)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}

	/**
	 * MD5 16位加密
	 * 
	 * @param origin
	 *            字符源
	 * @param charset
	 *            字符编码
	 * @return 16位密文
	 */
	public static String code16(String origin, String charset) {
		String resultString = code32(origin, charset);
		return resultString.substring(8, 24);
	}
	
}
