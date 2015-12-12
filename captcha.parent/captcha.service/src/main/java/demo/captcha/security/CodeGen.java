package demo.captcha.security;

import java.util.Random;

public class CodeGen {
	
	static char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	/**
	 * 生成随机密码
	 *
	 * @param pwd_len 生成的密码的总长度
	 * @return 密码的字符串
	 */
	public static String genRandomNum(int pwd_len) {
		
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		StringBuffer pwd = new StringBuffer();
		Random r = new Random();
		while (count < pwd_len) {

			// 生成随机数，取绝对值，防止生成负数
			i = Math.abs(r.nextInt(str.length));
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
}
