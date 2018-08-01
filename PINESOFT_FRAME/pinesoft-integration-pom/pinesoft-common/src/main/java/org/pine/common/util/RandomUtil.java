package org.pine.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RandomUtil {
	
	public static String getRandom() {
		return UUID.randomUUID().toString();
	}
	
	public static String getTimeRandom(){
		SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");
		return df.format(new Date())+""+getRandom();
	}
}
