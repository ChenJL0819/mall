package com.oiios.suibian.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.oiios.suibian.bean.GoodsDetatilsBean;
import com.oiios.suibian.listener.GoodsDetailsListener;
import com.oiios.suibian.mvp.IGoodsDetailsModel;

import android.util.Log;

/**
 * 根据商品id加载商品信息
 * 
 * @author admim http://www.bdysc.com/product.jsp?id=p_2580528
 */
public class GoodsDetailsModelIpml implements IGoodsDetailsModel {
	@Override
	public void getGoodsDetails(String goodsUrl, GoodsDetailsListener listener) {
		GoodsDetatilsBean bean = new GoodsDetatilsBean();
		List<String> imgList = new ArrayList<String>();
		try {
			Document document = Jsoup.connect(goodsUrl).timeout(HttpData.TIME_OUT).get();
			String elementkey = document.getElementsByClass("result_key_notfound").text();
			Elements elements0 = document.getElementsByClass("collect");
			for (int i = 0; i < elements0.size(); i++) {
				String s = elements0.get(i).select("a").attr("onClick");
				String goodsId = s.substring(s.indexOf("p"), s.indexOf(",") - 1);
				Log.i("TAG", "goodsId = " + goodsId);
				bean.setGoodId(goodsId);
			}

			if (document != null && elementkey.equals("")) {
				// 获得商品图片的URL
				Element e = document.getElementById("small_pic_ul");
				for (int i = 0; i < e.childNodeSize(); i++) {
					String imgUrl = e.childNode(i).attr("data-big");
					if (imgUrl.endsWith(".png") || imgUrl.endsWith(".jpg")) {
						imgList.add(imgUrl);
					}
				}
				bean.setImageUrl(imgList);
				Elements elements1 = document.getElementsByClass("topright");
				for (Element element : elements1) {
					String title = element.getElementsByTag("h1").text();
					bean.setTitle(title);
					String describe = element.getElementsByTag("h2").text();
					bean.setDescribe(describe);
					// 活动的链接
					String url = element.child(1).select("a").attr("href");
					Elements elements2 = element.getElementsByClass("summary");
					for (Element element2 : elements2) {
						// 评价
						String appraise = element2.getElementsByTag("a").text();
						int startPosition = appraise.indexOf("有");
						int endPosition = appraise.indexOf("条");
						// 评价数量
						bean.setAppraiseCount(Integer.parseInt(appraise.substring(startPosition + 1, endPosition)));

						// 之前的价格
						String oldPrice = element2.getElementsByTag("del").text();
						if (oldPrice != null && oldPrice.length() >= 1) {
							bean.setOldPrice(Float.parseFloat(oldPrice.substring(1)));
						}
						// 现在的价格
						String nowPrice = element2.getElementById("reLoadPriceSpan").text();
						bean.setNowPrice(Float.parseFloat(nowPrice));
						Elements elements3 = element2.getElementsByClass("star");
						for (Element element3 : elements3) {
							// 评分
							String grade = element3.getElementsByTag("strong").text();
							bean.setGrade(Float.parseFloat(grade));
						}
					}

					Elements elements = element.getElementsByClass("rulesLayer");
					for (Element element2 : elements) {
						String favorableInfo = element2.getElementsByTag("i").text();
						favorableInfo = favorableInfo.replace("[", "");
						favorableInfo = favorableInfo.replace("]", "\n");
						bean.setFavorableInfo(favorableInfo);
					}

				}
			}
			listener.loadGoodsDetailsComplete(bean);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("分类异常");
		}
	}

}
