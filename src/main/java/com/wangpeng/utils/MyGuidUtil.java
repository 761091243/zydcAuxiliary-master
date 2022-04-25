package com.wangpeng.utils;

import java.text.SimpleDateFormat;
import java.util.UUID;

public class MyGuidUtil {

	private static volatile int Guid = 100;

	private static int putAndSetIdAdd() {
		Guid++;
		if (Guid > 999) {
			Guid = 100;
		}
		return Guid;
	}

	private static ThreadLocal<SimpleDateFormat> sdf1 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyyMMdd'D'HHmmssSSS'T'");
		}
	};
	private static ThreadLocal<SimpleDateFormat> sdf2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("'D'HHmmss'T'");
		}
	};

	public static String getGUIDWithDateTime() {
		return sdf1.get().format(System.currentTimeMillis()) + putAndSetIdAdd();
	}

	public static String getGUIDWithTime() {
		return sdf2.get().format(System.currentTimeMillis()) + putAndSetIdAdd();
	}

	public static String getGUIDWithKey(String key) {
		return key + "_" + getGUIDWithDateTime();
	}

	public static String getUUIDWithSize32() {
		return UUID.randomUUID().toString().replace("-", "");
	}


}
