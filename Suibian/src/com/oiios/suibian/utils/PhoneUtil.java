package com.oiios.suibian.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.util.Log;

public class PhoneUtil {
	// 判断是否是电话号码
	public static boolean isTel(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(145)|(147)|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		Log.i("TAG", "是电话号码" + String.valueOf(m.matches()));
		return m.matches();
	}
}
