package com.oiios.suibian.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.oiios.suibian.R;
import com.oiios.suibian.activity.MyApplication;
import com.oiios.suibian.bean.CategoryGoodsBean;
import com.oiios.suibian.listener.CategoryGoodsListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

public class HttpUtil {
	public static boolean isConnect(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if (info != null && info.isConnected()) {
					// 判断当前网络是否已经连接
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			Log.v("error", e.toString());
		}
		return false;
	}

	public static void getCategoryGoods(String url, int page,CategoryGoodsListener listener) {
		List<CategoryGoodsBean> list = new ArrayList<CategoryGoodsBean>();
		try {
			Document document = Jsoup.connect(url + page + ".html").timeout(5000).get();
			String elementkey = document.getElementsByClass("result_key_notfound").text();

			if (document != null && elementkey.equals("")) {
				Elements elements = document.getElementById("prodcutListUl").children();
				for (Element element : elements) {
					CategoryGoodsBean bean = new CategoryGoodsBean();
					bean.setUrl("http://www.bdysc.com" + element.child(0).select("a").attr("href"));
					bean.setImageUrl(element.select("img").first().absUrl("src"));
					bean.setName(element.select("img").first().attr("alt"));
					bean.setDescribe("易商城自营店");
					bean.setPrice(element.child(2).text().replace(element.child(2).child(0).text(), "").trim());
					list.add(bean);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		listener.loadCategoryGoodsComplete(list);
	}
	
	static RequestQueue queue = Volley.newRequestQueue(MyApplication.context);
	public static ImageLoader imageLoader = new ImageLoader(queue, new ImageCache() {
		private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>((int) (Runtime.getRuntime().maxMemory()/8)){
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			};
		};
		@Override
		public void putBitmap(String arg0, Bitmap arg1) {
			lruCache.put(arg0, arg1);
		}
		
		@Override
		public Bitmap getBitmap(String arg0) {
			return lruCache.get(arg0);
		}
	});
	public static void displayImage(String url,ImageView iv){
		ImageListener listener = ImageLoader.getImageListener(iv, R.drawable.bucket_no_picture, R.drawable.bucket_no_picture);
		imageLoader.get(url, listener);
	}
	
}
