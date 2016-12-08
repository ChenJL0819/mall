package com.oiios.suibian.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.oiios.suibian.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

/**
 * 图片处理相关的工具类
 *
 */
public class ImageUtils {
	//把Bitmap对象转化为二进制
	public static byte[] getByteByBitmap(Bitmap bitmap,Context context){
		if (bitmap == null) {
			bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	//通过二进制得到Bitmap对象
	public static Bitmap getBitmapByByte(byte[] b,Context context){
		if (b == null) {
			return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		}
		Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
		return bitmap;
	}
	
	//将资源id转化为Bitmap对象
	public static Bitmap getBitmapByResId(int resId,Context context){
		return BitmapFactory.decodeResource(context.getResources(), resId);
	}
}
