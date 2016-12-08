package com.oiios.suibian.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oiios.suibian.bean.CategoryGoodsBean;
import com.oiios.suibian.listener.CategoryGoodsListener;
import com.oiios.suibian.mvp.ICategoryModel;

public class CategoryModelImpl implements ICategoryModel {

	@Override
	public void getCategoryGoods(String url, int page, CategoryGoodsListener listener) {
		List<CategoryGoodsBean> list = new ArrayList<CategoryGoodsBean>();
		try {
			Document document = Jsoup.connect(url + page + ".html").timeout(HttpData.TIME_OUT).get();
			String elementkey = document.getElementsByClass("result_key_notfound").text();
			if (document != null && elementkey.equals("")) {
				Elements elements = document.getElementById("prodcutListUl").children();
				for (Element element : elements) {
					CategoryGoodsBean bean = new CategoryGoodsBean();
					bean.setUrl("http://www.bdysc.com" + element.child(0).select("a").attr("href"));
					bean.setImageUrl(element.select("img").first().absUrl("src"));
					bean.setDescribe(element.select("img").first().attr("alt"));
					String s = element.child(2).text();
					if (s != null && s.contains(" ")) {
						String shopName = s.substring(0, s.indexOf(" "));
						bean.setName(shopName);
					}

					bean.setPrice(element.child(2).text().replace(element.child(2).child(0).text(), "").trim());
					list.add(bean);
				}
			}
			listener.loadCategoryGoodsComplete(list);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("∑÷¿‡“Ï≥£");
		}
	}

}
