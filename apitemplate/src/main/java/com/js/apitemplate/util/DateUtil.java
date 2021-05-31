package com.js.apitemplate.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateUtil {

	/**
	 * 현재시간 반환
	 * @param pattern
	 * @return
	 */
	public String nowDate(String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(new Date());
	}
}
