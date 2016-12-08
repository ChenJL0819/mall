package com.oiios.suibian.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.oiios.suibian.R;
import com.oiios.suibian.adapter.RecommendListAdapter;
import com.oiios.suibian.bean.HomeCheapGoodsBean;
import com.oiios.suibian.bean.RecommendListBean;
import com.oiios.suibian.mvp.IRecommendListView;
import com.oiios.suibian.presenter.RecommendListPresenterImpl;
import com.oiios.suibian.utils.TitleUtil;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

public class RecommendListActivity extends BaseActivity implements IRecommendListView,OnClickListener {
	private PullToRefreshListView pullToRefreshListView;
	private RecommendListAdapter adapter;
	private static final int LOAD_RECOMMEND_GOODS_COMPLETE = 1;
	private RecommendListPresenterImpl presenter;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend_goods_list);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);

		initView();
		initData();
		bindListener();
	}

	private void initData() {
		presenter = new RecommendListPresenterImpl(this);
		Intent intent = getIntent();
		final String url = intent.getStringExtra("RecommendUrlKey");
		Log.i("TAG", "url=" + url);
		// 异步加载推荐商品列表
		new Thread(new Runnable() {
			@Override
			public void run() {
				presenter.getRecommendListGoods(url);
			}
		}).start();
	}

	@Override
	public void initView() {
		myActionBar = (RelativeLayout) findViewById(R.id.myActionBar);
		initActionBar(R.drawable.previous, "推荐商品列表", -1);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);
		// 设置下拉和上拉都起作用
		pullToRefreshListView.setMode(Mode.BOTH);
		adapter = new RecommendListAdapter(this);
		pullToRefreshListView.setAdapter(adapter);
	}

	@Override
	public void bindListener() {
		ivHeadLeft.setOnClickListener(this);
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				RecommendListBean bean = adapter.getItem(arg2);
				String url = bean.getSkipUrl();
				Intent intent = new Intent(RecommendListActivity.this, GoodsDetailsActivity.class);
//				intent.putExtra("StoreNameKey", bean.getName());
				intent.putExtra("GoodsUrlKey", url);
				intent.putExtra("ImgUrlKey", bean.getImageUrl());
				startActivity(intent);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_RECOMMEND_GOODS_COMPLETE:
				List<RecommendListBean> list = (List<RecommendListBean>) msg.obj;
				adapter.addAll(list, true);
				break;
			default:
				break;
			}
		};
	};

	// 推荐商品加载完成时回调
	@Override
	public void resultRecommendList(List<RecommendListBean> list) {
		Message msg = Message.obtain();
		msg.what = LOAD_RECOMMEND_GOODS_COMPLETE;
		msg.obj = list;
		handler.sendMessage(msg);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivHeadLeft:
			finish();
			break;

		default:
			break;
		}
		
	}
}
