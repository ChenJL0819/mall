package com.oiios.suibian.fragment;

import com.oiios.suibian.R;

import android.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 封装fragment
 *
 */

public abstract class BaseFragment extends Fragment {
	protected View contentView;
	// 自定义ActionBar
	protected RelativeLayout myActionBar;
	protected ImageView ivHeadLeft;
	protected ImageView ivHeadRight;

	public void initActionBar(int leftResId, String title, int rightResId) {
		if (myActionBar == null) {
			return;
		}

		if (leftResId != -1) {
			ivHeadLeft = (ImageView) myActionBar.findViewById(R.id.ivHeadLeft);
			ivHeadLeft.setImageResource(leftResId);
		}
		if (title != null && !title.equals("")) {
			TextView tvTitle = (TextView) myActionBar.findViewById(R.id.tvTitle);
			tvTitle.setText(title);
		}
		if (rightResId != -1) {
			ivHeadRight = (ImageView) myActionBar.findViewById(R.id.ivHeadRight);
			ivHeadRight.setImageResource(rightResId);
		}
	}

	public abstract void initView(View view);

	public abstract void bindListener();
}
