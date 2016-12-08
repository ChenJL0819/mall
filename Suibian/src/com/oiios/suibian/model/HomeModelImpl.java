package com.oiios.suibian.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oiios.suibian.bean.HomeCheapGoodsBean;
import com.oiios.suibian.bean.HomeRecommendGoodsBean;
import com.oiios.suibian.listener.HomeCheapGoodsListener;
import com.oiios.suibian.listener.HomeRecommendGoodsListener;
import com.oiios.suibian.mvp.IHomeModel;

/**
 * http://www.bdysc.com/
 */
public class HomeModelImpl implements IHomeModel {
	// 推荐商品
	@Override
	public void recommendGoods(HomeRecommendGoodsListener listener) {
		List<HomeRecommendGoodsBean> list = new ArrayList<HomeRecommendGoodsBean>();
		for (int i = 0; i < HttpData.RECOMMEND_GOODS_IMG_URL.length; i++) {
			HomeRecommendGoodsBean bean = new HomeRecommendGoodsBean();
			bean.setImageUrl(HttpData.RECOMMEND_GOODS_IMG_URL[i]);
			bean.setSkipUrl(HttpData.RECOMMEND_GOODS_SKIP_URL[i]);
			list.add(bean);
		}
		listener.loadHomeGoodsComplete(list);
	}

	@Override
	public void cheapGoods(int page, HomeCheapGoodsListener listener) {
		List<HomeCheapGoodsBean> list = new ArrayList<HomeCheapGoodsBean>();
		try {
			Document document = Jsoup.connect(HttpData.CHEAP_GOODS + page).timeout(HttpData.TIME_OUT).get();
			String elementkey = document.getElementsByClass("result_key_notfound").text();
			if (document != null && elementkey.equals("")) {
				Elements elements = document.getElementById("prodcutListUl").children();
				for (Element element : elements) {
					HomeCheapGoodsBean bean = new HomeCheapGoodsBean();
					bean.setUrl(element.select("a").first().absUrl("href"));
					bean.setImageUrl(element.select("img").first().absUrl("src"));
					bean.setDescribe(element.select("img").first().attr("alt"));
					String s = element.child(2).text();
					String shopName = s.substring(0, s.indexOf(" "));
					bean.setName(shopName);
					bean.setPrice(element.child(2).text().replace(element.child(2).child(0).text(), "").trim());
					list.add(bean);
				}
			}
			listener.loadCheapGoodsComplete(list);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("特价商品加载异常");
		}
	}

}
