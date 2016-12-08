package com.oiios.suibian.model;

public interface HttpData {
	public static final int TIME_OUT = 50000;
	public static final String CITY_SHOP_URL = "http://www.bdysc.com/";
	
	public static final String[] GOODS_GROUP_LIST = { "奶粉", "个人健康", "生活电器", "厨房电器", "美容工具", "口腔护理", "洗浴用品", "洗发护发",
			"营养保健", "茶叶" };

	public static final String[] GOODS_GROUP_URL = { "http://www.bdysc.com/list-430269/p",
			"http://www.bdysc.com/list-430401/p", "http://www.bdysc.com/list-430395/p",
			"http://www.bdysc.com/list-430386/p", "http://www.bdysc.com/list-430377/p",
			"http://www.bdysc.com/list-430367/p", "http://www.bdysc.com/list-430361/p",
			"http://www.bdysc.com/list-430355/p", "http://www.bdysc.com/list-430168/p",
			"http://www.bdysc.com/list-430164/p" };

	//推荐商品
	public static final String[] RECOMMEND_GOODS_SKIP_URL = { "http://www.bdysc.com/zhuantiye72.html?qz_gdt=tuijian_ad",
			"http://www.bdysc.com/product_list.jsp?keyword=%E5%91%B3%E4%B8%80%E9%B1%BC%E6%9D%BE&cid=c_10000",
			"http://www.bdysc.com/zhuantiye76.html?qz_gdt=tuijian_ad",
			"http://www.bdysc.com/product_list.jsp?keyword=%E8%AE%A1%E7%AE%97%E5%99%A8&cid=c_10000" };
	public static final String[] RECOMMEND_GOODS_IMG_URL = { "http://60.166.15.148/upload/s1/2016/11/1/3240045.jpg",
			"http://60.166.15.148/upload/s1/2016/11/11/3251152.jpg",
			"http://60.166.15.148/upload/s1/2016/10/23/3211545.jpg",
			"http://60.166.15.148/upload/s1/2016/9/27/3150001_200X200.png" };

	public static final String CHEAP_GOODS = "http://www.bdysc.com/product_list.jsp?keyword=%E4%BF%9D%E8%B4%A8%E6%9C%9F&cid=c_10000&page=";
	// Home ViewPager
	public static final String[] home = { "http://60.166.15.148/upload/s1/2016/10/30/3212790.jpg",
			"http://60.166.15.148/upload/s1/2016/1/11/2764316.jpg",
			"http://60.166.15.148/upload/s1/2016/11/11/3251155.jpg" };
	public static final String[] content = { "香飘飘", "母婴", "好祺" };

	// home head2
	public static final String[] home2 = { "http://60.166.15.148/upload/s1/2016/11/18/3270107.jpg",
			"http://60.166.15.148/upload/s1/2016/9/12/3110142_200X200.jpg",
			"http://60.166.15.148/upload/s1/2013/10/25/620095_120X120.JPG",
			"http://60.166.15.148/upload/s1/2016/11/11/3251171.jpg" };
	public static final String[] home2_content = { "牛奶", "全脂成人奶粉", "白湖香粳米", "美容化妆" };
}
