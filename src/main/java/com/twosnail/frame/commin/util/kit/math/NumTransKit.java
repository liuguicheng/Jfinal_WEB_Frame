package com.twosnail.frame.commin.util.kit.math;

import java.math.BigInteger;


/**
 * 2,10,16,36,62 进制相互转换类
 * @author oscar
 * @date 2013-05-09
 * @param 
 * @return 
 */
public class NumTransKit {

//	public static void main(String[] args) {
//		String num = "78364164084";//"36";//"78364164095";
//		int bit = 36;
//		System.out.println("10进制是：" + NumTransKit.anyToDecimal(NumTransKit.decimalToAny(new BigInteger(num), bit), bit));
//		System.out.println(bit + "进制是：" + NumTransKit.decimalToAny(new BigInteger(num), bit));
//		
//		System.out.println(NumTransKit.anyToDecimal("ZZZZZZN", 36));
//	}

	// 十进制转换中把字符转换为数
	private static int changeDec(char ch) {
		int num = 0;
		if (ch >= 'A' && ch <= 'Z')
			num = ch - 'A' + 10;
		else if (ch >= 'a' && ch <= 'z')
			num = ch - 'a' + 36;
		else
			num = ch - '0';
		return num;
	}

	/**
	 * 任意进制转换为10进制
	 * */ 
	public static BigInteger anyToDecimal(String input, int base) {
		BigInteger Bigtemp = BigInteger.ZERO, temp = BigInteger.ONE;
		int len = input.length();
		for (int i = len - 1; i >= 0; i--) {
			if (i != len - 1)
				temp = temp.multiply(BigInteger.valueOf(base));
			int num = changeDec(input.charAt(i));
			Bigtemp = Bigtemp.add(temp.multiply(BigInteger.valueOf(num)));
		}
	   return Bigtemp;
	}

	// 数字转换为字符
	private static char changToNum(BigInteger temp) {
		int n = temp.intValue();

		if (n >= 10 && n <= 35)
			return (char) (n - 10 + 'A');

		else if (n >= 36 && n <= 61)
			return (char) (n - 36 + 'a');

		else
			return (char) (n + '0');
	}

	/**
	 * 十进制转换为任意进制
	 * */ 
	public static String decimalToAny(BigInteger Bigtemp, int base) {
		String ans = "";
		BigInteger baseI = BigInteger.valueOf(base);
		while (Bigtemp.compareTo(BigInteger.ZERO) != 0) {
			BigInteger temp = Bigtemp.mod(baseI);
			Bigtemp = Bigtemp.divide(baseI);
			char ch = changToNum(temp);
			ans = ch + ans;
		}
		return ans;
	}

	/**
	 * 任意类型转之间互转
	 * 
	 * */
	public static String anyToAny(String input, int scouceBase, int targetBase) {
		return decimalToAny(anyToDecimal(input, scouceBase), targetBase);
	}

}