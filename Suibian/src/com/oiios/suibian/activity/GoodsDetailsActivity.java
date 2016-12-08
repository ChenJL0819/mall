package com.oiios.suibian.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.oiios.suibian.R;
import com.oiios.suibian.adapter.GoodsDetailsPagerAdapter;
import com.oiios.suibian.adapter.HomePagerAdapter;
import com.oiios.suibian.bean.GoodsDetatilsBean;
import com.oiios.suibian.bean.ShopCarBean;
import com.oiios.suibian.model.BmobManager;
import com.oiios.suibian.mvp.IGoodsDetailsView;
import com.oiios.suibian.presenter.GoodsDetailsPresenterImpl;
import com.oiios.suibian.utils.CircleIndicator;
import com.oiios.suibian.utils.HttpUtil;
import com.oiios.suibian.utils.TitleUtil;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

public class GoodsDetailsActivity extends BaseActivity implements OnClickListener, IGoodsDetailsView {
	private PullToRefreshListView pullToRefreshLvAppraise;
	private Button btnAddCar;
	private Button btnBuy;
	private ViewPager vpPhoto;
	private GoodsDetailsPagerAdapter goodsPhotoAdapter;
	private CircleIndicator circleIndicator;
	private TextView tvDescribe;
	private TextView tvTitle;
	private TextView tvNowPrice;
	private TextView tvOldPrice;
	private ImageView ivStart;// 显示星星
	private TextView tvGrade;
	private TextView tvAppraiseCount;// 评价数量
	private TextView tvFavorableInfo;
	private LinearLayout layoutFavorableInfo;
	private ImageView ivRight;
	private TextView tvStoreName;
	private GoodsDetailsPresenterImpl presenter;
	private CheckBox cbCar;
	private ShopCarBean carBean;
	private String imgUrl;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_details);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);

		initView();
		loadData();
		bindListener();
	}

	public void loadData() {
		carBean = new ShopCarBean();
		carBean.setUserId(BmobUser.getCurrentUser(this).getUsername());
		presenter = new GoodsDetailsPresenterImpl(this);
		Intent intent = getIntent();
		String storeName = intent.getStringExtra("StoreNameKey");
		tvStoreName.setText(storeName);
		carBean.setStoreName(storeName);
		imgUrl = intent.getStringExtra("ImgUrlKey");

		final String goodsUrl = intent.getStringExtra("GoodsUrlKey");
		carBean.setGoodsUrl(goodsUrl);
		// 得到商品ID
		String goodsId = goodsUrl.substring(goodsUrl.lastIndexOf("-") + 1, goodsUrl.lastIndexOf("."));
//		 bean.setGoodId(goodsId);

		new Thread(new Runnable() {
			@Override
			public void run() {
				presenter.loadGoodsDetails(goodsUrl);
			}
		}).start();
	}

	// 初始化控件
	@Override
	public void initView() {
		myActionBar = (RelativeLayout) findViewById(R.id.myActionBar);
		initActionBar(R.drawable.previous, "商品信息", -1);
		pullToRefreshLvAppraise = (PullToRefreshListView) findViewById(R.id.pullToRefreshLvAppraise);
		// 设置下拉和上拉都起作用
		pullToRefreshLvAppraise.setMode(Mode.BOTH);
		cbCar = (CheckBox) findViewById(R.id.cbCar);
		View view = LayoutInflater.from(this).inflate(R.layout.inflate_goods_details, null);
		vpPhoto = (ViewPager) view.findViewById(R.id.vpPhoto);
		circleIndicator = (CircleIndicator) view.findViewById(R.id.circleIndicator);
		goodsPhotoAdapter = new GoodsDetailsPagerAdapter();
		vpPhoto.setAdapter(goodsPhotoAdapter);
		layoutFavorableInfo = (LinearLayout) view.findViewById(R.id.layoutFavorableInfo);
		tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		tvStoreName = (TextView) view.findViewById(R.id.tvStoreName);
		tvDescribe = (TextView) view.findViewById(R.id.tvDescribe);
		tvNowPrice = (TextView) view.findViewById(R.id.tvNowPrice);
		tvOldPrice = (TextView) view.findViewById(R.id.tvOldPrice);
		ivStart = (ImageView) view.findViewById(R.id.ivStart);
		tvGrade = (TextView) view.findViewById(R.id.tvGrade);
		tvAppraiseCount = (TextView) view.findViewById(R.id.tvAppraiseCount);
		tvFavorableInfo = (TextView) view.findViewById(R.id.tvFavorableInfo);
		ivRight = (ImageView) view.findViewById(R.id.ivRight);

		ListView lvAppraise = pullToRefreshLvAppraise.getRefreshableView();
		lvAppraise.addHeaderView(view);
		btnAddCar = (Button) findViewById(R.id.btnAddCar);
		btnBuy = (Button) findViewById(R.id.btnBuy);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		pullToRefreshLvAppraise.setAdapter(adapter);
	}

	@Override
	public void bindListener() {
		cbCar.setOnClickListener(this);
		btnBuy.setOnClickListener(this);
		btnAddCar.setOnClickListener(this);
		ivHeadLeft.setOnClickListener(this);
		vpPhoto.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				circleIndicator.updateDraw(arg0, arg1);
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnBuy:
			MyApplication.toast("立即购买");
			break;
		case R.id.btnAddCar:
			Log.i("TAG", "carBean == >"+carBean.toString());
			BmobManager.addCar(this, carBean, this);
			break;
		case R.id.ivHeadLeft:
			finish();
			break;
		case R.id.cbCar:
			Intent intent = new Intent(this, MainActivity.class);
			intent.putExtra("Key", "car");
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_GOODS_DETATILS_WHAT:
				refreshViewData(msg);
				break;
			default:
				break;
			}
		}
	};

	// 商品信息加载完成后刷新页面数据
	private void refreshViewData(android.os.Message msg) {
		GoodsDetatilsBean bean = (GoodsDetatilsBean) msg.obj;
		carBean.setGoodsId(bean.getGoodId());
		carBean.setGoodsCount(1);
		carBean.setDescible(bean.getTitle());
		if (bean.getImageUrls().size() >= 1) {
			carBean.setImgUrl(bean.getImageUrls().get(0));
		} else {
			carBean.setImgUrl(imgUrl);
		}
		carBean.setNowPrice(bean.getNowPrice());
		carBean.setOldPrice(bean.getOldPrice());
		// 得到ViewPager的图片
		List<String> imgs = bean.getImageUrl();
		if (imgs.size() <= 0) {
			ImageView iv = new ImageView(GoodsDetailsActivity.this);
			HttpUtil.displayImage(imgUrl, iv);
		} else if (imgs.size() <= 1) {
			circleIndicator.setVisibility(View.GONE);
		} else {
			circleIndicator.setVisibility(View.VISIBLE);
			circleIndicator.setCount(imgs.size());
			for (int i = 0; i < imgs.size(); i++) {
				ImageView iv = new ImageView(GoodsDetailsActivity.this);
				HttpUtil.displayImage(imgs.get(i), iv);
				iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				goodsPhotoAdapter.add(iv);
			}
		}
		tvDescribe.setText(bean.getDescribe());
		tvTitle.setText(bean.getTitle());
		tvNowPrice.setText("￥ " + bean.getNowPrice());
		tvOldPrice.setText("￥ " + bean.getOldPrice());
		// 划线
		tvOldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		tvGrade.setText(bean.getGrade() + "分");

		tvAppraiseCount.setText("(已有" + bean.getAppraiseCount() + "条评价)");
		if (bean.getFavorableInfo() == null) {
			layoutFavorableInfo.setVisibility(View.GONE);
		} else {
			layoutFavorableInfo.setVisibility(View.VISIBLE);
			tvFavorableInfo.setText(bean.getFavorableInfo());
		}
	};

	public static final int LOAD_GOODS_DETATILS_WHAT = 1;

	// 商品信息加载完成时回调
	@Override
	public void resultGoodsDetails(GoodsDetatilsBean bean) {
		Message msg = Message.obtain();
		msg.obj = bean;
		msg.what = LOAD_GOODS_DETATILS_WHAT;
		handler.sendMessage(msg);
	}

	@Override
	public void resultGoodsToCar(String result) {
		MyApplication.toast(result);
	}

}
