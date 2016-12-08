package com.oiios.suibian.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oiios.suibian.R;
import com.oiios.suibian.activity.GoodsDetailsActivity;
import com.oiios.suibian.activity.RecommendListActivity;
import com.oiios.suibian.activity.SearchGoodsActivity;
import com.oiios.suibian.adapter.HomeCheapGoodsAdapter;
import com.oiios.suibian.adapter.HomePagerAdapter;
import com.oiios.suibian.adapter.RecommendGoodsAdapter;
import com.oiios.suibian.adapter.HomeGridViewAdapter;
import com.oiios.suibian.bean.HomeCheapGoodsBean;
import com.oiios.suibian.bean.HomeRecommendGoodsBean;
import com.oiios.suibian.model.HttpData;
import com.oiios.suibian.mvp.IHomeView;
import com.oiios.suibian.presenter.HomePresenterImpl;
import com.oiios.suibian.utils.CircleIndicator;
import com.oiios.suibian.utils.HttpUtil;
import com.oiios.suibian.view.MyGridView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

@SuppressLint("InflateParams")
public class HomeFragment extends Fragment implements IHomeView {
	private ViewPager homePager; // 轮播的viewpager
	private CircleIndicator indicator;// viewpager的页面指示器
	private List<ImageView> list;// viewpager的数据
	private int index = 0; // viewpager当前显示的页标
	private MyGridView gvRecommendGoods;// 推荐商品
	private RecommendGoodsAdapter recommendAdapter;
	private HomePresenterImpl presenter;
	private GridView gvHome;// 天猫
	private EditText etSearch;// 搜索框
	private PullToRefreshListView pullToRefreshListView;// 下拉刷新listview
	private HomeCheapGoodsAdapter cheapAdapter;// 打折商品
	private int cheapPage = 1;
	private ListView lvHeader;

