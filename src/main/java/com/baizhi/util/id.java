/**
 * 
 */
package com.baizhi.util;

import java.util.UUID;

/**
 * @author: SunYuanfei
 * @date:2020年7月30日 下午2:36:35
 * @explain :生成（包含"-"）36位随机码的方法
 * 
 */
public class id {

	public static String getId() {
		String id = UUID.randomUUID().toString();
		return id;
	}
}
