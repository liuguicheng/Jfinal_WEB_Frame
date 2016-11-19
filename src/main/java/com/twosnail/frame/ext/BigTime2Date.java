package com.twosnail.frame.ext;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.beetl.core.Format;


/**   
 * @Title: BigTime2Date.java
 */
public class BigTime2Date implements Format{

	@Override
	public Object format(Object data, String pattern) {
		 if (data == null)
             return null;
		 
		SimpleDateFormat sdf=new SimpleDateFormat( pattern );  
		return sdf.format(new Date((long) data));
	}

}
