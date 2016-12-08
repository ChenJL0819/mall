package com.oiios.suibian.activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.widget.Toast;
import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;

public class MyApplication extends Application {
	public static Context context;
	//退出登录时，finish集合中的所有activity
	public static List<Activity> listActivity;
	public void onCreate() {
		super.onCreate();
		listActivity = new ArrayList<Activity>();
		// 只有主进程运行的时候才需要初始化
		if (getApplicationInfo().packageName.equals(getMyProcessName())) {
			initBmobSDK();
			// im初始化
			BmobIM.init(this);
			context = this;
		}
	}

	/**
	 * 获取当前运行的进程名
	 * 
	 * @return
	 */
	public static String getMyProcessName() {
		try {
			File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
			BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
			String processName = mBufferedReader.readLine().trim();
			mBufferedReader.close();
			return processName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void initBmobSDK() {
		// 后端云
		Bmob.initialize(this, "80f1cb66f486bcb884bf1089760a248d");
		// 使用推送服务时的初始化操作
//		BmobInstallation.getCurrentInstallation(this).save();
	}
	
	public static void toast(String content){
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}
}
