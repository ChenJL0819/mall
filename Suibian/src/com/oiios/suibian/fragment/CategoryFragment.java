package com.oiios.suibian.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.oiios.suibian.R;
import com.oiios.suibian.activity.GoodsDetailsActivity;
import com.oiios.suibian.activity.SearchGoodsActivity;
import com.oiios.suibian.adapter.GvCategoryAdapter;
import com.oiios.suibian.adapter.LvCategoryAdapter;
import com.oiios.suibian.bean.CategoryBean;
import com.oiios.suibian.bean.CategoryGoodsBean;
import com.oiios.suibian.model.HttpData;
import com.oiios.suibian.mvp.ICategoryGoodsView;
import com.oiios.suibian.presenter.CategoryPresenterImpl;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CategoryFragment extends BaseFragment implements OnClickListener, ICategoryGoodsView {
	private ListView lvNavigation;
	private LvCategoryAdapter lvCategoryAdapter;
	private PullToRefreshGridView gvRefreshCategory;
	private GvCategoryAdapter gvCategoryAdapter;
	private int page = 1;
	private String url = null;
	private static final int LOAD_GOODS_COMPLETE = 1;
	private DrawerLayout drawer;
	private EditText etSearch;
	private CategoryPresenterImpl presenter;
	public static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_category, null);
		initView(view);
		presenter = new CategoryPresenterImpl(this);

		loadCategory();
		bindListener();
		return view;
	}

	// 加载分类信息
	private void loadCategory() {
		List<CategoryBean> list = new ArrayList<CategoryBean>();
		for (int i = 0; i < HttpData.GOODS_GROUP_LIST.length; i++) {
			CategoryBean bean = new CategoryBean();
			bean.setName(HttpData.GOODS_GROUP_LIST[i]);
			bean.setUrl(HttpData.GOODS_GROUP_URL[i]);
			list.add(bean);
		}
		lvCategoryAdapter.addAll(list, true);
	}

	// 加载商品信息
	private void loadGoods() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				presenter.getCategoryGoods(url, page);
			}
		}).start();
	}

	public void initView(View view) {
		myActionBar = (RelativeLayout) view.findViewById(R.id.myActionBar);
		initActionBar(R.drawable.ic_category_27769, "分类", -1);

		drawer = (DrawerLayout) view.findViewById(R.id.category_drawer);
		drawer.openDrawer(Gravity.START);
		etSearch = (EditText) view.findViewById(R.id.etSearch);

		lvNavigation = (ListView) view.findViewById(R.id.lvCategory);
		lvCategoryAdapter = new LvCategoryAdapter(getActivity());
		lvNavigation.setAdapter(lvCategoryAdapter);

		gvRefreshCategory = (PullToRefreshGridView) view.findViewById(R.id.gvCategory);
		// 设置下拉和上拉都起作用
		gvRefreshCategory.setMode(Mode.BOTH);
		gvCategoryAdapter = new GvCategoryAdapter(getActivity());
		gvRefreshCategory.setAdapter(gvCategoryAdapter);
		// 设置加载图标
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.refresh);
		gvRefreshCategory.getLoadingLayoutProxy().setLoadingDrawable(new BitmapDrawable(getResources(), bitmap));
		// 设置下拉刷新文本
		gvRefreshCategory.getLoadingLayoutProxy(false, true).setPullLabel("上拉刷新...");
		gvRefreshCategory.getLoadingLayoutProxy(false, true).setReleaseLabel("放开刷新...");
		gvRefreshCategory.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
		// 设置上拉刷新文本
		gvRefreshCategory.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
		gvRefreshCategory.getLoadingLayoutProxy(true, false).setReleaseLabel("放开刷新...");
		gvRefreshCategory.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
	}

	public void bindListener() {
		ivHeadLeft.setOnClickListener(this);
		etSearch.setOnClickListener(this);
		lvNavigation.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				flag = true;
				page = arg2 + 1;
				url = lvCategoryAdapter.getItem(arg2).getUrl();
				loadGoods();
			}
		});
		// 点击每一件商品
		gvRefreshCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
				CategoryGoodsBean bean = gvCategoryAdapter.getItem(arg2);
				intent.putExtra("StoreNameKey", bean.getName());
				intent.putExtra("GoodsUrlKey", bean.getUrl());
				intent.putExtra("ImgUrlKey", bean.getImageUrl());
				startActivity(intent);
			}
		});

		// 下拉刷新、上拉加载更多
		gvRefreshCategory.setOnRefreshListener(new OnRefreshListener2<GridView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
				if (url == null) {
					gvRefreshCategory.onRefreshComplete();
					return;
				}
				page = 1;
				loadGoods();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
				page++;
				flag = false;
				loadGoods();
			}
		});

		gvRefreshCategory.setOnPullEventListener(new OnPullEventListener<GridView>() {
			@Override
			public void onPullEvent(PullToRefreshBase<GridView> refreshView, State state, Mode direction) {
				if (direction == Mode.PULL_FROM_START) {
					// 正在刷新
					if (state == State.REFRESHING) {
						SharedPreferences preferences = getActivity().getSharedPreferences("suibian",
								Activity.MODE_PRIVATE);
						// 获取上次刷新的时间
						String s = preferences.getString("CategoryTimeKey",
								CategoryFragment.format.format(System.currentTimeMillis()));
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("上次刷新时间：" + s);
						refreshView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新");

						// 保存本次刷新的时间
						Editor editor = preferences.edit();
						editor.putString("CategoryTimeKey", CategoryFragment.format.format(System.currentTimeMillis()));
						editor.commit();
					}
				}
			}
		});
	}

	private boolean flag = true;
	Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			if (msg.obj == null) {
				return;
			}
			if (msg.what == LOAD_GOODS_COMPLETE) {// 商品加载完成
				List<CategoryGoodsBean> list = (List<CategoryGoodsBean>) msg.obj;
				gvCategoryAdapter.addAll(list, flag);
				drawer.closeDrawer(Gravity.START);
				gvRefreshCategory.onRefreshComplete();
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivHeadLeft:
			if (drawer.isDrawerOpen(Gravity.START)) {
				drawer.closeDrawer(Gravity.START);
			} else {
				drawer.openDrawer(Gravity.START);
			}
			break;
		case R.id.etSearch:
			Intent intent = new Intent(getActivity(), SearchGoodsActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void resultCategoryGoods(List<CategoryGoodsBean> list) {
		Message msg = new Message();
		msg.obj = list;
		msg.what = LOAD_GOODS_COMPLETE;
		handler.sendMessage(msg);
	}

	@Override
	public void resultCategory(List<CategoryBean> list) {

	}
}