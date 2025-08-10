package com.project.homepage_v2.cmmn;

public class AssertUtil {
	public static boolean notNull(int result) {
		return result == 1;
	}
	
	public static boolean notNull(String str) {
		return str != null || str != "";
	}
	
	public static boolean notNull(Object obj) {
		return obj != null;
	}
}