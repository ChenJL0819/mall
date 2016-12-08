package com.oiios.suibian.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oiios.suibian.bean.SearchGoodsBean;
import com.oiios.suibian.listener.SearchGoodsListener;
import com.oiios.suibian.mvp.ISearchGoodsModel;

/**
 * 加载查询到的商品
 * 
 * @author admim
 *
 */
public class SearchGoodsModelImpl implements ISearchGoodsModel {

	/**
	 * content:查询的内容 page：页数
	 */
	@Override
	public void getSearchGoods(String content, int page, SearchGoodsListener listener) {
		List<SearchGoodsBean> list = new ArrayList<SearchGoodsBean>();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("keyword", content);
			Document document = Jsoup.connect("http://www.bdysc.com/product_list.jsp").data(map).timeout(HttpData.TIME_OUT).get();
			String elementkey = document.getElementsByClass("result_key_notfound").text();
			if (document != null && elementkey.equals("")) {
				Elements elements = document.getElementById("prodcutListUl").children();
				for (Element element : elements) {
					SearchGoodsBean bean = new SearchGoodsBean();
					bean.setUrl("http://www.bdysc.com" + element.child(0).select("a").attr("href"));
					bean.setImageUrl(element.select("img").first().absUrl("src"));
					bean.setDescribe(element.select("img").first().attr("alt"));
					String s = element.child(2).text();
					String shopName = s.substring(0, s.indexOf(" "));
					bean.setName(shopName);
					bean.setPrice(element.child(2).text().replace(element.child(2).child(0).text(), "").trim());
					list.add(bean);
				}
			}
			listener.loadSearchGoodsComplete(list);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("搜索时异常");
		}
	}

}
