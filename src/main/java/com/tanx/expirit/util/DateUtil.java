package com.tanx.expirit.util;

import java.util.Calendar;

public class DateUtil {
	public static java.sql.Date getNow(){
		return new java.sql.Date(Calendar.getInstance().getTimeInMillis());
	}
}
