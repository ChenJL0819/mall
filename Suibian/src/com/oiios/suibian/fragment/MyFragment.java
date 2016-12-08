package com.oiios.suibian.fragment;

import java.io.File;
import java.util.List;

import com.oiios.suibian.R;
import com.oiios.suibian.activity.AddressActivity;
import com.oiios.suibian.activity.MyApplication;
import com.oiios.suibian.activity.SetActivity;
import com.oiios.suibian.adapter.UserGridViewAdapter;
import com.oiios.suibian.bean.User;
import com.oiios.suibian.listener.MyListener;
import com.oiios.suibian.model.BmobManager;
import com.oiios.suibian.utils.FileUtil;
import com.oiios.suibian.utils.HttpUtil;
import com.oiios.suibian.view.CircleImageView;
import com.oiios.suibian.view.MyGridView;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UploadFileListener;

public class MyFragment extends Fragment implements OnClickListener, MyListener {
	private MyGridView userGridView;
	private MyGridView gvTool;
	private TextView tvSet;
	private CircleImageView ivHead;
	private RelativeLayout layoutAddress;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my, null);
		initView(view);
		initData();
		bindListener();
		setGridView1(view);
		setGridView2(view);
		return view;
	}

	Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(android.os.Message msg) {
			String picName = System.currentTimeMillis() + ".jpg";
			Bitmap bitmap = (Bitmap) msg.obj;
			ivHead.setImageBitmap(bitmap);
			FileUtil.saveBitmap(bitmap, picName);
			String path = "/sdcard/suibian/"+picName;
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("suibian", getActivity().MODE_PRIVATE)
					.edit();
			editor.putString("filePath", path);
			editor.commit();
		};
	};
	public static final int HEADER_LOAD_COMPLETE = 1;

	private void initData() {
		SharedPreferences preferences = getActivity().getSharedPreferences("suibian", getActivity().MODE_PRIVATE);
		Bitmap bitmap = FileUtil.getDiskBitmap(preferences.getString("filePath", null));
		if (bitmap != null) {
			ivHead.setImageBitmap(bitmap);
			return;
		}
		BmobUser user = BmobUser.getCurrentUser(getActivity());
		BmobQuery<User> query = new BmobQuery<User>("_User");
		query.addWhereEqualTo("objectId", user.getObjectId());
		query.findObjects(getActivity(), new FindListener<User>() {
			@Override
			public void onSuccess(final List<User> arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String headerUrl = arg0.get(0).getAvatar();
						Bitmap bitmap = FileUtil.getBitmapByUrl(headerUrl);
						Message msg = Message.obtain();
						msg.what = HEADER_LOAD_COMPLETE;
						msg.obj = bitmap;
						handler.sendMessage(msg);
					}
				}).start();
			}

			@Override
			public void onError(int arg0, String arg1) {
				Log.i("TAG", arg1);
			}
		});
	}

	private void initView(View view) {
		tvSet = (TextView) view.findViewById(R.id.tvSet);
		gvTool = (MyGridView) view.findViewById(R.id.gvTool);
		userGridView = (MyGridView) view.findViewById(R.id.userGridView);
		layoutAddress = (RelativeLayout) view.findViewById(R.id.layoutAddress);
		ivHead = (CircleImageView) view.findViewById(R.id.ivHead);
	}

	String cameraPath;// 拍摄头像照片时SD卡的路径
	String avatarUrl;// 上传头像照片完毕后，头像照片在服务器上的网址
	private Uri imageUri;

	private void bindListener() {
		tvSet.setOnClickListener(this);
		layoutAddress.setOnClickListener(this);
		ivHead.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent(Intent.ACTION_PICK);
				intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

				Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
						System.currentTimeMillis() + ".jpg");
				cameraPath = file.getAbsolutePath();
				imageUri = Uri.fromFile(file);
				intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

				Intent chooser = Intent.createChooser(intent1, "选择头像...");
				chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { intent2 });
				startActivityForResult(chooser, 101);
			}
		});
	}

	@Override
	public void onActivityResult(int arg0, int arg1, Intent arg2) {
		if (arg1 == getActivity().RESULT_OK && arg0 == 101) {
			String filePath = "";
			if (arg2 != null) {
				// 图库选图
				// 对于部分手机来说，在安卓原生的拍照程序基础上做了修改
				// 导致拍照的照片会随着arg2返回到这里
				Uri uri = arg2.getData();

				if (uri != null) {
					if (!uri.getPath().equals(imageUri.getPath())) {
						// 图库
						Cursor cursor = getActivity().getContentResolver().query(uri,
								new String[] { MediaStore.Images.Media.DATA }, null, null, null);
						cursor.moveToNext();
						filePath = cursor.getString(0);
						cursor.close();
					} else {
						// 拍照
						// 拍照的路径依然是cameraPath
						filePath = cameraPath;
					}
				} else {
					Bundle bundle = arg2.getExtras();
					// bitmap是拍照回来的照片
					Bitmap bitmap = (Bitmap) bundle.get("data");
					// TODO 将bitmap存储到SD卡
					String picName = System.currentTimeMillis() + ".jpg";
					FileUtil.saveBitmap(bitmap, picName);
				}
			} else {
				// 相机拍照
				filePath = cameraPath;
				Log.i("TAG", filePath);
			}
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("suibian", getActivity().MODE_PRIVATE)
					.edit();
			editor.putString("filePath", filePath);
			editor.commit();

			final BmobFile bf = new BmobFile(new File(filePath));
			bf.uploadblock(getActivity(), new UploadFileListener() {
				@Override
				public void onSuccess() {
					// 头像URL
					avatarUrl = bf.getFileUrl(getActivity());
					HttpUtil.displayImage(avatarUrl, ivHead);
					User user = new User();
					user.setObjectId(BmobUser.getCurrentUser(getActivity()).getObjectId());
					user.setAvatarUrl(avatarUrl);
					BmobManager.updateUser(getActivity(), user, MyFragment.this);
				}

				@Override
				public void onFailure(int arg0, String arg1) {
					Log.i("TAG", "上传头像失败" + arg0 + arg1);
				}
			});
		}
	}

	private void setGridView2(View view) {
		UserGridViewAdapter adapter = new UserGridViewAdapter(getActivity());
		gvTool.setAdapter(adapter);
		adapter.add(R.drawable.find_g_1);
		adapter.add(R.drawable.find_g_2);
		adapter.add(R.drawable.find_g_3);
		adapter.add(R.drawable.find_g_4);
		adapter.add(R.drawable.find_g_5);
		adapter.add(R.drawable.find_g_6);
		adapter.add(R.drawable.find_g_7);
		adapter.add(R.drawable.find_g_8);
	}

	private void setGridView1(View view) {
		UserGridViewAdapter adapter = new UserGridViewAdapter(getActivity());
		userGridView.setAdapter(adapter);
		adapter.add(R.drawable.user_3);
		adapter.add(R.drawable.user_4);
		adapter.add(R.drawable.user_5);
		adapter.add(R.drawable.user_6);
		adapter.add(R.drawable.user_7);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvSet:
			Intent intent = new Intent(getActivity(), SetActivity.class);
			startActivity(intent);
			break;
		case R.id.layoutAddress:
			Intent intent2 = new Intent(getActivity(), AddressActivity.class);
			startActivity(intent2);
			break;

		default:
			break;
		}
	}

	@Override
	public void success(String s) {
		MyApplication.toast(s);
	}

	@Override
	public void fail(String s) {
		MyApplication.toast(s);
	}
}
