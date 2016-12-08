package com.oiios.suibian.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oiios.suibian.bean.RecommendListBean;
import com.oiios.suibian.listener.RecommendListListener;
import com.oiios.suibian.mvp.IRecommendListModel;

public class RecommendListModelImpl implements IRecommendListModel {

	@Override
	public void getRecommendList(String url, RecommendListListener listener) {
		List<RecommendListBean> list = new ArrayList<RecommendListBean>();
		try {
			Document document = Jsoup.connect(url).timeout(HttpData.TIME_OUT).get();
			String elementkey = document.getElementsByClass("result_key_notfound").text();
			if (document != null && elementkey.equals("")) {
				Elements elements = document.getElementsByClass("p1_pic");
				for (Element element : elements) {
					RecommendListBean bean = new RecommendListBean();
					for (int i = 0; i < element.childNodeSize(); i++) {
						String s = element.child(i).select("img").attr("alt");
						if (s.contains("<font color=red>")) {
							int start = s.indexOf("<font color=red>");
							int end = 0;
							if (s.contains("</font>")) {
								end = s.indexOf("</font>");
								int index = s.indexOf(">");
								String storeName = s.substring(index + 1, end);
								bean.setStoreName(storeName);

								s = s.replace("</font>", "\n");
							}
							if (end != 0) {
								s = s.substring(start + 16);
							}
							bean.setDescribe(s);
							bean.setImageUrl(element.child(i).select("img").attr("src"));
							bean.setSkipUrl(HttpData.CITY_SHOP_URL + element.child(i).select("a").attr("href"));
							list.add(bean);
						}
					}
				}
			}
			listener.loadRecommendListComplete(list);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("特价商品加载时异常");
		}
	}

}