	public static final int LOAD_RECOMMEND_COMPLETE_WHAT = 1;
	public static final int LOAD_CHEAP_GOODS_COMPLETE_WHAT = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home, null);

		initData();
		initView(view);
		presenter = new HomePresenterImpl(this);
		loadData();

		playViewPager();
		setGridView1(view);
		bindListener();
		return view;
	}

	private void initData() {
		list = new ArrayList<ImageView>();
		for (int i = 0; i < HttpData.home.length; i++) {
			ImageView iv = new ImageView(getActivity());
			HttpUtil.displayImage(HttpData.home[i], iv);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			list.add(iv);
		}
	}

	private void loadData() {
		flag = true;
		cheapPage = 1;
		// 加载推荐商品数据
		loadRecommendGoods();
		loadCheapGoods();
	}

	// 加载推荐商品
	private void loadRecommendGoods() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				presenter.loadRecommendGoods();
			}
		}).start();
	}

	// 加载打折商品
	private void loadCheapGoods() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				presenter.loadCheapGoods(cheapPage);
			}
		}).start();
	}

	private void initView(View view) {
		etSearch = (EditText) view.findViewById(R.id.etSearch);
		pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
		// 设置下拉和上拉都起作用
		pullToRefreshListView.setMode(Mode.BOTH);

		View header1 = LayoutInflater.from(getActivity()).inflate(R.layout.inflate_home_header1, null);
		View header2 = LayoutInflater.from(getActivity()).inflate(R.layout.inflate_home_header2, null);
		View header3 = LayoutInflater.from(getActivity()).inflate(R.layout.inflate_home_header3, null);

		// 初始化页面指示器
		indicator = (CircleIndicator) header1.findViewById(R.id.circleIndicator);
		homePager = (ViewPager) header1.findViewById(R.id.homePager);
		// 构建viewpager的adapter
		HomePagerAdapter homePagerAdapter = new HomePagerAdapter(list,getActivity());
		// 关联adapter
		homePager.setAdapter(homePagerAdapter);
		homePager.setCurrentItem(1);
		gvHome = (GridView) header2.findViewById(R.id.gvHome);
		gvRecommendGoods = (MyGridView) header3.findViewById(R.id.gvRecommendGoods);
		// 推荐商品
		recommendAdapter = new RecommendGoodsAdapter(getActivity());
		gvRecommendGoods.setAdapter(recommendAdapter);

		lvHeader = pullToRefreshListView.getRefreshableView();
		lvHeader.addHeaderView(header1);
		lvHeader.addHeaderView(header2);
		lvHeader.addHeaderView(header3);
		cheapAdapter = new HomeCheapGoodsAdapter(getActivity());
		pullToRefreshListView.setAdapter(cheapAdapter);

		// 设置下拉刷新文本
		pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉刷新...");
		pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("放开刷新...");
		pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
		// 设置上拉刷新文本
		pullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
		pullToRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("放开刷新...");
		pullToRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
	}

	boolean flag = true;// 控制上拉加载更多时，不删除以前的数据

	private void bindListener() {
		etSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), SearchGoodsActivity.class);
				startActivity(intent);
			}
		});
		homePager.setOnPageChangeListener(new MyPagerChangeListener());
		gvHome.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), SearchGoodsActivity.class);
				intent.putExtra("SearchKey", HttpData.home2_content[position]);
				startActivity(intent);
			}
		});
		// 推荐商品的点击事件
		gvRecommendGoods.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), RecommendListActivity.class);
				intent.putExtra("RecommendUrlKey", recommendAdapter.getItem(arg2).getSkipUrl());
				startActivity(intent);
			}
		});
		// pullToRefreshListView的点击事件
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				HomeCheapGoodsBean bean = cheapAdapter.getItem(arg2 - lvHeader.getHeaderViewsCount());
				String url = bean.getUrl();
				Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
				intent.putExtra("StoreNameKey", bean.getName());
				intent.putExtra("GoodsUrlKey", url);
				intent.putExtra("ImgUrlKey", bean.getImageUrl());
				startActivity(intent);
			}
		});

		// pullToRefreshListView的监听事件
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				loadData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				cheapPage++;
				flag = false;
				loadCheapGoods();
			}
		});
		// 更改pullToRefreshListView的头部
		pullToRefreshListView.setOnPullEventListener(new OnPullEventListener<ListView>() {
			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView, State state, Mode direction) {
				if (direction == Mode.PULL_FROM_START) {
					// 正在刷新
					if (state == State.REFRESHING) {
						Log.i("TAG", "REFRESHING");
						SharedPreferences preferences = getActivity().getSharedPreferences("suibian",
								Activity.MODE_PRIVATE);
						// 获取上次刷新的时间
						String s = preferences.getString("HomeTimeKey",
								CategoryFragment.format.format(System.currentTimeMillis()));
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间：" + s);
						refreshView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");
						// 设置加载图标
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.refresh);
						pullToRefreshListView.getLoadingLayoutProxy()
								.setLoadingDrawable(new BitmapDrawable(getResources(), bitmap));

						// 保存本次刷新的时间
						Editor editor = preferences.edit();
						editor.putString("HomeTimeKey", CategoryFragment.format.format(System.currentTimeMillis()));
						editor.commit();
					} else if (state == state.PULL_TO_REFRESH) {
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pull_refresh);
						pullToRefreshListView.getLoadingLayoutProxy()
								.setLoadingDrawable(new BitmapDrawable(getResources(), bitmap));
					} else if (state == state.RESET) {

					}
				}
			}
		});
	}

	// home页的快速导航
	private void setGridView1(View view) {
		HomeGridViewAdapter adapter = new HomeGridViewAdapter(getActivity());
		gvHome.setAdapter(adapter);
		for(int i =0;i<HttpData.home2.length;i++){
			adapter.add(HttpData.home2[i]);
		}
	}

	// 使ViewPager轮播
	private void playViewPager() {
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				index++;
				if (index == list.size()) {
					index = 0;
				}
				homePager.setCurrentItem(index);
				handler.postDelayed(this, 3000);
			}
		}, 3000);
	}

	private class MyPagerChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int arg0) {

		}

		// 在页面切换的同时改变页面指示器的位置
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			indicator.updateDraw(arg0, arg1);

		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.obj == null) {
				return;
			}
			switch (msg.what) {
			case LOAD_RECOMMEND_COMPLETE_WHAT:
				List<HomeRecommendGoodsBean> list = (List<HomeRecommendGoodsBean>) msg.obj;
				recommendAdapter.addAll(list, true);
				pullToRefreshListView.onRefreshComplete();
				break;
			case LOAD_CHEAP_GOODS_COMPLETE_WHAT:
				List<HomeCheapGoodsBean> list2 = (List<HomeCheapGoodsBean>) msg.obj;
				cheapAdapter.addAll(list2, flag);
				pullToRefreshListView.onRefreshComplete();
				break;
			default:
				break;
			}
		};
	};

	// 推荐商品加载完成
	@Override
	public void resultRecommendGoods(List<HomeRecommendGoodsBean> list) {
		Message msg = Message.obtain();
		msg.obj = list;
		msg.what = LOAD_RECOMMEND_COMPLETE_WHAT;
		handler.sendMessage(msg);
	}

	// 特价商品
	@Override
	public void resultCheapGoods(List<HomeCheapGoodsBean> list) {
		Message msg = Message.obtain();
		msg.obj = list;
		msg.what = LOAD_CHEAP_GOODS_COMPLETE_WHAT;
		handler.sendMessage(msg);
	}

}
