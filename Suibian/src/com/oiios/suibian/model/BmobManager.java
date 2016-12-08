package com.oiios.suibian.model;

import java.util.List;

import com.oiios.suibian.activity.MyApplication;
import com.oiios.suibian.bean.AddressBean;
import com.oiios.suibian.bean.ShopCarBean;
import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.AddAddressListener;
import com.oiios.suibian.listener.AddressListener;
import com.oiios.suibian.listener.MyListener;
import com.oiios.suibian.listener.ShopCarChangedListener;
import com.oiios.suibian.listener.UpdateUserListener;
import com.oiios.suibian.mvp.IGoodsDetailsView;

import android.content.Context;
import android.util.Log;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class BmobManager {
	// 添加用户
	public static void addUser(final Context context, User user, final MyListener listener) {
		// 向后端云提交用户信息
		user.signUp(context, new SaveListener() {
			@Override
			public void onSuccess() {
				Log.i("TAG", "注册成功");
				listener.success("注册成功");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("TAG", "注册失败" + arg1);
				listener.fail("注册失败" + arg1);
			}
		});
	}
	public static void updateUser(Context context,User user,final MyListener listener){
		user.update(context, new UpdateListener() {
			@Override
			public void onSuccess() {
				listener.success("上传头像成功");
			}
			@Override
			public void onFailure(int arg0, String arg1) {
				listener.fail("上传头像失败"+arg1);
				Log.i("TAG", "上传头像失败"+arg0+"===="+arg1);
			}
		});
	}
	
	/**
	 * 加入购物车 前先判断购物车中是否已存在该商品，如果已存在，则修改该商品的数量
	 * 
	 * @param context
	 * @param bean
	 * @param listener
	 */
	public static void addCar(final Context context, final ShopCarBean bean, final IGoodsDetailsView listener) {
		BmobQuery<ShopCarBean> query = new BmobQuery<ShopCarBean>();
		query.addWhereEqualTo("userId", bean.getUserId());
		query.addWhereEqualTo("goodsId", bean.getGoodsId());
		query.findObjects(context, new FindListener<ShopCarBean>() {
			@Override
			public void onSuccess(List<ShopCarBean> arg0) {
				if (arg0.size() == 0) {// 不存在则添加
					// 保存记录
					bean.save(context, new SaveListener() {
						@Override
						public void onSuccess() {
							Log.i("TAG", "添加成功");
							listener.resultGoodsToCar("添加成功");
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							Log.i("TAG", "arg1 = " + arg1);
							listener.resultGoodsToCar("添加失败" + arg1);
						}
					});
					return;
				}
				// 存在则更新数据
				// 通过userId和goodsId查询只有一条记录
				ShopCarBean carBean = arg0.get(0);
				Log.i("TAG", carBean.toString());
				carBean.setGoodsCount(carBean.getGoodsCount() + 1);
				carBean.update(context, new UpdateListener() {
					@Override
					public void onSuccess() {
						Log.i("TAG", "添加成功");
						listener.resultGoodsToCar("添加成功");
					}

					@Override
					public void onFailure(int arg0, String arg1) {
						Log.i("TAG", "添加失败");
						listener.resultGoodsToCar("添加失败" + arg1);
					}
				});
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("TAG", "arg0 = " + arg0 + ";arg1 = " + arg1);
				listener.resultGoodsToCar("arg0 = " + arg0 + ";arg1 = " + arg1);
			}

		});
	}

	// 查询购物车
	public static void searchShoppingCar(Context context, String userId, final ShopCarChangedListener listener) {
		BmobQuery<ShopCarBean> query = new BmobQuery<ShopCarBean>();
		query.addWhereEqualTo("userId", userId);
		query.findObjects(context, new FindListener<ShopCarBean>() {
			@Override
			public void onSuccess(List<ShopCarBean> arg0) {
				listener.loadShopCarComplete(arg0);
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("TAG", "onError===>" + arg1);
			}
		});
	}

	// 删除购物车中的商品
	public static void delCarGoods(ShopCarBean bean, final ShopCarChangedListener listener) {
		bean.delete(MyApplication.context, new DeleteListener() {
			@Override
			public void onSuccess() {
				Log.i("TAG", "删除成功");
				listener.delGoodsSuccess("删除成功");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("TAG", "删除失败" + arg1);
				listener.delGoodsSuccess("删除失败" + arg1);
			}
		});
	}

	// 更新商品数量
	public static void updateGoodsCount(final ShopCarBean bean,final ShopCarChangedListener listener) {
		bean.update(MyApplication.context, new UpdateListener() {
			@Override
			public void onSuccess() {
				Log.i("TAG", "商品数量更新成功");
				listener.goodsCountSuccess("商品数量更新成功", bean);
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("TAG", "商品数量更新失败" + arg1);
				listener.goodsCountFail("商品数量更新失败"+arg1);
			}
		});
	}

	public static void updateUserInfo(Context context, User user, final UpdateUserListener listener) {
		user.update(context, new UpdateListener() {
			@Override
			public void onSuccess() {
				listener.success("用户信息更新成功");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				listener.fail("用户信息更新失败" + arg1);
				Log.i("TAG", "用户信息更新失败===>" + arg1);
			}
		});
	}

	// 添加收货地址
	public static void addAddress(Context context, AddressBean bean, final AddAddressListener listener) {
		bean.save(context, new SaveListener() {
			@Override
			public void onSuccess() {
				listener.success("添加收货地址成功");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				listener.fail("添加收货地址失败" + arg1);
			}
		});
	}

	// 查询收货地址
	public static void searchAddress(Context context, String username, final AddressListener listener) {
		BmobQuery<AddressBean> query = new BmobQuery<AddressBean>();
		query.addWhereEqualTo("username", username);
		query.findObjects(context, new FindListener<AddressBean>() {
			@Override
			public void onSuccess(List<AddressBean> arg0) {
				Log.i("TAG", "查询收货地址成功");
				listener.loadAddressComplete(arg0);
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("TAG", "查询收货地址失败" + arg1);
			}
		});
	}

	// 删除收货地址
	public static void deleteAddress(Context context, AddressBean bean, final AddressListener listener) {
		bean.delete(MyApplication.context, new DeleteListener() {
			@Override
			public void onSuccess() {
				Log.i("TAG", "删除成功");
				listener.deleteSuccess("删除成功");
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				Log.i("TAG", "删除失败" + arg1);
				listener.deleteFail("删除失败" + arg1);
			}
		});
	}
}
