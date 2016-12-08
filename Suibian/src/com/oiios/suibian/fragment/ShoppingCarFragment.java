package com.oiios.suibian.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oiios.suibian.R;
import com.oiios.suibian.activity.GoodsDetailsActivity;
import com.oiios.suibian.activity.MyApplication;
import com.oiios.suibian.adapter.ShopCarAdapter;
import com.oiios.suibian.bean.ShopCarBean;
import com.oiios.suibian.bean.StoreBean;
import com.oiios.suibian.listener.ShopCarChangedListener;
import com.oiios.suibian.model.BmobManager;
import com.oiios.suibian.utils.LogUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;

public class ShoppingCarFragment extends BaseFragment implements ShopCarChangedListener, OnClickListener {
	private TextView tvTotal;
	private CheckBox cbSelectAll;
	private RelativeLayout layoutCarEmpty;
	private ExpandableListView elvShoppingCar;
	private ShopCarAdapter carAdapter;
	private List<StoreBean> storeList;
	private Map<String, List<ShopCarBean>> carMap;
	public static final int LOAD_COMPLETE_WHAT = 1;
	public static final int DELETE_CAR_GOODS_WHAT = 2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = View.inflate(getActivity(), R.layout.fragment_shopping_car, null);
		initView(view);
		bindListener();
		loadShopCarInfo();
		return view;
	}

	public void initView(View view) {
		storeList = new ArrayList<StoreBean>();
		carMap = new HashMap<String, List<ShopCarBean>>();
		tvTotal = (TextView) view.findViewById(R.id.tvTotal);
		layoutCarEmpty = (RelativeLayout) view.findViewById(R.id.layoutCarEmpty);
		elvShoppingCar = (ExpandableListView) view.findViewById(R.id.elvShoppingCar);
		cbSelectAll = (CheckBox) view.findViewById(R.id.cbSelectAll);
		carAdapter = new ShopCarAdapter(getActivity(), this);
		elvShoppingCar.setAdapter(carAdapter);
		
		myActionBar = (RelativeLayout) view.findViewById(R.id.myActionBar);
		initActionBar(-1, "购物车", -1);
	}

	@Override
	public void bindListener() {
		cbSelectAll.setOnClickListener(this);
		elvShoppingCar.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition,
					long id) {
				ShopCarBean bean = carAdapter.getChild(groupPosition, childPosition);
				Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
				intent.putExtra("StoreNameKey", bean.getStoreName());
				intent.putExtra("ImgUrlKey", bean.getImgUrl());
				intent.putExtra("GoodsUrlKey", bean.getGoodsUrl());
				startActivity(intent);
				return true;
			}
		});
	}

	private void loadShopCarInfo() {
		// 加载购物车信息
		new Thread(new Runnable() {
			@Override
			public void run() {
				/**
				 * 因为addGroupAll()在工作线程中执行，所以不能在该方法中使用notifyDataSetChanged()刷新适配器
				 */
				BmobUser user = BmobUser.getCurrentUser(getActivity());
				BmobManager.searchShoppingCar(getActivity(), user.getUsername(), ShoppingCarFragment.this);
			}
		}).start();
	}

	Handler hanlder = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			// 购物车信息加载
			if (msg.what == LOAD_COMPLETE_WHAT) {
				// 展开所有的分组
				List<ShopCarBean> list = (List<ShopCarBean>) msg.obj;
				if (list.isEmpty()) {
					layoutCarEmpty.setVisibility(View.VISIBLE);
					elvShoppingCar.setVisibility(View.GONE);
					return;
				} else {
					layoutCarEmpty.setVisibility(View.GONE);
					elvShoppingCar.setVisibility(View.VISIBLE);
				}
				Set<String> storeName = new HashSet<String>();
				for (ShopCarBean shopCarBean : list) {
					storeName.add(shopCarBean.getStoreName());
				}

				// 得到store信息
				for (String string : storeName) {
					List<ShopCarBean> childList = new ArrayList<ShopCarBean>();
					boolean flag = true;
					for (ShopCarBean shopCarBean : list) {
						if (string.equals(shopCarBean.getStoreName())) {
							if (flag) {
								StoreBean bean = new StoreBean();
								bean.setStoreName(shopCarBean.getStoreName());
								storeList.add(bean);
								flag = false;
							}
							shopCarBean.setChecked(false);
							childList.add(shopCarBean);
						}
					}
					carMap.put(string, childList);
				}
				carAdapter.addGroupAll(storeList, true);
				carAdapter.addChildAll(carMap, true);
				for (int i = 0; i < carAdapter.getGroupCount(); i++) {
					if (!elvShoppingCar.isGroupExpanded(i)) {
						elvShoppingCar.expandGroup(i);
					}
				}
				carAdapter.notifyDataSetChanged();
			}

			if (msg.what == DELETE_CAR_GOODS_WHAT) {
				carAdapter.removeChild(groupPosition, childPosition);
				if (carAdapter.getGroupCount() == 0) {
					layoutCarEmpty.setVisibility(View.VISIBLE);
					elvShoppingCar.setVisibility(View.GONE);
					return;
				} else {
					layoutCarEmpty.setVisibility(View.GONE);
					elvShoppingCar.setVisibility(View.VISIBLE);
				}
			}
		};
	};

	// 选择的商品改变时回调该方法
	@Override
	public void moneyChange(float money, float freight) {
		tvTotal.setText("￥ " + money);
	}

	// 选择全部商品时调用
	@Override
	public void onClick(View v) {
		selectGoodsAll(((CheckBox) v).isChecked());
	}

	@Override
	public void checkedStoreAll(boolean state) {
		cbSelectAll.setChecked(state);
	}

	// 选中购物车中所有的商品
	public void selectGoodsAll(boolean state) {
		if (carAdapter.getGroupCount() != 0) {
			carAdapter.selectStoreAll(state);
			carAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void loadShopCarComplete(List<ShopCarBean> list) {
		LogUtil.MyLog(list.toString());
		Message msg = Message.obtain();
		msg.what = LOAD_COMPLETE_WHAT;
		msg.obj = list;
		// 通知主线程加载完成
		hanlder.sendMessage(msg);
	}

	int groupPosition;
	int childPosition;

	@Override
	public void removeCarGoods(int groupPosition, int childPosition) {
		this.groupPosition = groupPosition;
		this.childPosition = childPosition;
		ShopCarBean bean = carAdapter.getChild(groupPosition, childPosition);
		BmobManager.delCarGoods(bean, this);
	}

	// 购物车数据删除成功时回调
	@Override
	public void delGoodsSuccess(String s) {
		MyApplication.toast(s);
		hanlder.sendEmptyMessage(DELETE_CAR_GOODS_WHAT);
	}

	@Override
	public void delGoodsFail(String s) {
		MyApplication.toast(s);
	}

	@Override
	public void goodsCountSuccess(String s, ShopCarBean bean) {
		MyApplication.toast(s);
		carAdapter.queryCheckedGoods();
	}

	@Override
	public void goodsCountFail(String s) {
		MyApplication.toast(s);
	}
}
