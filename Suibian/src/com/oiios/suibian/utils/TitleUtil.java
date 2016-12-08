package com.oiios.suibian.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class TitleUtil {

	public static void setSystemBarColor(Activity activity,int color){
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//ϵͳ�汾����19
			setTranslucentStatus(activity,true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(color);//���ñ�������ɫ������ɫ��color������
    }

	@TargetApi(19)
	private static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits; // a|=b����˼���ǰ�a��b��λ��Ȼ��ֵ��a
										// ��λ�����˼�����Ȱ�a��b������2���ƣ�Ȼ���û�������൱��a=a|b
		} else {
			winParams.flags &= ~bits; // &��λ�������棬������ a&=b�൱�� a = a&b ~�������
		}
		win.setAttributes(winParams);
	}
}
