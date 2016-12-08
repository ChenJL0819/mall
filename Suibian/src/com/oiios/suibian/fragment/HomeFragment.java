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
	private ViewPager homePager; // �ֲ���viewpager
	private CircleIndicator indicator;// viewpager��ҳ��ָʾ��
	private List<ImageView> list;// viewpager������
	private int index = 0; // viewpager��ǰ��ʾ��ҳ��
	private MyGridView gvRecommendGoods;// �Ƽ���Ʒ
	private RecommendGoodsAdapter recommendAdapter;
	private HomePresenterImpl presenter;
	private GridView gvHome;// ��è
	private EditText etSearch;// ������
	private PullToRefreshListView pullToRefreshListView;// ����ˢ��listview
	private HomeCheapGoodsAdapter cheapAdapter;// ������Ʒ
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
		// �����Ƽ���Ʒ����
		loadRecommendGoods();
		loadCheapGoods();
	}

	// �����Ƽ���Ʒ
	private void loadRecommendGoods() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				presenter.loadRecommendGoods();
			}
		}).start();
	}

	// ���ش�����Ʒ
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
		// ����������������������
		pullToRefreshListView.setMode(Mode.BOTH);

		View header1 = LayoutInflater.from(getActivity()).inflate(R.layout.inflate_home_header1, null);
		View header2 = LayoutInflater.from(getActivity()).inflate(R.layout.inflate_home_header2, null);
		View header3 = LayoutInflater.from(getActivity()).inflate(R.layout.inflate_home_header3, null);

		// ��ʼ��ҳ��ָʾ��
		indicator = (CircleIndicator) header1.findViewById(R.id.circleIndicator);
		homePager = (ViewPager) header1.findViewById(R.id.homePager);
		// ����viewpager��adapter
		HomePagerAdapter homePagerAdapter = new HomePagerAdapter(list,getActivity());
		// ����adapter
		homePager.setAdapter(homePagerAdapter);
		homePager.setCurrentItem(1);
		gvHome = (GridView) header2.findViewById(R.id.gvHome);
		gvRecommendGoods = (MyGridView) header3.findViewById(R.id.gvRecommendGoods);
		// �Ƽ���Ʒ
		recommendAdapter = new RecommendGoodsAdapter(getActivity());
		gvRecommendGoods.setAdapter(recommendAdapter);

		lvHeader = pullToRefreshListView.getRefreshableView();
		lvHeader.addHeaderView(header1);
		lvHeader.addHeaderView(header2);
		lvHeader.addHeaderView(header3);
		cheapAdapter = new HomeCheapGoodsAdapter(getActivity());
		pullToRefreshListView.setAdapter(cheapAdapter);

		// ��������ˢ���ı�
		pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("����ˢ��...");
		pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("�ſ�ˢ��...");
		pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("���ڼ���...");
		// ��������ˢ���ı�
		pullToRefreshListView.getLoadingLayoutProxy(true, false).setPullLabel("����ˢ��...");
		pullToRefreshListView.getLoadingLayoutProxy(true, false).setReleaseLabel("�ſ�ˢ��...");
		pullToRefreshListView.getLoadingLayoutProxy(true, false).setRefreshingLabel("���ڼ���...");
	}

	boolean flag = true;// �����������ظ���ʱ����ɾ����ǰ������

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
		// �Ƽ���Ʒ�ĵ���¼�
		gvRecommendGoods.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = new Intent(getActivity(), RecommendListActivity.class);
				intent.putExtra("RecommendUrlKey", recommendAdapter.getItem(arg2).getSkipUrl());
				startActivity(intent);
			}
		});
		// pullToRefreshListView�ĵ���¼�
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

		// pullToRefreshListView�ļ����¼�
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
		// ����pullToRefreshListView��ͷ��
		pullToRefreshListView.setOnPullEventListener(new OnPullEventListener<ListView>() {
			@Override
			public void onPullEvent(PullToRefreshBase<ListView> refreshView, State state, Mode direction) {
				if (direction == Mode.PULL_FROM_START) {
					// ����ˢ��
					if (state == State.REFRESHING) {
						Log.i("TAG", "REFRESHING");
						SharedPreferences preferences = getActivity().getSharedPreferences("suibian",
								Activity.MODE_PRIVATE);
						// ��ȡ�ϴ�ˢ�µ�ʱ��
						String s = preferences.getString("HomeTimeKey",
								CategoryFragment.format.format(System.currentTimeMillis()));
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel("�ϴ�ˢ��ʱ�䣺" + s);
						refreshView.getLoadingLayoutProxy().setRefreshingLabel("����ˢ��");
						// ���ü���ͼ��
						Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.refresh);
						pullToRefreshListView.getLoadingLayoutProxy()
								.setLoadingDrawable(new BitmapDrawable(getResources(), bitmap));

						// ���汾��ˢ�µ�ʱ��
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

	// homeҳ�Ŀ��ٵ���
	private void setGridView1(View view) {
		HomeGridViewAdapter adapter = new HomeGridViewAdapter(getActivity());
		gvHome.setAdapter(adapter);
		for(int i =0;i<HttpData.home2.length;i++){
			adapter.add(HttpData.home2[i]);
		}
	}

	// ʹViewPager�ֲ�
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

		// ��ҳ���л���ͬʱ�ı�ҳ��ָʾ����λ��
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

	// �Ƽ���Ʒ�������
	@Override
	public void resultRecommendGoods(List<HomeRecommendGoodsBean> list) {
		Message msg = Message.obtain();
		msg.obj = list;
		msg.what = LOAD_RECOMMEND_COMPLETE_WHAT;
		handler.sendMessage(msg);
	}

	// �ؼ���Ʒ
	@Override
	public void resultCheapGoods(List<HomeCheapGoodsBean> list) {
		Message msg = Message.obtain();
		msg.obj = list;
		msg.what = LOAD_CHEAP_GOODS_COMPLETE_WHAT;
		handler.sendMessage(msg);
	}

}
