package com.oiios.suibian.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

public class TitleUtil {

	public static void setSystemBarColor(Activity activity,int color){
    	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//系统版本大于19
			setTranslucentStatus(activity,true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(activity);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(color);//设置标题栏颜色，此颜色在color中声明
    }

	@TargetApi(19)
	private static void setTranslucentStatus(Activity activity, boolean on) {
		Window win = activity.getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits; // a|=b的意思就是把a和b按位或然后赋值给a
										// 按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
		} else {
			winParams.flags &= ~bits; // &是位运算里面，与运算 a&=b相当于 a = a&b ~非运算符
		}
		win.setAttributes(winParams);
	}
}
