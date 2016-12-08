package com.oiios.suibian.utils;

import android.util.Log;

public class LogUtil {
	// ����ȫ�ֿ����Ƿ��ӡlog��־
	private static boolean isPrintLog = true;
	private static int LOG_MAXLENGTH = 2000;

	public static void MyLog(String msg) {
		if (isPrintLog) {
			int strLength = msg.length();
			int start = 0;
			int end = LOG_MAXLENGTH;
			for (int i = 0; i < 100; i++) {
				if (strLength > end) {
					Log.e("TAG" + i, msg.substring(start, end));
					start = end;
					end = end + LOG_MAXLENGTH;
				} else {
					Log.e("TAG" + i, msg.substring(start, strLength));
					break;
				}
			}
		}
	}
}
