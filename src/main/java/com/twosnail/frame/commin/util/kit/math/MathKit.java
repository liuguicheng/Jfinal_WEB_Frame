/**
 * 
 */
package com.twosnail.frame.commin.util.kit.math;

import java.math.BigDecimal;

/**
 * @author HUSL
 *
 */
public class MathKit {

	/**
	 * 调整bigdecimal精度
	 * @param i 原数据
	 * @param scale	调整后的精度
	 * @return
	 */
	public static BigDecimal adjustNumPlaces(BigDecimal i, int scale){
		BigDecimal b = new BigDecimal(i.doubleValue());
		b = b.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return b;
	}
	
	/**
	 * 调整bigdecimal精度后的String字符串格式数据.
	 * @param i 原数据
	 * @param scale	调整后的精度
	 * @return
	 */
	public static String adjustNumPlaces2Str(BigDecimal i, int scale){
		BigDecimal b = new BigDecimal(i.doubleValue());
		b = b.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return b.toString();
	}
}
