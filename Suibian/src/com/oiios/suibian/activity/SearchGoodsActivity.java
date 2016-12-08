package com.oiios.suibian.activity;

import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.oiios.suibian.R;
import com.oiios.suibian.adapter.SearchGoodsAdapter;
import com.oiios.suibian.bean.SearchGoodsBean;
import com.oiios.suibian.mvp.ISearchGoodsView;
import com.oiios.suibian.presenter.SearchGoodsPresenterImpl;
import com.oiios.suibian.utils.TitleUtil;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SearchGoodsActivity extends BaseActivity implements OnClickListener, ISearchGoodsView {
	private ImageView ivBack;
	private EditText etSearch;
	private Button btnSearch;
	private PullToRefreshListView pullToRefreshListView;
	private SearchGoodsAdapter adapter;
	private SearchGoodsPresenterImpl presenter;
	private int page = 1;

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_goods);
		TitleUtil.setSystemBarColor(this, R.color.mainColor);

		Intent intent = getIntent();
		String content = intent.getStringExtra("SearchKey");
		if (content != null) {
			searchContent = content;
			loadData();
		}
		initView();
		bindListener();
	}

	// 加载数据
	private void loadData() {
		presenter = new SearchGoodsPresenterImpl(this);
		new Thread(new Runnable() {
			@Override
			public void run() {
				presenter.loadSearchGoods(searchContent, page);
			}
		}).start();
	}

	@Override
	public void initView() {
		ivBack = (ImageView) findViewById(R.id.ivBack);
		etSearch = (EditText) findViewById(R.id.etSearch);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);
		// 设置下拉和上拉都起作用
		pullToRefreshListView.setMode(Mode.BOTH);
		adapter = new SearchGoodsAdapter(this);
		pullToRefreshListView.setAdapter(adapter);
	}

	@Override
	public void bindListener() {
		ivBack.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		pullToRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				SearchGoodsBean bean = adapter.getItem(arg2);
				String url = bean.getUrl();
				Intent intent = new Intent(SearchGoodsActivity.this, GoodsDetailsActivity.class);
				intent.putExtra("StoreNameKey", bean.getName());
				intent.putExtra("GoodsUrlKey", url);
				startActivity(intent);
			}
		});
		pullToRefreshListView.setOnRefreshListener(new OnRefreshListener2() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				if (searchContent == null) {
					return;
				}
				page = 1;
				loadData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				if (searchContent == null) {
					return;
				}
				page++;
				loadData();
			}
		});
	}

	boolean flag = true;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOAD_SEARCH_GOODS_COMPLETE_WHAT:
				List<SearchGoodsBean> list = (List<SearchGoodsBean>) msg.obj;
				adapter.addAll(list, flag);
				flag = false;
				break;
			default:
				break;
			}
		};
	};
	public static final int LOAD_SEARCH_GOODS_COMPLETE_WHAT = 1;

	// 查询商品完成时回调
	@Override
	public void resultSearchGoods(List<SearchGoodsBean> list) {
		Message msg = new Message();
		msg.obj = list;
		msg.what = LOAD_SEARCH_GOODS_COMPLETE_WHAT;
		handler.sendMessage(msg);
	}

	private String searchContent = null;

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ivBack:
			finish();
			break;
		case R.id.btnSearch:
			searchContent = etSearch.getText().toString().trim();
			if (searchContent != null) {
				loadData();
			}
			break;

		default:
			break;
		}
	}
}
