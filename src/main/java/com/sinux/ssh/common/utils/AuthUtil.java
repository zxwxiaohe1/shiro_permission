package com.sinux.ssh.common.utils;

import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 * 
 * @author liliangdong
 *
 */
public class AuthUtil {
	public static String encryptByMd5New(String originString) {
		String result = "";
		if (originString != null) {
			try {
				// 指定加密的方式为MD5
				MessageDigest md = MessageDigest.getInstance("MD5");
				// 进行加密运算
				byte bytes[] = md.digest(originString.getBytes("UTF8"));
				for (int i = 0; i < bytes.length; i++) {
					// 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
					String str = Integer.toHexString(bytes[i] & 0xFF);
					if (str.length() == 1) {
						str += "F";
					}
					result += str;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public static String encryptByMd5(String originString) {
        return DigestUtils.md5Hex(originString).toLowerCase();
    }
	
//	public static void main(String[] args) {
//		//System.out.println(AuthUtil.encryptByMd5New("123456"));
//		System.out.println(AuthUtil.encryptByMd5("231432431"));
//	}
	
}
